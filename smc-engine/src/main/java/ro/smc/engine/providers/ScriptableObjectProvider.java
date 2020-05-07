package ro.smc.engine.providers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ro.smc.engine.metadata.ChangeTrackedConfigurationKey;
import ro.smc.engine.metadata.ConfigurationKey;
import ro.smc.engine.metadata.ConfigurationMetadata;
import ro.smc.engine.metadata.SqlObjectConfigurationKey;
import ro.smc.engine.runtime.ConfigurationInstance;
import ro.smc.engine.runtime.ConfigurationItem;
import ro.smc.engine.runtime.ConfigurationSqlItem;
import ro.smc.engine.util.DbObjectName;
import ro.smc.engine.util.StringUtil;

public class ScriptableObjectProvider extends DbProvider {

	public ScriptableObjectProvider(ConfigurationMetadata metadata) {
		super(metadata);
	}

	@Override
	public OperationResult<ConfigurationItem> getConfigurationItem(Connection connection, ConfigurationKey key,
			boolean includeSecurity) throws SQLException {
		OperationResult<ConfigurationItem> ret = new OperationResult<>();
		SqlObjectConfigurationKey sqlKey = (SqlObjectConfigurationKey) key;
		String query = "SELECT object_name(sp.object_id) AS [Name], ISNULL(smsp.definition, ssmsp.definition) AS [Definition], sp.type as dbType\r\n"
				+ "FROM sys.all_objects AS sp\r\n" + "	LEFT OUTER JOIN sys.sql_modules AS smsp \r\n"
				+ "		ON smsp.object_id = sp.object_id\r\n"
				+ "	LEFT OUTER JOIN sys.system_sql_modules AS ssmsp \r\n"
				+ "		ON ssmsp.object_id = sp.object_id\r\n"
				+ "WHERE (sp.type = N'P' OR sp.type = N'RF' OR sp.type='PC' OR sp.type='V' OR sp.type='TF' OR sp.type='FN' OR sp.type='IF' ) and is_ms_shipped = 0 and (sp.name=N'%1$s' and SCHEMA_NAME(sp.schema_id)=N'%2$s')";
		DbObjectName dbObj = DbObjectName.Convert(sqlKey.Id);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(String.format(query, dbObj.getTable(), dbObj.getSchema()));
		if (resultSet.next()) {
			String definition = resultSet.getString(2);
			String dbType = resultSet.getString(3);
			String dbObjectType = null;
			switch (dbType.trim()) {
			case "P":
			case "RF":
			case "PC":
				dbObjectType = SqlObjectType.STORED_PROCEDURE;
				break;
			case "TF":
			case "FN":
			case "IF":
				dbObjectType = SqlObjectType.FUNCTION;
				break;
			case "V":
				dbObjectType = SqlObjectType.VIEW;
				break;
			}

			if (StringUtil.isNullOrWhiteSpace(dbType)) {
				ret.addError(new UnsupportedOperationException(
						String.format("Sql Object [%1$s] (type [%2$s]) is not supported", sqlKey.Id, dbType.trim())));
				return ret;
			}

			ret.Result = new ConfigurationSqlItem(dbObj, definition, dbObjectType);
		} else
			ret.addError(new SqlObjectNotFoundException(String.format("Sql Object [%1$s] does not exist", sqlKey.Id)));

		return ret;
	}

	@Override
	public List<ChangeTrackedConfigurationKey> getAvailableConfigurations(ConfigurationAccessSession accessSession)
			throws SQLException {
		ArrayList<ChangeTrackedConfigurationKey> ret = new ArrayList<>();
		String query = "SELECT result.Id, 'SqlObject' ConfigurationName\r\n" + "FROM \r\n" + "( \r\n"
				+ "	SELECT '[' + SCHEMA_NAME(sp.schema_id) + '].[' + sp.name + ']' as Id, sp.*\r\n"
				+ "	FROM sys.all_objects AS sp\r\n" + "		LEFT OUTER JOIN sys.sql_modules AS smsp \r\n"
				+ "			ON smsp.object_id = sp.object_id\r\n"
				+ "		LEFT OUTER JOIN sys.system_sql_modules AS ssmsp \r\n"
				+ "			ON ssmsp.object_id = sp.object_id\r\n"
				+ "	WHERE (sp.type = N'P' OR sp.type = N'RF' OR sp.type='PC' OR sp.type='V' OR sp.type='TF' OR sp.type='FN' OR sp.type='IF' ) and is_ms_shipped = 0\r\n"
				+ ") AS result";
		Statement statement = accessSession.Connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			ChangeTrackedConfigurationKey availableConfiguration = new ChangeTrackedConfigurationKey();
			availableConfiguration.Id = resultSet.getString(1);
			availableConfiguration.ConfigurationName = resultSet.getString(2);
			ret.add(availableConfiguration);
		}

		return Collections.unmodifiableList(ret);
	}

	@Override
	public int getAvailableConfigurationsCount(ConfigurationAccessSession accessSession) throws SQLException {
		int ret = 0;
		String query = "SELECT count(1)\r\n" + "FROM \r\n" + "( \r\n"
				+ "	SELECT '[' + SCHEMA_NAME(sp.schema_id) + '].[' + sp.name + ']' as Id, sp.*\r\n"
				+ "	FROM sys.all_objects AS sp\r\n" + "		LEFT OUTER JOIN sys.sql_modules AS smsp \r\n"
				+ "			ON smsp.object_id = sp.object_id\r\n"
				+ "		LEFT OUTER JOIN sys.system_sql_modules AS ssmsp \r\n"
				+ "			ON ssmsp.object_id = sp.object_id\r\n"
				+ "	WHERE (sp.type = N'P' OR sp.type = N'RF' OR sp.type='PC' OR sp.type='V' OR sp.type='TF' OR sp.type='FN' OR sp.type='IF' ) and is_ms_shipped = 0\r\n"
				+ ") AS result";
		Statement statement = accessSession.Connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next())
			ret = resultSet.getInt(1);

		return ret;
	}

	@Override
	public ConfigurationAccessResult getConfigurationInstance(Connection connection, ConfigurationKey key,
			boolean includeSecurity, boolean treatMissingSqlObjectErrorsAsWarnings) {
		ConfigurationAccessResult ret = new ConfigurationAccessResult();
		try {
			ArrayList<ConfigurationItem> items = new ArrayList<>();
			OperationResult<ConfigurationItem> res = getConfigurationItem(connection, key, includeSecurity);
			if (!res.isSucces() || res.Result == null) {
				SqlObjectNotFoundException notFoundEx = (SqlObjectNotFoundException) res.getErrors().stream()
						.filter(x -> x instanceof SqlObjectNotFoundException).findFirst().orElse(null);
				if (notFoundEx != null)
					ret.NotFound = true;
				else {
					for (String info : res.getInfos())
						ret.addInfo(info);
					for (String warn : res.getWarnings())
						ret.addWarning(warn);
					for (Exception error : res.getErrors())
						ret.addError(error);
				}
			} else {
				items.add(res.Result);
				ret.Result = new ConfigurationInstance(Metadata, key.clone(), items, includeSecurity);
			}
		} catch (Exception ex) {
			ret.addError(ex);
		}

		return ret;
	}
}
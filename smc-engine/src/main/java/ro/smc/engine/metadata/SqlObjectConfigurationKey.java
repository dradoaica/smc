package ro.smc.engine.metadata;

import java.util.Hashtable;

import ro.smc.engine.SqlObjectType;

public class SqlObjectConfigurationKey extends ConfigurationKey {
	private static final long serialVersionUID = 1L;

	public SqlObjectType AcceptedSqlObjectTypes;

	@Override
	public ConfigurationKey clone() {
		SqlObjectConfigurationKey ret = new SqlObjectConfigurationKey();
		ret.ConfigurationVersion = ConfigurationVersion;
		ret.ConfigurationHash = ConfigurationHash;
		ret.ConfigurationName = ConfigurationName;
		ret.Description = Description;
		ret.Id = Id;
		ret.ExtraFields = new Hashtable<>(ExtraFields);
		ret.AcceptedSqlObjectTypes = AcceptedSqlObjectTypes;

		return ret;
	}

	@Override
	public String toString() {
		return String.format("ConfigurationName:%1$s, Id:%2$s, SqlReferenceTypes:%3$s, ConfigurationVersion:%4$s",
				ConfigurationName, Id, AcceptedSqlObjectTypes, ConfigurationVersion);
	}
}
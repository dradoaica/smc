package ConfigurationTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import ro.smc.engine.metadata.ChangeTrackedConfigurationKey;
import ro.smc.engine.metadata.SqlObjectConfigurationKey;
import ro.smc.engine.providers.ConfigurationAccessResult;
import ro.smc.engine.providers.ConfigurationAccessSession;
import ro.smc.engine.providers.OperationResult;
import ro.smc.engine.providers.OperationResultBase;
import ro.smc.engine.providers.ScriptableObjectProvider;
import ro.smc.engine.runtime.ConfigurationItem;
import ro.smc.engine.util.DbObjectName;

public class ScriptableObjectProviderTests {
	@BeforeClass
	public static void initClass() throws ClassNotFoundException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void dbObjectNameTest() {
		DbObjectName res = DbObjectName.Convert("[dbo].[usp_GetNextId]");
		assertTrue("", res != null);
	}

	@Test
	public void getAvailableConfigurationsCountTest() throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("serverName", "localhost");
		properties.setProperty("databaseName", "SMC");
		properties.setProperty("user", "sa");
		properties.setProperty("password", "mama123");
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
			ConfigurationAccessSession accessSession = new ConfigurationAccessSession(connection);
			ScriptableObjectProvider provider = new ScriptableObjectProvider(null);
			int res = provider.getAvailableConfigurationsCount(accessSession);
			assertTrue("", res > 0);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void getAvailableConfigurationsTest() throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("serverName", "localhost");
		properties.setProperty("databaseName", "SMC");
		properties.setProperty("user", "sa");
		properties.setProperty("password", "mama123");
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
			ConfigurationAccessSession accessSession = new ConfigurationAccessSession(connection);
			ScriptableObjectProvider provider = new ScriptableObjectProvider(null);
			List<ChangeTrackedConfigurationKey> res = provider.getAvailableConfigurations(accessSession);
			assertTrue("", !res.isEmpty());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void getConfigurationItemTest() throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("serverName", "localhost");
		properties.setProperty("databaseName", "SMC");
		properties.setProperty("user", "sa");
		properties.setProperty("password", "mama123");
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
			ScriptableObjectProvider provider = new ScriptableObjectProvider(null);
			SqlObjectConfigurationKey key = new SqlObjectConfigurationKey();
			key.Id = "usp_GetNextId";
			OperationResult<ConfigurationItem> res = provider.getConfigurationItem(connection, key, false);
			assertTrue("", res.Result != null);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void getConfigurationInstanceTest() throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("serverName", "localhost");
		properties.setProperty("databaseName", "SMC");
		properties.setProperty("user", "sa");
		properties.setProperty("password", "mama123");
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
			ScriptableObjectProvider provider = new ScriptableObjectProvider(null);
			SqlObjectConfigurationKey key = new SqlObjectConfigurationKey();
			key.Id = "usp_GetNextId";
			key.ConfigurationName = "SqlObject";
			ConfigurationAccessResult res = provider.getConfigurationInstance(connection, key, false, true);
			assertTrue("", res.Result != null);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void applyConfigurationTest() throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("serverName", "localhost");
		properties.setProperty("databaseName", "SMC");
		properties.setProperty("user", "sa");
		properties.setProperty("password", "mama123");
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
			ScriptableObjectProvider provider = new ScriptableObjectProvider(null);
			SqlObjectConfigurationKey key = new SqlObjectConfigurationKey();
			key.Id = "usp_GetNextId";
			key.ConfigurationName = "SqlObject";
			ConfigurationAccessResult res1 = provider.getConfigurationInstance(connection, key, false, true);
			OperationResultBase res2 = provider.applyConfiguration(connection, res1.Result);
			assertTrue("", res2.isSucces());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}

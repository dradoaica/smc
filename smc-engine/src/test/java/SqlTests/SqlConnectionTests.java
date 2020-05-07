package SqlTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

public class SqlConnectionTests {
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
	public void openCloseSqlConnection() throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("serverName", "localhost");
		properties.setProperty("databaseName", "SMC");
		properties.setProperty("user", "sa");
		properties.setProperty("password", "mama123");
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
			Statement statement = connection.createStatement();
			statement.executeQuery("select 1");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}

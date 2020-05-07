package ro.smc.engine.providers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigurationAccessSession {
	private final ArrayList<String> extraFields = new ArrayList<>();
	public Connection Connection;

	public ConfigurationAccessSession(Connection connection) {
		Connection = connection;
	}

	public List<String> getExtraFields() {
		return Collections.unmodifiableList(extraFields);
	}

	public void addExtraField(String fieldName) {
		extraFields.add(fieldName);
	}
}

package ro.smc.engine.providers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ro.smc.engine.metadata.ConfigurationKey;
import ro.smc.engine.metadata.ConfigurationMetadata;
import ro.smc.engine.metadata.ChangeTrackedConfigurationKey;
import ro.smc.engine.runtime.ConfigurationItem;

public class DbProvider extends ConfigurationProvider {
	public ConfigurationMetadata Metadata;

	public DbProvider(ConfigurationMetadata metadata) {
		Metadata = metadata;
	}

	public OperationResult<ConfigurationItem> getConfigurationItem(Connection connection, ConfigurationKey key,
			boolean includeSecurity) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public List<ChangeTrackedConfigurationKey> getAvailableConfigurations(ConfigurationAccessSession accessSession)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getAvailableConfigurationsCount(ConfigurationAccessSession accessSession) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ConfigurationAccessResult getConfigurationInstance(Connection connection, ConfigurationKey key,
			boolean includeSecurity, boolean treatMissingSqlObjectErrorsAsWarnings) {
		throw new UnsupportedOperationException();
	}
}

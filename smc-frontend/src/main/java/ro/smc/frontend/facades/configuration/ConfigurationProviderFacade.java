package ro.smc.frontend.facades.configuration;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;

import ro.smc.engine.metadata.ChangeTrackedConfigurationKey;
import ro.smc.engine.metadata.ConfigurationKey;
import ro.smc.engine.metadata.MetadataManager;
import ro.smc.engine.providers.ConfigurationAccessResult;
import ro.smc.engine.providers.ConfigurationAccessSession;
import ro.smc.engine.providers.DbProvider;
import ro.smc.engine.providers.OperationResultBase;
import ro.smc.engine.runtime.ConfigurationInstance;

@Stateless
public class ConfigurationProviderFacade {
	public List<ChangeTrackedConfigurationKey> getAvailableConfigurations(String name,
			ConfigurationAccessSession accessSession) throws SQLException {
		DbProvider provider = MetadataManager.getConfigurationProvider(MetadataManager.getLatestVersion(), name);

		return provider.getAvailableConfigurations(accessSession);
	}

	public ConfigurationAccessResult getConfigurationInstance(ConfigurationKey key,
			ConfigurationAccessSession accessSession, boolean includeSecurity,
			boolean treatMissingSqlObjectErrorsAsWarnings) {
		DbProvider provider = MetadataManager.getConfigurationProvider(MetadataManager.getLatestVersion(), key);

		return provider.getConfigurationInstance(accessSession.Connection, key, includeSecurity,
				treatMissingSqlObjectErrorsAsWarnings);
	}

	public OperationResultBase applyConfiguration(ConfigurationInstance instance,
			ConfigurationAccessSession accessSession) {
		DbProvider provider = MetadataManager.getConfigurationProvider(MetadataManager.getLatestVersion(),
				instance.getConfigurationKey());

		return provider.applyConfiguration(accessSession.Connection, instance);
	}

	public List<String> getConfigurationNames() {
		return MetadataManager.getConfigurationNames();
	}
}

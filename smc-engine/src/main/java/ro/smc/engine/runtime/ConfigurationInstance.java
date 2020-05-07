package ro.smc.engine.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ro.smc.engine.metadata.ConfigurationKey;
import ro.smc.engine.metadata.ConfigurationMetadata;
import ro.smc.engine.metadata.ExternalConfigurationKey;

public class ConfigurationInstance {
	private ConfigurationKey _configurationKey;
	private ConfigurationMetadata _metadata;
	private List<ConfigurationItem> _configurationItems;
	private ArrayList<ExternalConfigurationKey> _externalConfigurations;
	private boolean _includesSecurity;

	public ConfigurationInstance(ConfigurationMetadata metadata, ConfigurationKey key, List<ConfigurationItem> items,
			boolean includesSecurity) {
		_metadata = metadata;
		_configurationKey = key;
		_configurationItems = items;
		_includesSecurity = includesSecurity;
		_externalConfigurations = new ArrayList<>();
	}

	public ConfigurationKey getConfigurationKey() {
		return _configurationKey;
	}

	public ConfigurationMetadata getMetadata() {
		return _metadata;
	}

	public List<ConfigurationItem> getConfigurationItems() {
		return Collections.unmodifiableList(_configurationItems);
	}

	public List<ExternalConfigurationKey> getExternalConfigurations() {
		return Collections.unmodifiableList(_externalConfigurations);
	}

	public void addExternalConfiguration(ExternalConfigurationKey key) {
		_externalConfigurations.add(key);
	}

	public boolean includesSecurity() {
		return _includesSecurity;
	}
}
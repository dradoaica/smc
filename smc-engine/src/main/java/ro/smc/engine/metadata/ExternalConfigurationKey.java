package ro.smc.engine.metadata;

import java.util.Hashtable;

public class ExternalConfigurationKey extends ConfigurationKey {
	private static final long serialVersionUID = 1L;

	@Override
	public ConfigurationKey clone() {
		ExternalConfigurationKey ret = new ExternalConfigurationKey();
		ret.ConfigurationVersion = ConfigurationVersion;
		ret.ConfigurationHash = ConfigurationHash;
		ret.ConfigurationName = ConfigurationName;
		ret.Description = Description;
		ret.Id = Id;
		ret.ExtraFields = new Hashtable<>(ExtraFields);

		return ret;
	}
}

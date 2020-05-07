package ro.smc.engine.metadata;

import java.io.Serializable;
import java.util.Hashtable;

public class ConfigurationKey implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public String Id;
	public String ConfigurationName;
	public String Description;
	public String ConfigurationHash;
	public String ConfigurationVersion;
	public Hashtable<String, Object> ExtraFields;

	public ConfigurationKey() {
		ExtraFields = new Hashtable<>();
	}

	public ConfigurationKey clone() {
		ConfigurationKey ret = new ConfigurationKey();
		ret.ConfigurationVersion = ConfigurationVersion;
		ret.ConfigurationHash = ConfigurationHash;
		ret.ConfigurationName = ConfigurationName;
		ret.Description = Description;
		ret.Id = Id;
		ret.ExtraFields = new Hashtable<>(ExtraFields);

		return ret;
	}

	@Override
	public String toString() {
		return String.format("ConfigurationName:%1$s, Id:%2$s, ConfigurationVersion:%3$s", ConfigurationName, Id,
				ConfigurationVersion);
	}
}

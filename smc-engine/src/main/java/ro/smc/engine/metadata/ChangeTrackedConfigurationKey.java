package ro.smc.engine.metadata;

import java.util.Hashtable;

public class ChangeTrackedConfigurationKey extends ConfigurationKey {
	private static final long serialVersionUID = 1L;
	public String Operation;
	public String SqlUserName;
	public String Moment;
	public String ConfigurationLevel;

	@Override
	public ConfigurationKey clone() {
		ChangeTrackedConfigurationKey ret = new ChangeTrackedConfigurationKey();
		ret.ConfigurationVersion = ConfigurationVersion;
		ret.ConfigurationHash = ConfigurationHash;
		ret.ConfigurationName = ConfigurationName;
		ret.Description = Description;
		ret.Id = Id;
		ret.ExtraFields = new Hashtable<>(ExtraFields);
		ret.Operation = Operation;
		ret.SqlUserName = SqlUserName;
		ret.Moment = Moment;
		ret.ConfigurationLevel = ConfigurationLevel;

		return ret;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getConfigurationName() {
		return ConfigurationName;
	}

	public void setConfigurationName(String configurationName) {
		ConfigurationName = configurationName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}

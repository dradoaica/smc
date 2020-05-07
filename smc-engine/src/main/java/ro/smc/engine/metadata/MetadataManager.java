package ro.smc.engine.metadata;

import java.util.ArrayList;
import java.util.List;

import ro.smc.engine.providers.DbProvider;
import ro.smc.engine.providers.ScriptableObjectProvider;

public class MetadataManager {
	public static String getLatestVersion() {
		return "";
	}

	public static DbProvider getConfigurationProvider(String version, String configurationName) {
		if (configurationName.equals("SqlObject"))
			return new ScriptableObjectProvider(null);

		throw new UnsupportedOperationException(configurationName);
	}

	public static DbProvider getConfigurationProvider(String version, ConfigurationKey key) {
		return getConfigurationProvider(version, key.ConfigurationName);
	}

	public static List<String> getConfigurationNames() {
		ArrayList<String> ret = new ArrayList<>();
		ret.add("SqlObject");

		return ret;
	}
}

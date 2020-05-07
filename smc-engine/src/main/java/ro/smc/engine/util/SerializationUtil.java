package ro.smc.engine.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ro.smc.engine.metadata.SqlObjectConfigurationKey;
import ro.smc.engine.runtime.ConfigurationItem;
import ro.smc.engine.util.serialization.ConfigurationInstance;
import ro.smc.engine.util.serialization.ConfigurationItems;
import ro.smc.engine.util.serialization.ConfigurationSqlItem;

public class SerializationUtil {
	private static JAXBContext _context;

	private static JAXBContext getContext() throws JAXBException {
		if (_context == null)
			_context = JAXBContext.newInstance(ConfigurationInstance.class);

		return _context;
	}

	public static String serialize(ConfigurationInstance configurationInstance) throws JAXBException {
		StringWriter sw = new StringWriter();
		getContext().createMarshaller().marshal(configurationInstance, sw);

		return sw.toString();
	}

	public static ConfigurationInstance deserialize(String string) throws JAXBException {
		StringReader sr = new StringReader(string);

		return (ConfigurationInstance) getContext().createUnmarshaller().unmarshal(sr);
	}

	private static ConfigurationSqlItem fromRuntime(ro.smc.engine.runtime.ConfigurationSqlItem configurationSqlItem) {
		ConfigurationSqlItem ret = new ConfigurationSqlItem();
		ret.setDefinition(configurationSqlItem.Definition);
		ret.setName(configurationSqlItem.Name.toString());
		ret.setSqlObjectType(configurationSqlItem.DbObjectType);

		return ret;
	}

	public static ConfigurationInstance fromRuntime(ro.smc.engine.runtime.ConfigurationInstance configurationInstance) {
		ConfigurationInstance ret = new ConfigurationInstance();
		ret.setConfigurationName(configurationInstance.getConfigurationKey().ConfigurationName);
		ret.setVersion(configurationInstance.getConfigurationKey().ConfigurationVersion);
		ret.setId(configurationInstance.getConfigurationKey().Id);
		ret.setIncludesSecurity(configurationInstance.includesSecurity());
		ret.setDescription(configurationInstance.getConfigurationKey().Description);
		ret.setConfigurationItems(new ConfigurationItems());

		for (ConfigurationItem item : configurationInstance.getConfigurationItems()) {
			if (item instanceof ro.smc.engine.runtime.ConfigurationSqlItem)
				ret.getConfigurationItems().getConfigurationSqlItem()
						.add(fromRuntime((ro.smc.engine.runtime.ConfigurationSqlItem) item));
		}

		return ret;
	}

	private static ro.smc.engine.runtime.ConfigurationSqlItem toRuntime(ConfigurationSqlItem configurationSqlItem) {
		DbObjectName name = DbObjectName.Convert(configurationSqlItem.getName());

		return new ro.smc.engine.runtime.ConfigurationSqlItem(name, configurationSqlItem.getSqlObjectType(),
				configurationSqlItem.getDefinition());
	}

	public static ro.smc.engine.runtime.ConfigurationInstance toRuntime(ConfigurationInstance configurationInstance) {
		SqlObjectConfigurationKey key = new SqlObjectConfigurationKey();
		key.ConfigurationName = configurationInstance.getConfigurationName();
		key.ConfigurationVersion = configurationInstance.getVersion();
		key.Id = configurationInstance.getId();
		key.Description = configurationInstance.getDescription();
		ArrayList<ro.smc.engine.runtime.ConfigurationItem> items = new ArrayList<>();
		for (ConfigurationSqlItem configurationSqlItem : configurationInstance.getConfigurationItems()
				.getConfigurationSqlItem())
			items.add(toRuntime(configurationSqlItem));

		return new ro.smc.engine.runtime.ConfigurationInstance(null, key, items,
				configurationInstance.isIncludesSecurity());
	}
}
package ro.smc.engine.runtime;

import ro.smc.engine.util.DbObjectName;

public class ConfigurationSqlItem extends ConfigurationItem {
	public DbObjectName Name;
	public String DbObjectType;
	public String Definition;

	public ConfigurationSqlItem(DbObjectName name, String definition, String dbObjectType) {
		Name = name;
		DbObjectType = dbObjectType;
		Definition = definition;
	}

	@Override
	public String Serialize() {
		return Definition;
	}

	@Override
	public String toString() {
		return String.format("DbObjectType:%1$s Name:%2$s}", DbObjectType, Name);
	}
}
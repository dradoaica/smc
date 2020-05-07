package ro.smc.engine.providers;

import ro.smc.engine.metadata.ConfigurationKey;
import java.sql.Connection;

public class ConfigurationAccessContext {
    public Connection Connection;
    public ConfigurationKey ConfigurationKey;
    public boolean IncludeSecurityData;
    
    public ConfigurationAccessContext(Connection connection, ConfigurationKey configurationKey)
    {
    	Connection = connection;
    	ConfigurationKey = configurationKey;
    }
}

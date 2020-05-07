package ro.smc.frontend.controllers.configuration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ro.smc.engine.metadata.ChangeTrackedConfigurationKey;
import ro.smc.engine.providers.ConfigurationAccessSession;
import ro.smc.frontend.controllers.AccountController;
import ro.smc.frontend.entities.general.EnvDatabase;
import ro.smc.frontend.facades.configuration.ConfigurationProviderFacade;
import ro.smc.frontend.facades.general.EnvDatabaseFacade;

@Named("configurationController")
@ViewScoped
public class ConfigurationController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ConfigurationProviderFacade _configurationProviderFacade;
	@EJB
	private EnvDatabaseFacade _envDatabaseFacade;
	@Inject
	private AccountController _accountController;
	private String _configurationNameFilter;
	private EnvDatabase _envDatabaseFilter;
	private ChangeTrackedConfigurationKey _selected;
	private List<ChangeTrackedConfigurationKey> _items;
	private List<EnvDatabase> _envDatabaseItems;
	private List<String> _configurationNameItems;

	public ConfigurationController() {
		_items = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("environmentId", _accountController.getCurrentEnvironment().getEnvironmentId());
		_envDatabaseItems = _envDatabaseFacade.runNamedQuery("EnvDatabase.findByEnvironment", parameters);
		_configurationNameItems = _configurationProviderFacade.getConfigurationNames();
	}

	public String getConfigurationNameFilter() {
		return _configurationNameFilter;
	}

	public void setConfigurationNameFilter(String configurationNameFilter) {
		_configurationNameFilter = configurationNameFilter;
	}

	public EnvDatabase getEnvDatabaseFilter() {
		return _envDatabaseFilter;
	}

	public void setEnvDatabaseFilter(EnvDatabase envDatabaseFilter) {
		_envDatabaseFilter = envDatabaseFilter;
	}

	public ChangeTrackedConfigurationKey getSelected() {
		return _selected;
	}

	public void setSelected(ChangeTrackedConfigurationKey selected) {
		_selected = selected;
	}

	public List<ChangeTrackedConfigurationKey> getItems() {
		return _items;
	}

	public List<EnvDatabase> getEnvDatabaseItems() {
		return _envDatabaseItems;
	}

	public List<String> getConfigurationNameItems() {
		return _configurationNameItems;
	}

	public void apply() throws SQLException, ClassNotFoundException {
		if (_envDatabaseFilter == null || _configurationNameFilter == null)
			return;

		Properties properties = new Properties();
		properties.setProperty("serverName", _envDatabaseFilter.getServerName());
		properties.setProperty("databaseName", _envDatabaseFilter.getDatabaseName());
		properties.setProperty("user", _envDatabaseFilter.getUserName());
		properties.setProperty("password", _envDatabaseFilter.getPassword());
		Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
		ConfigurationAccessSession accessSession = new ConfigurationAccessSession(connection);
		_items = new ArrayList<>(
				_configurationProviderFacade.getAvailableConfigurations(_configurationNameFilter, accessSession));
	}
}

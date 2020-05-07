package ro.smc.frontend.controllers.configuration;

import ro.smc.engine.metadata.SqlObjectConfigurationKey;
import ro.smc.engine.providers.ConfigurationAccessResult;
import ro.smc.engine.providers.ConfigurationAccessSession;
import ro.smc.engine.util.SerializationUtil;
import ro.smc.engine.util.serialization.ConfigurationInstance;
import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.controllers.AccountController;
import ro.smc.frontend.entities.configuration.AvailableConfiguration;
import ro.smc.frontend.entities.configuration.AvailablePackage;
import ro.smc.frontend.entities.configuration.DeployedConfiguration;
import ro.smc.frontend.entities.configuration.DeployedPackage;
import ro.smc.frontend.entities.general.ActivityLog;
import ro.smc.frontend.entities.general.DictionaryItem;
import ro.smc.frontend.entities.general.EnvDatabase;
import ro.smc.frontend.facades.configuration.AvailablePackageFacade;
import ro.smc.frontend.facades.configuration.ConfigurationProviderFacade;
import ro.smc.frontend.facades.configuration.DeployedPackageFacade;
import ro.smc.frontend.facades.general.ActivityLogFacade;
import ro.smc.frontend.facades.general.DictionaryItemFacade;
import ro.smc.frontend.facades.general.EnvDatabaseFacade;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;
import javax.faces.view.ViewScoped;

@Named("availablePackageController")
@ViewScoped
public class AvailablePackageController extends AbstractController<AvailablePackage> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private AvailablePackageFacade _ejbFacade;
	@EJB
	private DictionaryItemFacade _dictionaryItemFacade;
	@EJB
	private ConfigurationProviderFacade _configurationProviderFacade;
	@EJB
	private EnvDatabaseFacade _envDatabaseFacade;
	@EJB
	private DeployedPackageFacade _deployedPackageFacade;
	@EJB
	private ActivityLogFacade _activityLogFacade;
	@Inject
	private AccountController _accountController;
	@Inject
	private ConfigurationController _configurationController;
	private AvailableConfiguration _selectedConfiguration;
	private List<DictionaryItem> _packageStates;
	private List<DictionaryItem> _activityLogTypes;

	public AvailablePackageController() {
		super(AvailablePackage.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
		_packageStates = _dictionaryItemFacade.runNamedQuery("DictionaryItem.findAllPackageStates", null);
		_activityLogTypes = _dictionaryItemFacade.runNamedQuery("DictionaryItem.findAllActivityLogTypes", null);
	}

	@Override
	public void prepare4Add() {
		super.prepare4Add();
		DictionaryItem packageState = _packageStates.stream()
				.filter(x -> x.getDictionaryItemCode().equals("#ReadyForDeployment")).findFirst().orElse(null);
		_selected.setPackageState(packageState);
		_selected.setConfigurations(new ArrayList<>());
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(AvailablePackage entity) {
		List rez = _ejbFacade.runNamedQueryRaw("AvailablePackage.getNextId", null);
		entity.setAvailablePackageId((Integer) rez.get(0));
		entity.setEnvironment(_accountController.getCurrentEnvironment());
	}

	@Override
	public List<AvailablePackage> getItems() {
		if (_items == null)
			_items = _ejbFacade.findByEnvironment(_accountController.getCurrentEnvironment().getEnvironmentId());

		return _items;
	}

	public AvailableConfiguration getSelectedConfiguration() {
		return _selectedConfiguration;
	}

	public void setSelectedConfiguration(AvailableConfiguration selectedConfiguration) {
		_selectedConfiguration = selectedConfiguration;
	}

	public void deleteConfiguration() {
		if (_selected != null && _selectedConfiguration != null)
			_selected.removeConfiguration(_selectedConfiguration);
	}

	@SuppressWarnings("rawtypes")
	public void addConfiguration() throws SQLException, JAXBException {
		Properties properties = new Properties();
		properties.setProperty("serverName", _configurationController.getEnvDatabaseFilter().getServerName());
		properties.setProperty("databaseName", _configurationController.getEnvDatabaseFilter().getDatabaseName());
		properties.setProperty("user", _configurationController.getEnvDatabaseFilter().getUserName());
		properties.setProperty("password", _configurationController.getEnvDatabaseFilter().getPassword());
		Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
		ConfigurationAccessSession accessSession = new ConfigurationAccessSession(connection);
		SqlObjectConfigurationKey key = new SqlObjectConfigurationKey();
		key.Id = _configurationController.getSelected().Id;
		key.ConfigurationName = _configurationController.getSelected().ConfigurationName;
		ConfigurationAccessResult res = _configurationProviderFacade.getConfigurationInstance(key, accessSession, false,
				false);
		ConfigurationInstance configurationInstance = SerializationUtil.fromRuntime(res.Result);

		List rez = _ejbFacade.runNamedQueryRaw("AvailableConfiguration.getNextId", null);
		_selectedConfiguration = new AvailableConfiguration();
		_selectedConfiguration.setAvailableConfigurationId((Integer) rez.get(0));
		_selectedConfiguration.setAvailableConfigurationName(_configurationController.getSelected().Id);
		_selectedConfiguration.setDeployOrder(0);
		_selectedConfiguration.setType(_configurationController.getSelected().ConfigurationName);
		_selectedConfiguration.setXmlData(SerializationUtil.serialize(configurationInstance));
		_selected.addConfiguration(_selectedConfiguration);
	}

	@SuppressWarnings("rawtypes")
	public void deploy() {
		DictionaryItem packageState = null;
		try {
			EnvDatabase targetDatabase = null;
			List<EnvDatabase> databases = _envDatabaseFacade
					.findByEnvironment(_selected.getEnvironment().getEnvironmentId());
			for (EnvDatabase database : databases)
				if (database.getMask().equals(_selected.getTargetMask())) {
					targetDatabase = database;
					break;
				}

			Properties properties = new Properties();
			properties.setProperty("serverName", targetDatabase.getServerName());
			properties.setProperty("databaseName", targetDatabase.getDatabaseName());
			properties.setProperty("user", targetDatabase.getUserName());
			properties.setProperty("password", targetDatabase.getPassword());
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://", properties);
			ConfigurationAccessSession accessSession = new ConfigurationAccessSession(connection);

			for (AvailableConfiguration availableConfiguration : _selected.getConfigurations()) {
				ConfigurationInstance configurationInstance = SerializationUtil
						.deserialize(availableConfiguration.getXmlData());
				_configurationProviderFacade.applyConfiguration(SerializationUtil.toRuntime(configurationInstance),
						accessSession);
			}

			packageState = _packageStates.stream().filter(x -> x.getDictionaryItemCode().equals("#DeployFinished"))
					.findFirst().orElse(null);
		} catch (Exception e) {
			packageState = _packageStates.stream().filter(x -> x.getDictionaryItemCode().equals("#DeployFailed"))
					.findFirst().orElse(null);
		}

		List rez = _ejbFacade.runNamedQueryRaw("DeployedPackage.getNextId", null);
		DeployedPackage deployedPackage = new DeployedPackage();
		deployedPackage.setDeployedPackageId((Integer) rez.get(0));
		deployedPackage.setDeployedPackageCode(_selected.getAvailablePackageCode());
		deployedPackage.setDeployedPackageName(_selected.getAvailablePackageName());
		deployedPackage.setEnvironment(_selected.getEnvironment());
		deployedPackage.setPackageState(packageState);
		deployedPackage.setTargetMask(_selected.getTargetMask());
		deployedPackage.setConfigurations(new ArrayList<>());
		for (AvailableConfiguration availableConfiguration : _selected.getConfigurations()) {
			rez = _ejbFacade.runNamedQueryRaw("DeployedConfiguration.getNextId", null);
			DeployedConfiguration deployedConfiguration = new DeployedConfiguration();
			deployedConfiguration.setDeployedConfigurationId((Integer) rez.get(0));
			deployedConfiguration.setDeployedConfigurationName(availableConfiguration.getAvailableConfigurationName());
			deployedConfiguration.setType(availableConfiguration.getType());
			deployedConfiguration.setDeployOrder(availableConfiguration.getDeployOrder());
			deployedConfiguration.setXmlData(availableConfiguration.getXmlData());
			deployedPackage.addConfiguration(deployedConfiguration);
		}
		_deployedPackageFacade.add(deployedPackage);

		rez = _ejbFacade.runNamedQueryRaw("ActivityLog.getNextId", null);
		DictionaryItem activityLogType = _activityLogTypes.stream()
				.filter(x -> x.getDictionaryItemCode().equals("#Deploy")).findFirst().orElse(null);
		ActivityLog activityLog = new ActivityLog();
		activityLog.setActivityLogId((Integer) rez.get(0));
		activityLog.setActivityLogType(activityLogType);
		activityLog.setMessage(String.format(
				"Deployed package (Code: '%1$s'; Name: '%2$s') with target '%3$s' on environment (Code: '%4$s'; Name: '%5$s') at '%6$s'",
				_selected.getAvailablePackageCode(), _selected.getAvailablePackageName(), _selected.getTargetMask(),
				_selected.getEnvironment().getEnvironmentCode(), _selected.getEnvironment().getEnvironmentName(),
				new Date().toString()));
		_activityLogFacade.add(activityLog);
	}
}

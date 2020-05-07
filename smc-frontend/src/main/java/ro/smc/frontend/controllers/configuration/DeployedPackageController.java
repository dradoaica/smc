package ro.smc.frontend.controllers.configuration;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.controllers.AccountController;
import ro.smc.frontend.entities.configuration.AvailableConfiguration;
import ro.smc.frontend.entities.configuration.AvailablePackage;
import ro.smc.frontend.entities.configuration.DeployedConfiguration;
import ro.smc.frontend.entities.configuration.DeployedPackage;
import ro.smc.frontend.entities.general.ActivityLog;
import ro.smc.frontend.entities.general.DictionaryItem;
import ro.smc.frontend.facades.configuration.AvailablePackageFacade;
import ro.smc.frontend.facades.configuration.DeployedPackageFacade;
import ro.smc.frontend.facades.general.ActivityLogFacade;
import ro.smc.frontend.facades.general.DictionaryItemFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("deployedPackageController")
@ViewScoped
public class DeployedPackageController extends AbstractController<DeployedPackage> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private DeployedPackageFacade _ejbFacade;
	@EJB
	private DictionaryItemFacade _dictionaryItemFacade;
	@EJB
	private AvailablePackageFacade _availablePackageFacade;
	@EJB
	private ActivityLogFacade _activityLogFacade;
	@Inject
	private AccountController _accountController;
	private DeployedConfiguration _selectedConfiguration;
	private List<DictionaryItem> _packageStates;
	private List<DictionaryItem> _activityLogTypes;

	public DeployedPackageController() {
		super(DeployedPackage.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
		_packageStates = _dictionaryItemFacade.runNamedQuery("DictionaryItem.findAllPackageStates", null);
		_activityLogTypes = _dictionaryItemFacade.runNamedQuery("DictionaryItem.findAllActivityLogTypes", null);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(DeployedPackage entity) {
		List rez = _ejbFacade.runNamedQueryRaw("DeployedPackage.getNextId", null);
		entity.setDeployedPackageId((Integer) rez.get(0));
	}

	@Override
	public List<DeployedPackage> getItems() {
		if (_items == null)
			_items = _ejbFacade.findByEnvironment(_accountController.getCurrentEnvironment().getEnvironmentId());

		return _items;
	}

	public DeployedConfiguration getSelectedConfiguration() {
		return _selectedConfiguration;
	}

	public void setSelectedConfiguration(DeployedConfiguration selectedConfiguration) {
		_selectedConfiguration = selectedConfiguration;
	}

	@SuppressWarnings("rawtypes")
	public void push() {
		if (_selected.getEnvironment().getUpstreamEnvironment() == null)
			return;

		List rez = _ejbFacade.runNamedQueryRaw("AvailablePackage.getNextId", null);
		DictionaryItem packageState = _packageStates.stream()
				.filter(x -> x.getDictionaryItemCode().equals("#ReadyForDeployment")).findFirst().orElse(null);
		AvailablePackage availablePackage = new AvailablePackage();
		availablePackage.setAvailablePackageId((Integer) rez.get(0));
		availablePackage.setAvailablePackageCode(_selected.getDeployedPackageCode());
		availablePackage.setAvailablePackageName(_selected.getDeployedPackageName());
		availablePackage.setEnvironment(_selected.getEnvironment().getUpstreamEnvironment());
		availablePackage.setPackageState(packageState);
		availablePackage.setTargetMask(_selected.getTargetMask());
		availablePackage.setConfigurations(new ArrayList<>());
		for (DeployedConfiguration deployedConfiguration : _selected.getConfigurations()) {
			rez = _ejbFacade.runNamedQueryRaw("AvailableConfiguration.getNextId", null);
			AvailableConfiguration availableConfiguration = new AvailableConfiguration();
			availableConfiguration.setAvailableConfigurationId((Integer) rez.get(0));
			availableConfiguration.setAvailableConfigurationName(deployedConfiguration.getDeployedConfigurationName());
			availableConfiguration.setType(deployedConfiguration.getType());
			availableConfiguration.setDeployOrder(deployedConfiguration.getDeployOrder());
			availableConfiguration.setXmlData(deployedConfiguration.getXmlData());
			availablePackage.addConfiguration(availableConfiguration);
		}
		_availablePackageFacade.add(availablePackage);

		rez = _ejbFacade.runNamedQueryRaw("ActivityLog.getNextId", null);
		DictionaryItem activityLogType = _activityLogTypes.stream()
				.filter(x -> x.getDictionaryItemCode().equals("#Push")).findFirst().orElse(null);
		ActivityLog activityLog = new ActivityLog();
		activityLog.setActivityLogId((Integer) rez.get(0));
		activityLog.setActivityLogType(activityLogType);
		activityLog.setMessage(String.format(
				"Pushed package (Code: '%1$s'; Name: '%2$s') with target '%3$s' on environment (Code: '%4$s'; Name: '%5$s') at '%6$s'",
				_selected.getDeployedPackageCode(), _selected.getDeployedPackageName(), _selected.getTargetMask(),
				_selected.getEnvironment().getUpstreamEnvironment().getEnvironmentCode(),
				_selected.getEnvironment().getUpstreamEnvironment().getEnvironmentName(), new Date().toString()));
		_activityLogFacade.add(activityLog);
	}
}

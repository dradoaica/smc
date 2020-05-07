package ro.smc.frontend.controllers.configuration;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.configuration.DeployedConfiguration;
import ro.smc.frontend.facades.configuration.DeployedConfigurationFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("deployedConfigurationController")
@ViewScoped
public class DeployedConfigurationController extends AbstractController<DeployedConfiguration> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private DeployedConfigurationFacade _ejbFacade;

	public DeployedConfigurationController() {
		super(DeployedConfiguration.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(DeployedConfiguration entity) {
		List rez = _ejbFacade.runNamedQueryRaw("DeployedConfiguration.getNextId", null);
		entity.setDeployedConfigurationId((Integer) rez.get(0));
	}
}

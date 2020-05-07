package ro.smc.frontend.controllers.configuration;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.configuration.AvailableConfiguration;
import ro.smc.frontend.facades.configuration.AvailableConfigurationFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("availableConfigurationController")
@ViewScoped
public class AvailableConfigurationController extends AbstractController<AvailableConfiguration>
		implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private AvailableConfigurationFacade _ejbFacade;

	public AvailableConfigurationController() {
		super(AvailableConfiguration.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(AvailableConfiguration entity) {
		List rez = _ejbFacade.runNamedQueryRaw("AvailableConfiguration.getNextId", null);
		entity.setAvailableConfigurationId((Integer) rez.get(0));
	}
}

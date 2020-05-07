package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.Environment;
import ro.smc.frontend.facades.general.EnvironmentFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("environmentController")
@ViewScoped
public class EnvironmentController extends AbstractController<Environment> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private EnvironmentFacade _ejbFacade;

	public EnvironmentController() {
		super(Environment.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(Environment entity) {
		List rez = _ejbFacade.runNamedQueryRaw("Environment.getNextId", null);
		entity.setEnvironmentId((Integer) rez.get(0));
	}
}

package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.EnvDatabase;
import ro.smc.frontend.facades.general.EnvDatabaseFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("envDatabaseController")
@ViewScoped
public class EnvDatabaseController extends AbstractController<EnvDatabase> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private EnvDatabaseFacade _ejbFacade;

	public EnvDatabaseController() {
		super(EnvDatabase.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(EnvDatabase entity) {
		List rez = _ejbFacade.runNamedQueryRaw("EnvDatabase.getNextId", null);
		entity.setEnvDatabaseId((Integer) rez.get(0));
	}
}

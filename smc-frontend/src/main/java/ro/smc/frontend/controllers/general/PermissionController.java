package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.Permission;
import ro.smc.frontend.facades.general.PermissionFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("permissionController")
@ViewScoped
public class PermissionController extends AbstractController<Permission> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private PermissionFacade _ejbFacade;

	public PermissionController() {
		super(Permission.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(Permission entity) {
		List rez = _ejbFacade.runNamedQueryRaw("Permission.getNextId", null);
		entity.setPermissionId((Integer) rez.get(0));
	}
}

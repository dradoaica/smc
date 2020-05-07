package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.UserGroupXPermission;
import ro.smc.frontend.facades.general.UserGroupXPermissionFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("userGroupXPermissionController")
@ViewScoped
public class UserGroupXPermissionController extends AbstractController<UserGroupXPermission> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserGroupXPermissionFacade _ejbFacade;

	public UserGroupXPermissionController() {
		super(UserGroupXPermission.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(UserGroupXPermission entity) {
		List rez = _ejbFacade.runNamedQueryRaw("UserGroup.getNextId", null);
		entity.setUserGroupXPermissionId((Integer) rez.get(0));
	}
}

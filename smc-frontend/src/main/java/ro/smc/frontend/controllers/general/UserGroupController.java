package ro.smc.frontend.controllers.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.Permission;
import ro.smc.frontend.entities.general.UserGroup;
import ro.smc.frontend.entities.general.UserGroupXPermission;
import ro.smc.frontend.facades.general.PermissionFacade;
import ro.smc.frontend.facades.general.UserGroupFacade;

import org.primefaces.model.DualListModel;

@Named("userGroupController")
@ViewScoped
public class UserGroupController extends AbstractController<UserGroup> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserGroupFacade _ejbFacade;
	@EJB
	private PermissionFacade _permissionFacade;
	private List<Permission> _allPermissions;
	private DualListModel<Permission> _permissions;

	public UserGroupController() {
		super(UserGroup.class);
	}

	public DualListModel<Permission> getPermissions() {
		return _permissions;
	}

	public void setPermissions(DualListModel<Permission> permissions) {
		_permissions = permissions;
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);

		_allPermissions = _permissionFacade.findAll();
		// dummy
		setPermissions(new DualListModel<>(new ArrayList<Permission>(), new ArrayList<Permission>()));
	}

	@Override
	public void prepare4Add() {
		super.prepare4Add();

		setPermissions(new DualListModel<>(new ArrayList<Permission>(_allPermissions), new ArrayList<Permission>()));
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(UserGroup entity) {
		List rez = _ejbFacade.runNamedQueryRaw("UserGroup.getNextId", null);
		entity.setUserGroupId((Integer) rez.get(0));
		entity.setUserGroupXPermissions(new ArrayList<>());
		for (Permission permission : _permissions.getTarget()) {
			rez = _ejbFacade.runNamedQueryRaw("UserGroupXPermission.getNextId", null);
			UserGroupXPermission newUGXP = new UserGroupXPermission();
			newUGXP.setUserGroupXPermissionId((Integer) rez.get(0));
			newUGXP.setUserGroup(entity);
			newUGXP.setPermission(permission);
			entity.addUserGroupXPermission(newUGXP);
		}
	}

	@Override
	protected void afterAdd(UserGroup entity) {
		if (!isValidationFailed())
			setPermissions(new DualListModel<>(new ArrayList<Permission>(), new ArrayList<Permission>()));
	}

	@Override
	public void prepare4Modify() {
		List<Permission> source = new ArrayList<>(_allPermissions);
		List<Permission> target = new ArrayList<>();
		for (UserGroupXPermission ugxp : _selected.getUserGroupXPermissions()) {
			source.remove(ugxp.getPermission());
			target.add(ugxp.getPermission());
		}
		setPermissions(new DualListModel<>(source, target));
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeModify(UserGroup entity) {
		for (Permission permission : _permissions.getTarget()) {
			boolean exists = false;
			for (UserGroupXPermission ugxp : _selected.getUserGroupXPermissions())
				if (!permission.equals(ugxp.getPermission()))
					exists = true;

			if (!exists) {
				List rez = _ejbFacade.runNamedQueryRaw("UserGroupXPermission.getNextId", null);
				UserGroupXPermission newUGXP = new UserGroupXPermission();
				newUGXP.setUserGroupXPermissionId((Integer) rez.get(0));
				newUGXP.setUserGroup(entity);
				newUGXP.setPermission(permission);
				entity.addUserGroupXPermission(newUGXP);
			}
		}

		List<UserGroupXPermission> ugxpList = new ArrayList<>(_selected.getUserGroupXPermissions());
		for (UserGroupXPermission ugxp : ugxpList)
			if (!_permissions.getTarget().contains(ugxp.getPermission()))
				entity.removeUserGroupXPermission(ugxp);
	}

	@Override
	protected void afterModify(UserGroup entity) {
		if (!isValidationFailed())
			setPermissions(new DualListModel<>(new ArrayList<Permission>(), new ArrayList<Permission>()));
	}
}

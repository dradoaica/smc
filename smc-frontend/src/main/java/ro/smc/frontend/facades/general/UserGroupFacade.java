package ro.smc.frontend.facades.general;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.UserGroup;
import ro.smc.frontend.entities.general.UserGroupXPermission;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class UserGroupFacade extends AbstractFacade<UserGroup> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public UserGroupFacade() {
		super(UserGroup.class);
	}

	@SuppressWarnings("unused")
	@Override
	protected void onFind(UserGroup entity) {
		for (UserGroupXPermission ugxp : entity.getUserGroupXPermissions()) {
		}
	}

	@Override
	protected void onFind(List<UserGroup> entities) {
		for (UserGroup ug : entities)
			onFind(ug);
	}
}

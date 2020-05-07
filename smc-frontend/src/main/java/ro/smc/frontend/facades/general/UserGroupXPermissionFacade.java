package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.UserGroupXPermission;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class UserGroupXPermissionFacade extends AbstractFacade<UserGroupXPermission> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public UserGroupXPermissionFacade() {
		super(UserGroupXPermission.class);
	}
}

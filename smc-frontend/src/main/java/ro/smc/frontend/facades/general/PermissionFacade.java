package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.Permission;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class PermissionFacade extends AbstractFacade<Permission> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public PermissionFacade() {
		super(Permission.class);
	}
}

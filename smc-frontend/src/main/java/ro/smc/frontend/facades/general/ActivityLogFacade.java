package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.ActivityLog;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class ActivityLogFacade extends AbstractFacade<ActivityLog> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public ActivityLogFacade() {
		super(ActivityLog.class);
	}
}

package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.Environment;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class EnvironmentFacade extends AbstractFacade<Environment> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public EnvironmentFacade() {
		super(Environment.class);
	}
}

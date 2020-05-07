package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.SMCUser;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class SMCUserFacade extends AbstractFacade<SMCUser> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public SMCUserFacade() {
		super(SMCUser.class);
	}
}

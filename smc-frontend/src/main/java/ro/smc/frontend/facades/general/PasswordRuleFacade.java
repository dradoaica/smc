package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.PasswordRule;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class PasswordRuleFacade extends AbstractFacade<PasswordRule> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public PasswordRuleFacade() {
		super(PasswordRule.class);
	}
}

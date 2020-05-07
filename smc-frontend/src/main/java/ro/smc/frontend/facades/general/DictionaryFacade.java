package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.Dictionary;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class DictionaryFacade extends AbstractFacade<Dictionary> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public DictionaryFacade() {
		super(Dictionary.class);
	}
}

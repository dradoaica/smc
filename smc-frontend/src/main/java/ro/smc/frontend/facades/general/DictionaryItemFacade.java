package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.DictionaryItem;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class DictionaryItemFacade extends AbstractFacade<DictionaryItem> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public DictionaryItemFacade() {
		super(DictionaryItem.class);
	}
}

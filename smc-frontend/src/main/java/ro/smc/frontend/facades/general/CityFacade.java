package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.City;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class CityFacade extends AbstractFacade<City> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public CityFacade() {
		super(City.class);
	}
}

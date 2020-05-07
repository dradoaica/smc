package ro.smc.frontend.facades.general;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.Person;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class PersonFacade extends AbstractFacade<Person> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public PersonFacade() {
		super(Person.class);
	}
}

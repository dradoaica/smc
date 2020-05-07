package ro.smc.frontend.facades.general;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ro.smc.frontend.entities.general.EnvDatabase;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class EnvDatabaseFacade extends AbstractFacade<EnvDatabase> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public EnvDatabaseFacade() {
		super(EnvDatabase.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<EnvDatabase> findByEnvironment(int environmentId) {
		Query cq = getEntityManager().createNamedQuery("EnvDatabase.findByEnvironment");
		cq.setParameter("environmentId", environmentId);
		List<EnvDatabase> entities = cq.getResultList();
		onFind(entities);

		return entities;
	}
}

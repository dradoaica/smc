package ro.smc.frontend.facades.configuration;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ro.smc.frontend.entities.configuration.AvailableConfiguration;
import ro.smc.frontend.entities.configuration.AvailablePackage;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class AvailablePackageFacade extends AbstractFacade<AvailablePackage> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public AvailablePackageFacade() {
		super(AvailablePackage.class);
	}

	@SuppressWarnings("unchecked")
	public List<AvailablePackage> findByEnvironment(int environmentId) {
		Query cq = getEntityManager().createNamedQuery("AvailablePackage.findByEnvironment");
		cq.setParameter("environmentId", environmentId);
		List<AvailablePackage> entities = cq.getResultList();
		onFind(entities);

		return entities;
	}

	@SuppressWarnings("unused")
	@Override
	protected void onFind(AvailablePackage entity) {
		for (AvailableConfiguration ac : entity.getConfigurations()) {
		}
	}

	@Override
	protected void onFind(List<AvailablePackage> entities) {
		for (AvailablePackage ap : entities)
			onFind(ap);
	}
}

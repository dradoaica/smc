package ro.smc.frontend.facades.configuration;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ro.smc.frontend.entities.configuration.DeployedConfiguration;
import ro.smc.frontend.entities.configuration.DeployedPackage;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class DeployedPackageFacade extends AbstractFacade<DeployedPackage> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public DeployedPackageFacade() {
		super(DeployedPackage.class);
	}

	@SuppressWarnings("unchecked")
	public List<DeployedPackage> findByEnvironment(int environmentId) {
		Query cq = getEntityManager().createNamedQuery("DeployedPackage.findByEnvironment");
		cq.setParameter("environmentId", environmentId);
		List<DeployedPackage> entities = cq.getResultList();
		onFind(entities);

		return entities;
	}

	@SuppressWarnings("unused")
	@Override
	protected void onFind(DeployedPackage entity) {
		for (DeployedConfiguration dc : entity.getConfigurations()) {
		}
	}

	@Override
	protected void onFind(List<DeployedPackage> entities) {
		for (DeployedPackage dp : entities)
			onFind(dp);
	}
}

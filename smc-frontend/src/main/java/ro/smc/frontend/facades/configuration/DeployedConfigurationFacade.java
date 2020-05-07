package ro.smc.frontend.facades.configuration;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.configuration.DeployedConfiguration;
import ro.smc.frontend.facades.AbstractFacade;

@Stateless
public class DeployedConfigurationFacade extends AbstractFacade<DeployedConfiguration> {
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return _entityManager;
	}

	public DeployedConfigurationFacade() {
		super(DeployedConfiguration.class);
	}
}

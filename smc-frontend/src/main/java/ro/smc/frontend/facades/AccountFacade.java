package ro.smc.frontend.facades;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.smc.frontend.entities.general.Environment;
import ro.smc.frontend.entities.general.SMCUser;
import ro.smc.frontend.facades.general.EnvironmentFacade;
import ro.smc.frontend.facades.general.SMCUserFacade;

@Stateless
public class AccountFacade {
	@Resource
	private SessionContext _context;
	@PersistenceContext(unitName = "SMCPU")
	private EntityManager _entityManager;
	@EJB
	private SMCUserFacade _smcUserFacade;
	@EJB
	private EnvironmentFacade _environmentFacade;

	public SMCUserFacade getSMCUserFacade() {
		return _smcUserFacade;
	}

	public Principal getPrincipal() {
		return _context.getCallerPrincipal();
	}

	public SessionContext getSessionContext() {
		return _context;
	}

	public SMCUser getSMCUserByUserName(String userName) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		SMCUser ret = null;

		parameters.put("userName", userName);
		List<SMCUser> list = _smcUserFacade.runNamedQuery("SMCUser.findByUserName", parameters);
		if (!list.isEmpty())
			ret = list.get(0);

		return ret;
	}

	public SMCUser getSMCUserByCookieUUID(String cookieUUID) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		SMCUser ret = null;

		parameters.put("cookieUUID", cookieUUID);
		List<SMCUser> list = _smcUserFacade.runNamedQuery("SMCUser.findByCookieUUID", parameters);
		if (!list.isEmpty())
			ret = list.get(0);

		return ret;
	}

	public Environment getDefaultEnvironment() {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		Environment ret = null;

		List<Environment> list = _environmentFacade.runNamedQuery("Environment.findAll", parameters);
		if (!list.isEmpty())
			ret = list.get(0);

		return ret;
	}
}

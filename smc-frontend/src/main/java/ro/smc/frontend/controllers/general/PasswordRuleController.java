package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.PasswordRule;
import ro.smc.frontend.facades.general.PasswordRuleFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("passwordRuleController")
@ViewScoped
public class PasswordRuleController extends AbstractController<PasswordRule> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private PasswordRuleFacade _ejbFacade;

	public PasswordRuleController() {
		super(PasswordRule.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(PasswordRule entity) {
		List rez = _ejbFacade.runNamedQueryRaw("PasswordRule.getNextId", null);
		entity.setPasswordRuleId((Integer) rez.get(0));
	}
}

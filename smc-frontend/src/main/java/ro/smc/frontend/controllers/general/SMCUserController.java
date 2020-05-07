package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.controllers.AccountController;
import ro.smc.frontend.entities.general.SMCUser;
import ro.smc.frontend.facades.general.SMCUserFacade;
import ro.smc.frontend.util.AuthUtil;
import ro.smc.frontend.util.CustomException;
import ro.smc.frontend.util.JsfUtil;
import ro.smc.frontend.util.SessionEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("smcUserController")
@ViewScoped
public class SMCUserController extends AbstractController<SMCUser> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private SMCUserFacade _ejbFacade;
	@Inject
	private AccountController _accountController;

	public SMCUserController() {
		super(SMCUser.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	protected void beforeModify(SMCUser entity) {
		if (entity.getNewPassword() != null) {
			if (entity.getPasswordRule() != null) {
				String result = AuthUtil.verifyPasswordAgainstRules(entity.getNewPassword(), entity.getPasswordRule());
				if (result != "")
					throw new CustomException(result);
			}
			entity.setPassword(JsfUtil.hash(entity.getNewPassword()));
		}
		entity.setPasswordChangedByAdmin(_accountController.isCallerInRole(SessionEnum.ADMINGROUP.value()));
		Date pwdCreationDate = new Date();
		entity.setPasswordCreationDate(new Timestamp(pwdCreationDate.getTime()));
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(SMCUser entity) {
		if (entity.getNewPassword() != null) {
			if (entity.getPasswordRule() != null) {
				String result = AuthUtil.verifyPasswordAgainstRules(entity.getNewPassword(), entity.getPasswordRule());
				if (result != "")
					throw new CustomException(result);
			}
			entity.setPassword(JsfUtil.hash(entity.getNewPassword()));
		}

		List rez = _ejbFacade.runNamedQueryRaw("SMCUser.getNextId", null);
		entity.setUserId((Integer) rez.get(0));
		entity.setPasswordChangedByAdmin(_accountController.isCallerInRole(SessionEnum.ADMINGROUP.value()));
		Date pwdCreationDate = new Date();
		entity.setPasswordCreationDate(new Timestamp(pwdCreationDate.getTime()));
	}
}

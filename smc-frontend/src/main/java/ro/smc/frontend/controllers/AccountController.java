package ro.smc.frontend.controllers;

import ro.smc.frontend.entities.general.SMCUser;
import ro.smc.frontend.entities.general.Environment;
import ro.smc.frontend.entities.general.Person;
import ro.smc.frontend.facades.AccountFacade;
import ro.smc.frontend.facades.general.SMCUserFacade;
import ro.smc.frontend.facades.general.PersonFacade;
import ro.smc.frontend.util.AuthUtil;
import ro.smc.frontend.util.CustomException;
import ro.smc.frontend.util.JsfUtil;
import ro.smc.frontend.util.SessionEnum;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named("accountController")
@SessionScoped
public class AccountController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private AccountFacade _accountFacade;
	@EJB
	private SMCUserFacade _smcUserFacade;
	@EJB
	private PersonFacade _personFacade;
	private SMCUser _current;
	private String _lastLoginName;
	private String _password;
	private String _oldPassword;
	private String _newPassword;
	private String _confirmNewPassword;
	private boolean _rememberMe;
	private Environment _auxEnvironment;
	private Environment _currentEnvironment;

	public AccountController() {
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	private HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public boolean isCallerInRole(String role) {
		return _accountFacade.getSessionContext().isCallerInRole(role);
	}

	public String login() {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		SMCUser smcUser = (SMCUser) request.getSession().getAttribute(SessionEnum.USERATTRIBUTE.value());

		try {
			if (smcUser == null) {
				smcUser = _accountFacade.getSMCUserByUserName(getLastLoginName());

				if (smcUser == null)
					throw new CustomException(ResourceBundle.getBundle("/Bundle").getString("ERR_INVALID_USER_NAME"));
				else if (smcUser.getInactive())
					throw new CustomException(ResourceBundle.getBundle("/Bundle").getString("ERR_INACTIVE_USER"));
				else if (!smcUser.getPassword().equals(JsfUtil.hash(_password)))
					throw new CustomException(ResourceBundle.getBundle("/Bundle").getString("ERR_INVALID_PASSWORD"));

				request.login(_lastLoginName, smcUser.getPassword());
				request.getSession().setAttribute(SessionEnum.USERATTRIBUTE.value(), smcUser);

				JsfUtil.addCookie(response, SessionEnum.LASTLOGINCOOKIE.value(), _lastLoginName, Integer.MAX_VALUE);

				if (_rememberMe) {
					String uuid = UUID.randomUUID().toString();
					smcUser.setCookieUUID(uuid);
					_accountFacade.getSMCUserFacade().modify(smcUser);
					JsfUtil.addCookie(response, SessionEnum.AUTHCOOKIE.value(), JsfUtil.encrypt(uuid),
							Integer.MAX_VALUE);
				} else {
					smcUser.setCookieUUID(null);
					_accountFacade.getSMCUserFacade().modify(smcUser);
					JsfUtil.removeCookie(response, SessionEnum.AUTHCOOKIE.value());
				}
			}
		} catch (CustomException cex) {
			JsfUtil.addErrorMessage(cex, "Error on logging in");
			return null;
		} catch (Exception ex) {
			return "/error?faces-redirect=true";
		}

		return "/index";
	}

	public String logOut() throws ServletException {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();

		request.logout();
		request.getSession().invalidate();

		JsfUtil.removeCookie(response, SessionEnum.AUTHCOOKIE.value());

		return "/login?faces-redirect=true";
	}

	public void saveAccount() {
		SMCUser smcUser = getMySelf();

		try {
			if (_oldPassword == null)
				throw new CustomException(ResourceBundle.getBundle("/Bundle").getString("ERR_OLD_PASSWORD_IS_EMPTY"));

			if (_newPassword == null)
				throw new CustomException(ResourceBundle.getBundle("/Bundle").getString("ERR_NEW_PASSWORD_IS_EMPTY"));

			if (_confirmNewPassword == null)
				throw new CustomException(
						ResourceBundle.getBundle("/Bundle").getString("ERR_CONFIRM_NEW_PASSWORD_IS_EMPTY"));

			if (smcUser.getPasswordRule() != null) {
				String result = AuthUtil.canChangePassword(smcUser.getPasswordRule());
				if (result != "" && !isCallerInRole(SessionEnum.ADMINGROUP.value()))
					throw new CustomException(result);
			}

			if (!smcUser.getPassword().equals(JsfUtil.hash(_oldPassword)))
				throw new CustomException(ResourceBundle.getBundle("/Bundle").getString("ERR_INVALID_OLD_PASSWORD"));

			if (!_newPassword.equals(_confirmNewPassword))
				throw new CustomException(
						ResourceBundle.getBundle("/Bundle").getString("ERR_NEW_PASSWORD_NOT_CONFIRMED"));

			if (smcUser.getPasswordRule() != null) {
				String result = AuthUtil.verifyPasswordAgainstRules(_newPassword, smcUser.getPasswordRule());
				if (result != "")
					throw new CustomException(result);
			}

			smcUser.setPassword(JsfUtil.hash(_newPassword));
			_smcUserFacade.modify(smcUser);

			JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MSG_PASSWORD_CHANGED"));
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "Error on persisting data");
		}
	}

	public void saveProfile() {
		Person person = getMySelf().getPerson();

		try {
			_personFacade.modify(person);

			JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MSG_PROFILE_SAVED"));
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "Error on persisting data");
		}
	}

	public String changeCurrentEnvironment() throws IOException {
		_currentEnvironment = _auxEnvironment;

		return "/index";
	}

	public void prepare4ChangeCurrentEnvironment() {
		_auxEnvironment = getCurrentEnvironment();
	}

	public SMCUser getMySelf() {
		if (_current == null)
			_current = _accountFacade.getSMCUserByUserName(getLastLoginName());

		return _current;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public boolean getRememberMe() {
		return _rememberMe;
	}

	public void setRememberMe(boolean remember) {
		_rememberMe = remember;
	}

	public String getLastLoginName() {
		HttpServletRequest request = getRequest();

		if (_lastLoginName == null)
			_lastLoginName = JsfUtil.getCookieValue(request, SessionEnum.LASTLOGINCOOKIE.value());

		return _lastLoginName;
	}

	public void setLastLoginName(String lastLoginName) {
		_lastLoginName = lastLoginName;
	}

	public String getConfirmNewPassword() {
		if (_confirmNewPassword != null)
			_confirmNewPassword = null;

		return _confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		_confirmNewPassword = confirmNewPassword;
	}

	public String getNewPassword() {
		if (_newPassword != null)
			_newPassword = null;

		return _newPassword;
	}

	public void setNewPassword(String newPassword) {
		_newPassword = newPassword;
	}

	public String getOldPassword() {
		if (_oldPassword != null)
			_oldPassword = null;

		return _oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		_oldPassword = oldPassword;
	}

	public Environment getCurrentEnvironment() {
		if (_currentEnvironment == null)
			_currentEnvironment = _accountFacade.getDefaultEnvironment();

		return _currentEnvironment;
	}

	public Environment getAuxEnvironment() {
		return _auxEnvironment;
	}

	public void setAuxEnvironment(Environment auxEnvironment) {
		_auxEnvironment = auxEnvironment;
	}

}

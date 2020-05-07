package ro.smc.frontend.filters;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.smc.frontend.entities.general.SMCUser;
import ro.smc.frontend.facades.AccountFacade;
import ro.smc.frontend.util.JsfUtil;
import ro.smc.frontend.util.SessionEnum;

public class ApplicationFilter implements Filter {
	@EJB
	private AccountFacade _accountFacade;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		SMCUser smcUser = (SMCUser) request.getSession().getAttribute(SessionEnum.USERATTRIBUTE.value());

		if (smcUser == null) {
			String cookieValue = JsfUtil.getCookieValue(request, SessionEnum.AUTHCOOKIE.value());

			if (cookieValue != null) {
				smcUser = _accountFacade.getSMCUserByCookieUUID(JsfUtil.decrypt(cookieValue));

				if (smcUser != null) {
					request.login(smcUser.getUserName(), smcUser.getPassword());
					request.getSession().setAttribute(SessionEnum.USERATTRIBUTE.value(), smcUser);
					JsfUtil.addCookie(response, SessionEnum.AUTHCOOKIE.value(), cookieValue, Integer.MAX_VALUE);
				} else
					JsfUtil.removeCookie(response, SessionEnum.AUTHCOOKIE.value());
			}
		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
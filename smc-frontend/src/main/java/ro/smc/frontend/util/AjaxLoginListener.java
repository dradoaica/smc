package ro.smc.frontend.util;

import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

public class AjaxLoginListener implements PhaseListener {
	private static final long serialVersionUID = 1L;

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String originalURL = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
		String loginURL = request.getContextPath() + "/login.xhtml";

		if (context.getPartialViewContext().isAjaxRequest() && originalURL != null
				&& loginURL.equals(request.getRequestURI())) {
			request.getSession().invalidate();
			throw new ViewExpiredException("Ajax_ViewExpiredException",
					FacesContext.getCurrentInstance().getViewRoot().getViewId());
		}
	}
}
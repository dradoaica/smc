package ro.smc.frontend.controllers.general;

import ro.smc.frontend.controllers.AbstractController;
import ro.smc.frontend.entities.general.ActivityLog;
import ro.smc.frontend.facades.general.ActivityLogFacade;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("activityLogController")
@ViewScoped
public class ActivityLogController extends AbstractController<ActivityLog> implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ActivityLogFacade _ejbFacade;

	public ActivityLogController() {
		super(ActivityLog.class);
	}

	@PostConstruct
	public void init() {
		super.setFacade(_ejbFacade);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void beforeAdd(ActivityLog entity) {
		List rez = _ejbFacade.runNamedQueryRaw("ActivityLog.getNextId", null);
		entity.setActivityLogId((Integer) rez.get(0));
	}
}

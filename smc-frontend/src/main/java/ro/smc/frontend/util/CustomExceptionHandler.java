package ro.smc.frontend.util;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {
	private ExceptionHandler _wrapped;

	public CustomExceptionHandler(ExceptionHandler wrapped) {
		_wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return _wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterable<ExceptionQueuedEvent> events = _wrapped.getUnhandledExceptionQueuedEvents();
		for (Iterator<ExceptionQueuedEvent> it = events.iterator(); it.hasNext();) {
			ExceptionQueuedEvent eqe = it.next();
			ExceptionQueuedEventContext eqec = eqe.getContext();

			if (eqec.getException() instanceof ViewExpiredException) {
				ExternalContext ext = eqec.getContext().getExternalContext();
				try {
					ext.redirect(ext.getRequestContextPath() + "/login.xhtml");
				} catch (IOException ex) {
				} finally {
					it.remove();
				}
			}

			_wrapped.handle();
		}
	}
}

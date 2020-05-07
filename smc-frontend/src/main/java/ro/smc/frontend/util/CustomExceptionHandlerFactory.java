package ro.smc.frontend.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {
	private ExceptionHandlerFactory _base;

	public CustomExceptionHandlerFactory(ExceptionHandlerFactory base) {
		_base = base;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new CustomExceptionHandler(_base.getExceptionHandler());
	}
}
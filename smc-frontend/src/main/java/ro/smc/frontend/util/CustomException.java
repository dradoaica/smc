package ro.smc.frontend.util;

public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String _message = "";

	public CustomException(String message) {
		_message = message;
	}

	@Override
	public String getMessage() {
		return _message;
	}
}

package ro.smc.engine.providers;

public class SqlObjectNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public SqlObjectNotFoundException(String message) {
		super(message);
	}
}

package ro.smc.engine.providers;

public class SqlObjectInvalidReferencesException extends Exception {
	private static final long serialVersionUID = 1L;

	public SqlObjectInvalidReferencesException(String message, Exception innerException) {
		super(message, innerException);
	}
}

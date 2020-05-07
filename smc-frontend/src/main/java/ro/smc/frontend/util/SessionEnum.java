package ro.smc.frontend.util;

public enum SessionEnum {
	USERATTRIBUTE("smcUser"),
	AUTHCOOKIE("AUTH"),
	LASTLOGINCOOKIE("LASTLOGIN"),
	ADMINGROUP("Admin");
	
	private final String value;
	
	SessionEnum(String value) { 
		this.value = value;
	}
	
	public String value() {
		return value; 
	}
}

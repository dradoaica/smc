package ro.smc.engine.util;

public final class StringUtil {
	public static boolean isNullOrEmpty(String str) {
		if (str == null)
			return true;

		if (str == "")
			return true;

		return false;
	}

	public static boolean isNullOrWhiteSpace(String str) {
		if (isNullOrEmpty(str))
			return true;

		if (str.trim() == "")
			return true;

		return false;
	}
}

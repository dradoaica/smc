package ro.smc.frontend.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.smc.frontend.util.smcConfig.SmcConfigInfo;

public class JsfUtil {
	private static final String C_INITIAL_VECTOR = "1048543954393284";

	public static String encrypt(String str) {
		CryptoUtil crypto = new CryptoUtil();
		String encryptionKey = null;
		try {
			encryptionKey = SmcConfigInfo.getSmcConfig().getEncryptionKey();
		} catch (Exception ex) {
			System.err.println("Problem retrieving encryption key");
			ex.printStackTrace();
		}

		return crypto.encrypt(str, C_INITIAL_VECTOR, encryptionKey);
	}

	public static String decrypt(String str) {
		CryptoUtil crypto = new CryptoUtil();
		String encryptionKey = null;
		try {
			encryptionKey = SmcConfigInfo.getSmcConfig().getEncryptionKey();
		} catch (Exception ex) {
			System.err.println("Problem retrieving encryption key");
			ex.printStackTrace();
		}

		return crypto.decrypt(str, C_INITIAL_VECTOR, encryptionKey);
	}

	public static String hash(String str) {
		CryptoUtil crypto = new CryptoUtil();

		return crypto.hash(str);
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (name.equals(cookie.getName()))
					return cookie.getValue();

		return null;
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, null, 0);
	}

	public static String getErrorMessage(Exception ex) {
		Throwable th = ex;
		while (th.getCause() != null)
			th = th.getCause();

		return th.getLocalizedMessage();
	}

	public static void addErrorMessage(Exception ex, String defaultMsg) {
		String msg = getErrorMessage(ex);
		if (msg != null && msg.length() > 0)
			addErrorMessage(msg);
		else
			addErrorMessage(defaultMsg);
	}

	public static void addErrorMessages(List<String> messages) {
		for (String message : messages)
			addErrorMessage(message);
	}

	public static void addErrorMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		FacesContext.getCurrentInstance().validationFailed();
	}

	public static void addSuccessMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static Throwable getRootCause(Throwable cause) {
		if (cause != null) {
			Throwable source = cause.getCause();
			if (source != null)
				return getRootCause(source);
			else
				return cause;
		}

		return null;
	}

	public static boolean isValidationFailed() {
		return FacesContext.getCurrentInstance().isValidationFailed();
	}

	public static boolean isDummySelectItem(UIComponent component, String value) {
		for (UIComponent children : component.getChildren())
			if (children instanceof UISelectItem) {
				UISelectItem item = (UISelectItem) children;
				if (item.getItemValue() == null && item.getItemLabel().equals(value))
					return true;

				break;
			}

		return false;
	}
}
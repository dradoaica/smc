package ro.smc.frontend.util;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ro.smc.frontend.entities.general.PasswordRule;

public class AuthUtil {
	public static String verifyPasswordAgainstRules(String password, PasswordRule passwordRule) {
		String result = "";
		if (result == "")
			result = checkPasswordLen(password, passwordRule.getPasswordMinLen());
		if (result == "")
			result = checkMinDigitNumber(password, passwordRule.getMinDigitNumber());
		if (result == "")
			result = checkMinLetterNumber(password, passwordRule.getMinAlphaCharNumber());
		if (result == "")
			result = checkMinSmallLetterNumber(password, passwordRule.getMinLowerCaseNumber());
		if (result == "")
			result = checkMinUpperLetterNumber(password, passwordRule.getMinUpperCaseNumber());
		if (result == "")
			result = checkMinSpecialCharNumber(password, passwordRule.getMinSpecialCharNumber(),
					passwordRule.getSpecialCharList());

		return result;
	}

	public static String canChangePassword(PasswordRule passwordRule) {
		if (!passwordRule.getAllowChangePassword())
			return ResourceBundle.getBundle("/Bundle").getString("ERR_USER_CANNOT_CHANGE_PASSWORD");

		return "";
	}

	private static String checkPasswordLen(String password, int minLen) {
		if ((minLen > 0) && (password.length() < minLen))
			return ResourceBundle.getBundle("/Bundle").getString("ERR_PASSWORD_IS_TOO_SHORT");

		return "";
	}

	private static String checkMinDigitNumber(String password, int minDigitNumber) {
		int count = 0;

		if (minDigitNumber <= 0)
			return "";

		char[] charArray = password.toCharArray();
		for (int i = 0; i < charArray.length; i++)
			if ("1234567890".indexOf(charArray[i]) != -1)
				count++;

		if (count < minDigitNumber)
			return ResourceBundle.getBundle("/Bundle").getString("ERR_NOT_ENOUGH_DIGITS");

		return "";
	}

	private static String checkMinLetterNumber(String password, int minLetterNumber) {
		Pattern pattern;
		Matcher matcher;
		int count = 0;

		if (minLetterNumber <= 0)
			return "";

		pattern = Pattern.compile("\\p{L}");
		matcher = pattern.matcher(password);

		while (matcher.find())
			count++;

		if (count < minLetterNumber)
			return ResourceBundle.getBundle("/Bundle").getString("ERR_NOT_ENOUGH_LETTERS");

		return "";
	}

	private static String checkMinSpecialCharNumber(String password, int minSpecialCharNumber, String specialCharList) {
		int count = 0;

		if (minSpecialCharNumber <= 0)
			return "";

		if (((specialCharList == null) ? "" : specialCharList) == "")
			return "";

		char[] charArray = password.toCharArray();
		for (int i = 0; i < charArray.length; i++)
			if (specialCharList.indexOf(charArray[i]) != -1)
				count++;

		if (count < minSpecialCharNumber)
			return ResourceBundle.getBundle("/Bundle").getString("ERR_NOT_ENOUGH_SPECIAL_CHAR");

		return "";
	}

	private static String checkMinSmallLetterNumber(String password, int minSmallLetterNumber) {
		Pattern pattern;
		Matcher matcher;
		int count = 0;

		if (minSmallLetterNumber <= 0)
			return "";

		pattern = Pattern.compile("[a-z]");
		matcher = pattern.matcher(password);

		while (matcher.find())
			count++;

		if (count < minSmallLetterNumber)
			return ResourceBundle.getBundle("/Bundle").getString("ERR_NOT_ENOUGH_SMALL_LETTERS");

		return "";
	}

	private static String checkMinUpperLetterNumber(String password, int minUpperLetterNumber) {
		Pattern pattern;
		Matcher matcher;
		int count = 0;

		if (minUpperLetterNumber <= 0)
			return "";

		pattern = Pattern.compile("[A-Z]");
		matcher = pattern.matcher(password);

		while (matcher.find())
			count++;

		if (count < minUpperLetterNumber)
			return ResourceBundle.getBundle("/Bundle").getString("ERR_NOT_ENOUGH_UPPER_LETTERS");

		return "";
	}

}

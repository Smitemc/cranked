
package me.sniperzciinema.cranked.Messages;

public class StringUtil {

	public static String getWord(String string) {
		String s = string;
		if (string != null)
			s = s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(s.charAt(0)).toUpperCase());
		return s;
	}
}

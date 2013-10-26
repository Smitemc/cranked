
package me.sniperzciinema.cranked.Messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class ScoreBoardVariables {

	public static String getLine(String string, Player user) {

		String newString = string;
		// Replace all variables we need

		// TODO: Add variables...

		// Replace color codes
		newString = ChatColor.translateAlternateColorCodes('&', ChatColor.stripColor(newString));

		// Make sure string isnt to long
		if (newString.length() > 16)
			newString = newString.substring(0, Math.min(newString.length(), 16));

		return newString;
	}
}

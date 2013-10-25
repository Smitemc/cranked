package me.sniperzciinema.cranked.Messages;

import java.util.Random;

import me.sniperzciinema.cranked.GameMechanics.DeathTypes;
import me.sniperzciinema.cranked.Tools.Files;

import org.bukkit.ChatColor;


public class DeathMessages {

	public static String getDeathMessage(DeathTypes death, String killer, String killed) {
		Random r = new Random();
		int i = r.nextInt(Files.getMessages().getStringList("Deaths." + death.toString()).size());
		String msg = Files.getMessages().getStringList("Deaths." + death.toString()).get(i);
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		msg = msg.replaceAll("<killer>", killer);
		msg = msg.replaceAll("<killed>", killed);
		return msg;
	}
}

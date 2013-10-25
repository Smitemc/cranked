package me.sniperzciinema.cranked.Messages;

import java.util.Random;

import me.sniperzciinema.cranked.GameMechanics.DeathTypes;
import me.sniperzciinema.cranked.Tools.Files;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class DeathMessages {

	public static String getDeathMessage(Player killer, Player killed, DeathTypes death) {
		Random r = new Random();
		int i = r.nextInt(Files.getMessages().getStringList("Deaths." + death.toString()).size());
		String msg = Files.getMessages().getStringList("Deaths." + death.toString()).get(i);
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		if(killer != null)
			msg = msg.replaceAll("<killer>", killer.getName());
		msg = msg.replaceAll("<killed>", killed.getName());
		return msg;
	}
}

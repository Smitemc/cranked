
package me.sniperzciinema.cranked.Messages;

import java.util.Random;

import me.sniperzciinema.cranked.GameMechanics.DeathTypes;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import me.sniperzciinema.cranked.Tools.Files;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class DeathMessages {

	// Get the death message
	public static String getDeathMessage(Player killer, Player killed, DeathTypes death) {
		// Get a random message from the death type's messages
		Random r = new Random();
		int i = r.nextInt(Files.getMessages().getStringList("Deaths." + death.toString()).size());
		String msg = Files.getMessages().getStringList("Deaths." + death.toString()).get(i);

		// Replace color codes, and killer and killed names
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		if (killer != null)
			msg = msg.replaceAll("<killer>", killer.getName()+"("+CPlayerManager.getCrankedPlayer(killer).getPoints()+")");
		if (killed != null)
		msg = msg.replaceAll("<killed>", killed.getName() +"("+CPlayerManager.getCrankedPlayer(killed).getPoints()+")");
		return msg;
	}
}

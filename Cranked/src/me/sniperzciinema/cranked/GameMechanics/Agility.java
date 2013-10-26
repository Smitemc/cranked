
package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.Tools.Settings;

import org.bukkit.entity.Player;


public class Agility {

	// Method that speeds up the player
	public static void speedUp(Player p, boolean byKillStreak) {
		// Get the arenas settings
		Settings Settings = new Settings(ArenaManager.getArena(p));
		// Are we doing this by killstreaks(Waiting has speed too)
		if (byKillStreak)
		{
			// Get their kills and the regular speed
			int killStreak = Stats.getKills(p.getName());
			float regularSpeed = 0.2F;
			// Set their speed accordingly
			p.setWalkSpeed(regularSpeed + (float) ((killStreak > 0) ? (Settings.getBonusSpeed() * killStreak) : 0));
		} else
			// Set their speed to what ever the Waiting Bonus Speed it
			p.setWalkSpeed(Settings.getWaitingSpeed());
	}

	public static void resetSpeed(Player p) {
		// Reset the players walking speed
		p.setWalkSpeed(0.2F);
	}

}

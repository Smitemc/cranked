
package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.ArenaClasses.ArenaManager;
import me.sniperzciinema.cranked.Tools.Settings;

import org.bukkit.entity.Player;


public class Agility {


	
	public static void speedUp(Player p, boolean byKillStreak) {
		Settings Settings = new Settings(ArenaManager.getArena(p));
		if(byKillStreak){
			int killStreak = Stats.getKills(p.getName());
			float regularSpeed = 0.2F;
			p.setWalkSpeed(regularSpeed + (float)((killStreak >0) ? (Settings.getBonusSpeed()*killStreak) : 0));
			p.sendMessage(String.valueOf(p.getWalkSpeed()));
		}
		else
			p.setWalkSpeed(Settings.getPreGameSpeed());
	}
	public static void resetSpeed(Player p){
		p.setWalkSpeed(0.2F);
	}

}

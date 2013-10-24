
package me.sniperzciinema.cranked.GameMechanics;

import org.bukkit.entity.Player;


public class Agility {

	public static void speedUp(Player p, boolean byKillStreak) {
		if(byKillStreak){
			int killStreak = Stats.getKills(p.getName());
			float regularSpeed = 0.2F;
			p.setWalkSpeed(regularSpeed + (float)(0.05*killStreak));
			p.sendMessage(String.valueOf(regularSpeed + (float)(0.05*killStreak)));
		}
		else
			p.setWalkSpeed(0.4F);
	}
	public static void resetSpeed(Player p){
		p.setWalkSpeed(0.2F);
	}

}

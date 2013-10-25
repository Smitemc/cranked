
package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.Tools.Settings;

import org.bukkit.Sound;
import org.bukkit.entity.Player;


public class Equip {
	
	@SuppressWarnings("deprecation")
	public static void equipPlayer(Player p) {
		Settings Settings = new Settings(ArenaManager.getArena(p));
		p.playSound(p.getLocation(), Sound.ANVIL_USE, 1, 1);

		p.getInventory().setHelmet(Settings.getDefaultHead());
		p.getInventory().setChestplate(Settings.getDefaultChest());
		p.getInventory().setLeggings(Settings.getDefaultLegs());
		p.getInventory().setBoots(Settings.getDefaultFeet());
		p.getInventory().setContents(Settings.getDefaultItems());
		p.updateInventory();
	}
}

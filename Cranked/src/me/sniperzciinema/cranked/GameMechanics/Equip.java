
package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.Tools.Handlers.ItemHandler;

import org.bukkit.Sound;
import org.bukkit.entity.Player;


public class Equip {

	@SuppressWarnings("deprecation")
	public static void equipPlayer(Player p) {
		p.playSound(p.getLocation(), Sound.ANVIL_USE, 1, 1);

		if (Main.me.getConfig().getString("Armor.Human.Head") != null)
			p.getInventory().setHelmet(ItemHandler.getItemStack(Main.me.getConfig().getString("Armor.Human.Head")));
		if (Main.me.getConfig().getString("Armor.Human.Chest") != null)
			p.getInventory().setChestplate(ItemHandler.getItemStack(Main.me.getConfig().getString("Armor.Human.Chest")));
		if (Main.me.getConfig().getString("Armor.Human.Legs") != null)
			p.getInventory().setLeggings(ItemHandler.getItemStack(Main.me.getConfig().getString("Armor.Human.Legs")));
		if (Main.me.getConfig().getString("Armor.Human.Feet") != null)
			p.getInventory().setBoots(ItemHandler.getItemStack(Main.me.getConfig().getString("Armor.Human.Feet")));
		p.updateInventory();
	}
}

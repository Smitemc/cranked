
package me.sniperzciinema.cranked.Extras;

import java.util.Random;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.ArenaClasses.Arena;
import me.sniperzciinema.cranked.ArenaClasses.ArenaManager;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.Tools.IconMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class Menus {

	@SuppressWarnings("deprecation")
	public static void chooseArena(final Player player) {
		IconMenu menu = new IconMenu(
				ChatColor.GREEN + player.getName() + "- Join", ((ArenaManager.getArenas().size() / 9) * 9) + 9,
				new IconMenu.OptionClickEventHandler()
				{

					@Override
					public void onOptionClick(final IconMenu.OptionClickEvent event) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.me, new Runnable()
						{

							public void run() {
								if(ArenaManager.isArenaValid(ChatColor.stripColor(event.getName())))
									event.getPlayer().performCommand("Cranked Join " + ChatColor.stripColor(event.getName()));
								else
									event.getPlayer().sendMessage(Msgs.Error_Missing_Spawns.getString());
							}
						}, 2);
					}
				}, Main.me);
		int place = 0;
		for(Arena arena : ArenaManager.getArenas()){
			Random r = new Random();
			int i = r.nextInt(173)+1;
		
			while( Material.getMaterial(i) == null || !Material.getMaterial(i).isBlock() || !Material.getMaterial(i).isOccluding() || !Material.getMaterial(i).isSolid()  || Material.getMaterial(i).isTransparent()){
				i = r.nextInt(174);
			}
			if(ArenaManager.isArenaValid(arena.getName()))
				menu.setOption(place, new ItemStack(Material.getMaterial(i)), ChatColor.getByChar(String.valueOf(place+1)) + arena.getName(), ChatColor.GREEN + "Click Here Join This Arena", "", ChatColor.YELLOW + "Playing: "+arena.getPlayers().size(),ChatColor.YELLOW + "State: "+arena.getState(), "", ChatColor.AQUA + "Creator: "+ ChatColor.WHITE+arena.getCreator());
			else
				menu.setOption(place, new ItemStack(Material.getMaterial(i)), ChatColor.DARK_RED + arena.getName(), ChatColor.RED + "Arenas Missing Spawns!", "", ChatColor.AQUA + "Creator: "+ ChatColor.WHITE+arena.getCreator());
					
			place++;
		}
		menu.open(player);
	}
}

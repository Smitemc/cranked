
package me.sniperzciinema.cranked;

import java.util.HashMap;

import me.sniperzciinema.cranked.Tools.Files;
import me.sniperzciinema.cranked.Tools.StringUtil;
import me.sniperzciinema.cranked.Tools.Handlers.Arena;
import me.sniperzciinema.cranked.Tools.Handlers.ArenaManager;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private Listeners listeners = new Listeners(this);
	public static Plugin me;
	public static String cranked = "" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "-[" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Cranked" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "]-" + ChatColor.GRAY;

	public HashMap<String, String> creating = new HashMap<String, String>();
	
	
	public void onEnable(){
		 me = this;
		// Register the event listeners
		getServer().getPluginManager().registerEvents(listeners, this);
		getCommand("Cranked").setExecutor(new Commands(this));
		
		
		//Create the default config.yml
		getConfig().options().copyDefaults();
		saveConfig();
		
		if(Files.getArenas().getConfigurationSection("Arenas") != null)
			for (String s : Files.getArenas().getConfigurationSection("Arenas").getKeys(false)) {
				Arena arena = new Arena(StringUtil.getWord(s));
				ArenaManager.loadArena(arena);
				System.out.println("Loaded Arena: "+ arena);
			}
		else
			System.out.println("Couldn't Loaded Any Arenas");
	}
	
	public void onDisable(){
	}
	
}


package me.sniperzciinema.cranked;

import java.sql.Connection;
import me.sniperzciinema.cranked.GameMechanics.CrackedPlayer;
import me.sniperzciinema.cranked.GameMechanics.CrackedPlayerManager;
import me.sniperzciinema.cranked.Tools.Files;
import me.sniperzciinema.cranked.Tools.Msgs;
import me.sniperzciinema.cranked.Tools.StringUtil;
import me.sniperzciinema.cranked.Tools.Handlers.Arena;
import me.sniperzciinema.cranked.Tools.Handlers.ArenaManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import code.husky.mysql.MySQL;


public class Main extends JavaPlugin {

	private Listeners listeners = new Listeners(this);
	public static Plugin me;
	public static String cranked = "" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "-[" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Cranked" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "]-" + ChatColor.GRAY;

	public static MySQL MySQL = null;
	public static Connection c = null;

	public void onEnable() {
		me = this;
		// Register the event listeners
		getServer().getPluginManager().registerEvents(listeners, this);
		getCommand("Cranked").setExecutor(new Commands(this));

		// Create the default config.yml
		getConfig().options().copyDefaults();
		saveConfig();

		for (Player p : Bukkit.getOnlinePlayers())
		{
			CrackedPlayer cp = new CrackedPlayer(p);
			CrackedPlayerManager.loadCrackedPlayer(cp);
		}
		if (Files.getArenas().getConfigurationSection("Arenas") != null)
			for (String s : Files.getArenas().getConfigurationSection("Arenas").getKeys(false))
			{
				Arena arena = new Arena(StringUtil.getWord(s));
				ArenaManager.loadArena(arena);
				System.out.println("Loaded Arena: " + arena);
			}
		else
			System.out.println("Couldn't Loaded Any Arenas");
		if (getConfig().getBoolean("MySQL.Enable"))
		{
			MySQL = new MySQL(this, getConfig().getString("MySQL.Host"),
					getConfig().getString("MySQL.Port"),
					getConfig().getString("MySQL.Database"),
					getConfig().getString("MySQL.User"),
					getConfig().getString("MySQL.Pass"));
			c = MySQL.openConnection();
		}

	}

	public void onDisable() {
		if (!CrackedPlayerManager.getPlayers().isEmpty())
			for (CrackedPlayer cp : CrackedPlayerManager.getPlayers())
			{
				if(cp.getArena() != null){
					cp.getPlayer().sendMessage(Msgs.Error_Plugin_Unload.getString());
					cp.reset();
				}
			}
		if (getConfig().getBoolean("MySQL.Enable"))
		{
			MySQL.closeConnection();
		}
	}

}

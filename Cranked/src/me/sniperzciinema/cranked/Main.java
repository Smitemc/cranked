
package me.sniperzciinema.cranked;

import java.sql.Connection;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.Listeners.RegisterAndUnRegister;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.Messages.StringUtil;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import me.sniperzciinema.cranked.Tools.Files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import code.husky.mysql.MySQL;


public class Main extends JavaPlugin {

	private RegisterAndUnRegister listeners = new RegisterAndUnRegister(this);
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
		Files.getArenas().options().copyDefaults(true);
		Files.saveArenas();
		Files.getPlayers().options().copyDefaults(true);
		Files.savePlayers();
		Files.getArenas().options().copyDefaults(true);
		Files.saveArenas();
		Files.getMessages().options().copyDefaults(true);
		Files.saveMessages();
		Files.getConfig().options().copyDefaults(true);
		Files.saveConfig();
		

		for (Player p : Bukkit.getOnlinePlayers())
		{
			CPlayer cp = new CPlayer(p);
			CPlayerManager.loadCrackedPlayer(cp);
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
		if (!CPlayerManager.getPlayers().isEmpty())
			for (CPlayer cp : CPlayerManager.getPlayers())
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

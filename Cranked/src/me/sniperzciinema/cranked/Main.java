
package me.sniperzciinema.cranked;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.Listeners.CrackShotApi;
import me.sniperzciinema.cranked.Listeners.DamageEvents;
import me.sniperzciinema.cranked.Listeners.RankingsToggle;
import me.sniperzciinema.cranked.Listeners.RegisterAndUnRegister;
import me.sniperzciinema.cranked.Listeners.MiscListeners;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.Messages.StringUtil;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import me.sniperzciinema.cranked.Tools.Files;
import me.sniperzciinema.cranked.Tools.Metrics;
import me.sniperzciinema.cranked.Tools.Updater;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import code.husky.mysql.MySQL;


public class Main extends JavaPlugin {

	public static Plugin me;
	public static String cranked = "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "«" + ChatColor.GOLD + ChatColor.BOLD + "Cranked" + ChatColor.DARK_GRAY + ChatColor.BOLD + "»" + ChatColor.DARK_AQUA + " ";

	public static MySQL MySQL = null;
	public static Connection c = null;
	
	public static boolean update;
	public static String name;

	public void onEnable() {

		System.out.println("===== Cranked =====");
		if (getConfig().getBoolean("Check For Updates.Enable"))
		{
			try
			{
				Updater updater = new Updater(this, 0/*NEED TO UPLOAD FIRST TO GET ID*/, getFile(),
						Updater.UpdateType.NO_DOWNLOAD, false);

				Main.update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
				Main.name = updater.getLatestName();

				System.out.println(this.getDescription().getVersion().replaceAll(".", ""));
				System.out.println(updater.getLatestFileVersion().replaceAll(".", ""));

				int currentVersion = Integer.valueOf(this.getDescription().getVersion().replaceAll("\\.", ""));
				int newVersion = Integer.valueOf(updater.getLatestFileVersion().replaceAll("\\.", ""));

				if (currentVersion >= newVersion)
				{
					System.out.println("You are running a beta version of Cranked!");
					update = false;
				}

				else
					System.out.println("You need to update Cranked to: " + updater.getLatestFileVersion());

			} catch (Exception ex)
			{
				System.out.println("The auto-updater tried to contact dev.bukkit.org, but was unsuccessful.");
			}
		}
		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
			System.out.println("Metrics was started!");
		} catch (IOException e)
		{
			System.out.println("Metrics was unable to start...");
		}
		
		me = this;
		// Register the event listeners

		DamageEvents damage = new DamageEvents(this);
		getServer().getPluginManager().registerEvents(damage, this);
		RegisterAndUnRegister register = new RegisterAndUnRegister(this);
		getServer().getPluginManager().registerEvents(register, this);
		MiscListeners MiscListeners = new MiscListeners(this);
		getServer().getPluginManager().registerEvents(MiscListeners, this);
		RankingsToggle RankingsToggle = new RankingsToggle();
		getServer().getPluginManager().registerEvents(RankingsToggle, this);
		
		if(getServer().getPluginManager().getPlugin("CrackShot") != null){
			CrackShotApi CrackShotApi = new CrackShotApi(this);
			getServer().getPluginManager().registerEvents(CrackShotApi, this);
			System.out.println("CrackShot is loaded up and ready for use!");
		}
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
			CPlayerManager.loadCrankedPlayer(cp);
		}
		System.out.println("Loading Arenas...");
		if (Files.getArenas().getConfigurationSection("Arenas") != null)
			for (String s : Files.getArenas().getConfigurationSection("Arenas").getKeys(false))
			{
				Arena arena = new Arena(StringUtil.getWord(s));
				ArenaManager.loadArena(arena);
				System.out.println("Loaded Arena: " + arena.getName());
			}
		else
			System.out.println("Couldn't Find Any Arenas");
		if (getConfig().getBoolean("MySQL.Enable"))
		{
			System.out.println("Attempting to connect to MySQL");
			MySQL = new MySQL(this, getConfig().getString("MySQL.Host"),
					getConfig().getString("MySQL.Port"),
					getConfig().getString("MySQL.Database"),
					getConfig().getString("MySQL.User"),
					getConfig().getString("MySQL.Pass"));
			c = MySQL.openConnection();
			 try
				{
					 Statement state = c.createStatement();
				
					state.executeUpdate("CREATE TABLE IF NOT EXISTS Cranked (Player CHAR(16), Kills INT(10), Deaths INT(10), Score INT(10));");
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("!Unable to connect to MySQL!");
					getConfig().set("MySql.Enable", false);
					saveConfig();
				}
		}
		System.out.println("Using Players.yml for stats");
		System.out.println("====================");

	}

	public void onDisable() {
		if (!CPlayerManager.getPlayers().isEmpty())
			for (CPlayer cp : CPlayerManager.getPlayers())
			{
				if(cp.getArena() != null){
					cp.getPlayer().sendMessage(Msgs.Error_Plugin_Unload.getString());
					Game.leave(cp);
				}
			}
		if (getConfig().getBoolean("MySQL.Enable"))
		{
			MySQL.closeConnection();
		}
	}

}

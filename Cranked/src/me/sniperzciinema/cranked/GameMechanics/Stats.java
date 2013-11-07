
package me.sniperzciinema.cranked.GameMechanics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.Tools.Files;


public class Stats {

	// Get the kills from the location required
	public static int getKills(String name) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			return Integer.valueOf(getMySQLStats(name, "Kills"));
		else
			return Files.getPlayers().getInt("Players." + name + ".Kills");
	}

	// Set the kills to the location required
	public static void setKills(String name, Integer kills) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			setMySQLStats(name, "Kills", kills);
		else
		{
			Files.getPlayers().set("Players." + name + ".Kills", kills);
			Files.savePlayers();
		}
	}

	// Get the deaths from the location required
	public static int getDeaths(String name) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			return Integer.valueOf(getMySQLStats(name, "Deaths"));
		else
			return Files.getPlayers().getInt("Players." + name + ".Deaths");
	}

	// Set the Deaths to the location required
	public static void setDeaths(String name, Integer deaths) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			setMySQLStats(name, "Deaths", deaths);
		else
		{
			Files.getPlayers().set("Players." + name + ".Deaths", deaths);
			Files.savePlayers();
		}
	}

	// Get the score from the location required
	public static int getScore(String name) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			return Integer.valueOf(getMySQLStats(name, "Score"));
		else
			return Files.getPlayers().getInt("Players." + name + ".Score");
	}

	// Set the Score to the location required
	public static void setScore(String name, Integer score) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			setMySQLStats(name, "Score", score);
		else
		{
			Files.getPlayers().set("Players." + name + ".Score", score);
			Files.savePlayers();
		}
	}

	/**
	 * 
	 * @param name
	 * @return playingTime
	 */
	public static int getPlayingTime(String name) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			return Integer.valueOf(getMySQLStats(name, "Playing Time"));
		else
			return Files.getPlayers().getInt("Players." + name + ".Playing Time");
	}
/**
 * 
 * @param name
 * @param playingTime
 */
	public static void setPlayingTime(String name, Long playingTime) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			setMySQLStats(name, "Playing Time", playingTime.intValue());
		else
		{
			Files.getPlayers().set("Players." + name + ".Playing Time", playingTime.intValue());
			Files.savePlayers();
		}
	}

	// Method to get the MySQL Stats (Untested...)
	private static String getMySQLStats(String name, String stat) {
		String value = "0";

		Statement statement;
		try
		{
			statement = Main.c.createStatement();

			ResultSet res;

			res = statement.executeQuery("SELECT * FROM " + stat + " WHERE PlayerName = '" + name + "';");

			res.next();

			if (res.getString("PlayerName") == null)
			{
				value = "0";
			} else
			{
				value = res.getString("stat");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			value = "0";
		}
		return value;
	}

	// Method to set the MySQL Stats (Untested...)
	private static void setMySQLStats(String name, String stat, int value) {
		Statement statement;
		try
		{
			statement = Main.c.createStatement();

			statement.executeUpdate("INSERT INTO Cranked (`PlayerName`, `" + stat + "`) VALUES ('" + name + "', " + value + ");");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

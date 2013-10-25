
package me.sniperzciinema.cranked.GameMechanics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.Tools.Files;


public class Stats {

	public static int getKills(String name) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			return Integer.valueOf(getMySQLStats(name, "Kills"));
		else
			return Files.getPlayers().getInt("Players." + name + ".Kills");
	}

	public static void setKills(String name, Integer kills) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			setMySQLStats(name, "Kills", kills);
		else
		{
			Files.getPlayers().set("Players." + name + ".Kills", kills);
			Files.savePlayers();
		}
	}

	public static int getDeaths(String name) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			return Integer.valueOf(getMySQLStats(name, "Deaths"));
		else
			return Files.getPlayers().getInt("Players." + name + ".Deaths");
	}

	public static void setDeaths(String name, Integer deaths) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			setMySQLStats(name, "Deaths", deaths);
		else
		{
			Files.getPlayers().set("Players." + name + ".Deaths", deaths);
			Files.savePlayers();
		}
	}

	
	public static int getScore(String name) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			return Integer.valueOf(getMySQLStats(name, "Score"));
		else
			return Files.getPlayers().getInt("Players." + name + ".Score");
	}

	public static void setScore(String name, Integer score) {
		if (Main.me.getConfig().getBoolean("MySQL.Enable"))
			setMySQLStats(name, "Score", score);
		else
		{
			Files.getPlayers().set("Players." + name + ".Score", score);
			Files.savePlayers();
		}
	}

	
	
	
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

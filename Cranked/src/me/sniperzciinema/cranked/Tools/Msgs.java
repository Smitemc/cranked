
package me.sniperzciinema.cranked.Tools;

import org.bukkit.ChatColor;

import me.sniperzciinema.cranked.Main;


public enum Msgs
{
	Format_Line("Format.Line"),
	Format_Header("Format.Header"), 
	Arena_Created("Arena.Created"),
	Arena_Removed("Arena.Removed"), 
	Arena_First_Spawn_Set("Arena.First Spawn Set"), 
	Arena_How_To_Set_More_Spawns("Arena.How To Set More Spawns"),
	Error_No_Permission("Error.No Permission"), 
	Error_Game_Already_Started("Error.Games Already Started"), 
	Error_Not_In_A_Game("Error.Not In A Game"), 
	Error_Already_In_A_Game("Error.Already In Game"), 
	Error_Not_An_Arena("Error.Not An Arena"), 
	Error_Already_An_Arena("Error.Already An Arena"),
	Error_Missing_Spawns("Error.Missing Spawns"), 
	Game_You_Joined_A_Game("Game.You Joined A Game"), 
	Game_They_Joined_A_Game("Game.They Joined A Game"), 
	Game_You_Left_A_Game("Game.You Left A Game"), 
	Game_They_Left_A_Game("Game.They Left A Game"), 
	Game_Not_Enough_Players("Game.Not Enough Players"), 
	Commands_How_To_Join("Commands.How To Join"), 
	Commands_How_To_Create("Commands.How To Create"), 
	Commands_How_To_Remove("Commands.How To Remove"), ;

	private String string;

	private Msgs(String s)
	{
		string = s;
	}

	public String getString() {
		try
		{
			return ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string));
		} catch (NullPointerException npe)
		{
			npe.printStackTrace();
			return Main.cranked +"!! Unable to find message !!";
		}
	}

	public String getString(String replacethis, String withthis) {
		try
		{
			return Main.cranked + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll(replacethis, withthis));
		} catch (NullPointerException npe)
		{
			npe.printStackTrace();
			return Main.cranked +"!! Unable to find message !!";
		}
	}

	public String getString(String replacethis, String withthis, String replacethis2, String withthis2) {
		try
		{
			return Main.cranked + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll(replacethis, withthis).replaceAll(replacethis2, withthis2));
		} catch (NullPointerException npe)
		{
			npe.printStackTrace();
			return Main.cranked +"!! Unable to find message !!";
		}
	}
};

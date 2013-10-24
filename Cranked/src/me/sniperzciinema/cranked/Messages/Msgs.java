
package me.sniperzciinema.cranked.Messages;

import org.bukkit.ChatColor;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.Tools.Files;


public enum Msgs
{
	Format_Line("Format.Line"),
	Format_Header("Format.Header"),//<title>
	Arena_Created("Arena.Created"),//<arena>
	Arena_Removed("Arena.Removed"), //<arena>
	Arena_How_To_Set_More_Spawns("Arena.How To Set More Spawns"),
	Error_No_Permission("Error.No Permission"), 
	Error_Game_Already_Started("Error.Games Already Started"), 
	Error_Not_In_A_Game("Error.Not In A Game"), 
	Error_Already_In_A_Game("Error.Already In Game"), 
	Error_Not_An_Arena("Error.Not An Arena"),//<arena>
	Error_Already_An_Arena("Error.Already An Arena"),
	Error_Missing_Spawns("Error.Missing Spawns"), 
	Error_Plugin_Unload("Error.Plugin Unload"), 
	Game_You_Joined_A_Game("Game.You Joined A Game"), 
	Game_They_Joined_A_Game("Game.They Joined A Game"),//<player> 
	Game_You_Left_A_Game("Game.You Left A Game"), 
	Game_They_Left_A_Game("Game.They Left A Game"), //<player>>
	Game_Not_Enough_Players("Game.Not Enough Players"), 
	Game_Players_Needed("Game.Players Needed"),//<current>> // <needed>
	Game_PreGame_Time_Left("Game.PreGame Time Left"),// <time>
	Game_Game_Time_Left("Game.Game Time Left"), // <time>
	Commands_How_To_Join("Commands.How To Join"), 
	Commands_How_To_Set_Spawn("Commands.How To Set Spawn"), 
	Commands_Spawn_Set("Commands.Spawn Set"), //<spawns>
	Commands_How_To_Create("Commands.How To Create"), 
	Commands_How_To_Remove("Commands.How To Remove"),  
	Commands_List_Arenas("Commands.List Arenas"), ; //<validarenas> // <notvalidarenas>

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
			return Main.cranked +"Unable to find message: "+string;
		}
	}

	public String getString(String replacethis, String withthis) {
		try
		{
			return Main.cranked + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll(replacethis, withthis));
		} catch (NullPointerException npe)
		{
			return Main.cranked +"Unable to find message: "+string;
		}
	}

	public String getString(String replacethis, String withthis, String replacethis2, String withthis2) {
		try
		{
			return Main.cranked + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll(replacethis, withthis).replaceAll(replacethis2, withthis2));
		} catch (NullPointerException npe)
		{
			return Main.cranked +"Unable to find message: "+string;
		}
	}
};

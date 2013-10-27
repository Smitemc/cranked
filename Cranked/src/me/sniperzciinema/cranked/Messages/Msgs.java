
package me.sniperzciinema.cranked.Messages;

import org.bukkit.ChatColor;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.Tools.Files;


public enum Msgs
{
	//List of all Messages, and beside are some variables for them.
	Format_Line("Format.Line"),
	Format_Header("Format.Header"),//<title>
	Arena_StatusUpdate("Game.Status Update"),//<current>> // <needed>
	Arena_Created("Arena.Created"),//<arena>
	Arena_Creator("Arena.Creator"), // <creator>>
	Arena_Removed("Arena.Removed"), //<arena>
	Arena_How_To_Set_More_Spawns("Arena.How To Set More Spawns"),
	Error_No_Permission("Error.No Permission"), 
	Error_Game_Already_Started("Error.Games Already Started"), 
	Error_Not_In_A_Game("Error.Not In A Game"), 
	Error_Already_In_A_Game("Error.Already In A Game"), 
	Error_Not_An_Arena("Error.Not An Arena"),//<arena>
	Error_Already_An_Arena("Error.Already An Arena"),
	Error_Missing_Spawns("Error.Missing Spawns"), 
	Error_Plugin_Unload("Error.Plugin Unload"), 
	Error_Cant_Use_Command("Error.Cant Use Command"),
	Error_Not_Enough_Players("Error.Not Enough Players"),
	Error_Max_Speed("Error.Max Speed"),
	GameOver_Winners("GameOver.Winners"),
	GameOver_Times_Up("GameOver.Times Up"),
	GameOver_Max_Points_Reached("GameOver.Max Points Reached"),
	Game_Start("Game.Start"),
	Game_Starting("Game.Starting"),
	Game_You_Joined_A_Game("Game.You Joined A Game"), 
	Game_They_Joined_A_Game("Game.They Joined A Game"),//<player> 
	Game_You_Left_A_Game("Game.You Left A Game"), 
	Game_They_Left_A_Game("Game.They Left A Game"), //<player>>
	Game_Not_Enough_Players("Game.Not Enough Players"), 
	Game_Players_Needed("Game.Players Needed"),//<current>> // <needed>
	Game_PreGame_Time_Left("Game.PreGame Time Left"),// <time>
	Game_Time_Left("Game.Game Time Left"), // <time>
	Game_Ended("Game.Ended"),
	Commands_How_To_Set_Spawn("Commands.How To Set Spawn"), 
	Commands_How_To_Set_Arena("Commands.How To Set Arena"), 
	Commands_Spawn_Set("Commands.Spawn Set"), //<spawns>
	Commands_How_To_Create("Commands.How To Create"), 
	Commands_How_To_Remove("Commands.How To Remove"), 
	Commands_Arena_Is_Set("Commands.Arena Is Set"),  
	Commands_List_Arenas("Commands.List Arenas") ; //<validarenas> // <notvalidarenas>

	private String string;

	private Msgs(String s)
	{
		string = s;
	}

	//Get the message from the Messages.yml, well replacing and variables given
	public String getString() {
		String prefix = (string.equals("Format.Line") ? "" : Main.cranked);
		try
		{
			return prefix + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string));
		} catch (NullPointerException npe)
		{
			return prefix +"Unable to find message: "+string;
		}
	}

	public String getString(String replacethis, String withthis) {
		String prefix = (string.equals("Format.Line") ? "" : Main.cranked);
		try
		{
			return prefix + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll(replacethis, withthis));
		} catch (NullPointerException npe)
		{
			return prefix +"Unable to find message: "+string;
		}
	}

	public String getString(String replacethis, String withthis, String replacethis2, String withthis2) {
		String prefix = (string.equals("Format.Line") ? "" : Main.cranked);
		try
		{
			return prefix + ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll(replacethis, withthis).replaceAll(replacethis2, withthis2));
		} catch (NullPointerException npe)
		{
			return prefix +"Unable to find message: "+string;
		}
	}
};


package me.sniperzciinema.cranked.Tools;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.Tools.Handlers.ItemHandler;


public class Settings {

	private Arena arena;

	public Settings(Arena arena)
	{
		this.arena = arena;
	}

	// /////////////////////////////////////////////-Integers-//////////////////////////////////////////////////////
	public int getGameTime() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Time.Game"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".Time.Game");
		else
			return Files.getConfig().getInt("Settings.Global.Time.Game");
	}

	public int getPregameTime() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Time.PreGame"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".Time.PreGame");
		else
			return Files.getConfig().getInt("Settings.Global.Time.PreGame");
	}

	public int getWaitingStatusUpdateTime() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Time.Waiting Status Update"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".Time.Waiting Status Update");
		else
			return Files.getConfig().getInt("Settings.Global.Time.Waiting Status Update");
	}

	public int getRequiredPlayers() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Auto Start.Required Players"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".Auto Start.Required Players");
		else
			return Files.getConfig().getInt("Settings.Global.Auto Start.Required Players");
	}

	public int getScorePerKill() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Score.Per Kill"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".Score.Per Kill");
		else
			return Files.getConfig().getInt("Settings.Global.Score.Per Kill");
	}

	public int getPointsToWin() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Points.Max Points"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".Points.Max Points");
		else
			return Files.getConfig().getInt("Settings.Global.Points.MaxPoints");
	}
	public int getMaxPlayers() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Game.Max Players"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".Game.Max Players");
		else
			return Files.getConfig().getInt("Settings.Global.Game.MaxPlayers");
	}

	// ////////////////////////////////////////////////-BOOLEANS-////////////////////////////////////////////////////


	public boolean canDropBlocks() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Misc.Can Drop Blocks"))
			return Files.getArenas().getBoolean("Arenas." + arena.getName() + ".Misc.Can Drop Blocks");
		else
			return Files.getConfig().getBoolean("Settings.Global.Misc.Can Drop Blocks");
	}

	public boolean canLooseHunger() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Misc.Can Loose Hunger"))
			return Files.getArenas().getBoolean("Arenas." + arena.getName() + ".Misc.Can Loose Hunger");
		else
			return Files.getConfig().getBoolean("Settings.Global.Misc.Can Loose Hunger");
	}

	public boolean speedUpOnce() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Speed.Only Add Speed Once"))
			return Files.getArenas().getBoolean("Arenas." + arena.getName() + ".Only Add Speed Once");
		else
			return Files.getConfig().getBoolean("Settings.Global.Speed.Only Add Speed Once");
	}

	// ////////////////////////////////////////////////-FLOATS-///////////////////////////////////////////////////////

	public float getBonusSpeed() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Speed.Bonus Per Kill"))
			return Float.parseFloat(Files.getArenas().getString("Arenas." + arena.getName() + ".Speed.Bonus Per Kill"));
		else
			return Float.parseFloat(Files.getConfig().getString("Settings.Global.Speed.Bonus Per Kill"));
	}
	public float getMaxSpeed() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Speed.Max Speed"))
			return Float.parseFloat(Files.getArenas().getString("Arenas." + arena.getName() + ".Speed.Max Speed"));
		else
			return Float.parseFloat(Files.getConfig().getString("Settings.Global.Speed.Max Speed"));
	}

	public float getWaitingSpeed() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Speed.Bonus Well Waiting"))
			return Float.parseFloat(Files.getArenas().getString("Arenas." + arena.getName() + ".Speed.Bonus Well Waiting"));
		else
			return Float.parseFloat(Files.getConfig().getString("Settings.Global.Speed.Bonus Well Waiting"));
	}

	// ////////////////////////////////////////////-ITEMS-///////////////////////////////////////////////////////////

	public ItemStack getDefaultHead() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Helmet"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Helmet"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Settings.Global.Equipment.Helmet"));
	}

	public ItemStack getDefaultChest() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Chest"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Chest"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Settings.Global.Equipment.Chest"));
	}

	public ItemStack getDefaultLegs() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Legs"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Legs"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Settings.Global.Equipment.Legs"));
	}

	public ItemStack getDefaultFeet() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Feet"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Feet"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Settings.Global.Equipment.Feet"));
	}

	public ItemStack[] getDefaultItems() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Items"))
			return ItemHandler.getItemStackList(Files.getArenas().getStringList("Arenas." + arena.getName() + ".Equipment.Items"));
		else
			return ItemHandler.getItemStackList(Files.getConfig().getStringList("Settings.Global.Equipment.Items"));
	}
	// /////////////////////////////////////////////-LIST-////////////////////////////////////////
	public List<String> getScoreBoardRows() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".ScoreBoard."+arena.getState()))
			return Files.getArenas().getStringList("Arenas." + arena.getName() + ".ScoreBoard."+arena.getState());
		else
			return Files.getConfig().getStringList("Settings.Global.ScoreBoard."+arena.getState());
	}
}

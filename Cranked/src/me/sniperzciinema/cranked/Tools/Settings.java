
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
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".In Game.Time.Game"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".In Game.Time.Game");
		else
			return Files.getConfig().getInt("In Game.Time.Game");
	}

	public int getPregameTime() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".In Game.Time.PreGame"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".In Game.Time.PreGame");
		else
			return Files.getConfig().getInt("In Game.Time.PreGame");
	}

	public int getWaitingStatusUpdateTime() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".In Game.Time.Waiting Status Update"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".In Game.Time.Waiting Status Update");
		else
			return Files.getConfig().getInt("In Game.Time.Waiting Status Update");
	}

	public int getRequiredPlayers() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Auto Start.Required Players"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + "Auto Start.Required Players");
		else
			return Files.getConfig().getInt("Auto Start.Required Players");
	}

	public int getScorePerKill() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".In Game.Score.PerKill"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".In Game.Score.PerKill");
		else
			return Files.getConfig().getInt("In Game.Score.PerKill");
	}

	public int getPointsToWin() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".In Game.Points.MaxPoints"))
			return Files.getArenas().getInt("Arenas." + arena.getName() + ".In Game.Points.MaxPoints");
		else
			return Files.getConfig().getInt("In Game.Points.MaxPoints");
	}

	// ////////////////////////////////////////////////-BOOLEANS-////////////////////////////////////////////////////

	// ////////////////////////////////////////////////-FLOATS-///////////////////////////////////////////////////////

	public float getBonusSpeed() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".In Game.Speed.Bonus Per Kill"))
			return Float.parseFloat(Files.getArenas().getString("Arenas." + arena.getName() + ".In Game.Speed.Bonus Per Kill"));
		else
			return Float.parseFloat(Files.getConfig().getString("In Game.Speed.Bonus Per Kill"));
	}

	public float getWaitingSpeed() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".In Game.Speed.Bonus Well Waiting"))
			return Float.parseFloat(Files.getArenas().getString("Arenas." + arena.getName() + ".In Game.Speed.Bonus Well Waiting"));
		else
			return Float.parseFloat(Files.getConfig().getString("In Game.Speed.Bonus Well Waiting"));
	}

	// ////////////////////////////////////////////-ITEMS-///////////////////////////////////////////////////////////

	public ItemStack getDefaultHead() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Helmet"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Helmet"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Helmet"));
	}

	public ItemStack getDefaultChest() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Chest"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Chest"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Chest"));
	}

	public ItemStack getDefaultLegs() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Legs"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Legs"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Legs"));
	}

	public ItemStack getDefaultFeet() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Feet"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." + arena.getName() + ".Equipment.Feet"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Feet"));
	}

	public ItemStack[] getDefaultItems() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".Equipment.Items"))
			return ItemHandler.getItemStackList(Files.getArenas().getStringList("Arenas." + arena.getName() + ".Equipment.Items"));
		else
			return ItemHandler.getItemStackList(Files.getConfig().getStringList("Equipment.Items"));
	}
	// /////////////////////////////////////////////-LIST-////////////////////////////////////////
	public List<String> getScoreBoardRows() {
		if (Files.getArenas().contains("Arenas." + arena.getName() + ".ScoreBoard."+arena.getState()))
			return Files.getArenas().getStringList("Arenas." + arena.getName() + ".ScoreBoard."+arena.getState());
		else
			return Files.getConfig().getStringList("ScoreBoard."+arena.getState());
	}
}

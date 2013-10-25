package me.sniperzciinema.cranked.Tools;

import org.bukkit.inventory.ItemStack;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.Tools.Handlers.ItemHandler;


public class Settings {
	
	private Arena arena;
	
	public Settings(Arena arena){
		this.arena = arena;
	}
	
	///////////////////////////////////////////////-Integers-//////////////////////////////////////////////////////
	public int getGameTime() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".In Game.Time.Game"))
			return Files.getArenas().getInt("Arenas." +arena.getName()+ ".In Game.Time.Game");
		else
			return Files.getConfig().getInt("In Game.Time.Game");
	}
	
	public int getPregameTime() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".In Game.Time.PreGame"))
			return Files.getArenas().getInt("Arenas." +arena.getName()+ ".In Game.Time.PreGame");
		else
			return Files.getConfig().getInt("In Game.Time.PreGame");
		}	

	public int getRequiredPlayers() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".Auto Start.Required Players"))
			return Files.getArenas().getInt("Arenas." +arena.getName()+ "Auto Start.Required Players");
		else
			return Files.getConfig().getInt("Auto Start.Required Players");
	}
	
	public int getScorePerKill() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".In Game.Time.PreGame"))
			return Files.getArenas().getInt("Arenas." +arena.getName()+ ".In Game.Time.PreGame");
		else
			return Files.getConfig().getInt("In Game.Time.PreGame");
		}	

	//////////////////////////////////////////////////-BOOLEANS-////////////////////////////////////////////////////
	public boolean isRequiredPlayersEnabled() {
		if(Files.getArenas().contains("Arenas." +arena.getName() + ".Auto Start.Enable"))
			return Files.getArenas().getBoolean("Arenas." +arena.getName()+ "Auto Start.Enable");
		else
			return Files.getConfig().getBoolean("Auto Start.Enable");
	}

	
	
	//////////////////////////////////////////////////-FLOATS-///////////////////////////////////////////////////////
	
	public float getBonusSpeed() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".In Game.Settings.Bonus Speed Per Kill"))
			return Float.parseFloat(Files.getArenas().getString("Arenas." +arena.getName()+ ".In Game.Settings.Bonus Speed Per Kill"));
		else
			return Float.parseFloat(Files.getConfig().getString("In Game.Settings.Bonus Speed Per Kill"));
	}

	public float getPreGameSpeed() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".In Game.Settings.Speed PreGame"))
			return Float.parseFloat(Files.getArenas().getString("Arenas." +arena.getName()+ ".In Game.Settings.Speed PreGame"));
		else
			return Float.parseFloat(Files.getConfig().getString("In Game.Settings.Speed PreGame"));
	}
	
	
	
	//////////////////////////////////////////////-ITEMS-///////////////////////////////////////////////////////////
	public ItemStack getDefaultHead() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".Equipment.Helmet"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." +arena.getName()+ ".Equipment.Helmet"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Helmet"));
	}

	public ItemStack getDefaultChest() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".Equipment.Chest"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." +arena.getName()+ ".Equipment.Chest"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Chest"));
	}

	public ItemStack getDefaultLegs() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".Equipment.Legs"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." +arena.getName()+ ".Equipment.Legs"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Legs"));
	}
	
	public ItemStack getDefaultFeet() {
		if(Files.getArenas().contains("Arenas." +arena.getName()+ ".Equipment.Feet"))
			return ItemHandler.getItemStack(Files.getArenas().getString("Arenas." +arena.getName()+ ".Equipment.Feet"));
		else
			return ItemHandler.getItemStack(Files.getConfig().getString("Equipment.Feet"));
	}


	public ItemStack[] getDefaultItems() {
		if(!Files.getArenas().contains("Arenas." +arena.getName()+ ".Equipment.Items"))
			return ItemHandler.getItemStackList(Files.getArenas().getStringList("Arenas." +arena.getName()+ ".Equipment.Items"));
		else
			return ItemHandler.getItemStackList(Files.getConfig().getStringList("Equipment.Items"));
	}
	
	
}

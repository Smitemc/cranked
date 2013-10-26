
package me.sniperzciinema.cranked.ArenaHandlers;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.sniperzciinema.cranked.Tools.Files;
import me.sniperzciinema.cranked.Tools.Settings;


public class Arena {

	private String name;
	private States state = States.Waiting;
	private HashMap<Location, Inventory> chests = new HashMap<Location, Inventory>();
	private HashMap<Location, Material> blocks = new HashMap<Location, Material>();

	private Settings Settings = new Settings(this);
	private ArenaTimers Timer = new ArenaTimers(this);

	public Arena(String name)
	{
		this.name = name;
	}

	// Get the arenas settings
	public Settings getSettings() {
		return Settings;
	}

	// Get the arenas name
	public String getName() {
		return name;
	}

	// Get the arenas creator
	public String getCreator() {
		return Files.getArenas().getString("Arenas." + name + ".Creator");
	}

	// Set the arenas creator
	public void setCreator(String maker) {
		Files.getArenas().set("Arenas." + name + ".Creator", maker);
		Files.saveArenas();
	}

	// Get the arenas state
	public States getState() {
		return state;
	}

	// Set the arenas state
	public void setState(States state) {
		this.state = state;
	}

	// Get the arenas spawns
	public List<String> getSpawns() {
		List<String> spawns = Files.getArenas().getStringList("Arenas." + name + ".Spawns");
		return spawns;
	}

	// Get the players in this arena
	public List<Player> getPlayers() {
		return ArenaManager.getPlayers(this);
	}

	// Get the saved chests
	public HashMap<Location, Inventory> getChests() {
		return chests;
	}

	// Get the chest at Loc
	public Inventory getChest(Location loc) {
		return chests.get(loc);
	}

	// Set a chest at Loc
	public void setChest(Location loc, Inventory inv) {
		chests.put(loc, inv);
	}

	// Get the saved Blocks
	public Material getBlock(Location loc) {
		return blocks.get(loc);
	}

	// Get the block at Loc
	public HashMap<Location, Material> getBlocks() {
		return blocks;
	}

	// Set the block at Loc
	public void setBlock(Location loc, Material mat) {
		blocks.put(loc, mat);
	}

	// Get the arenas timers
	public ArenaTimers getTimer() {
		return Timer;
	}

}

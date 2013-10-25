
package me.sniperzciinema.cranked.ArenaClasses;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.sniperzciinema.cranked.Tools.Files;


public class Arena {

	private Timer Timer = new Timer(this);
	private String name;
	private State state = State.Waiting;
	private HashMap<Location, Inventory> chests = new HashMap<Location, Inventory>();
	private HashMap<Location, Material> blocks = new HashMap<Location, Material>();

	public Arena(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCreator() {
		return Files.getArenas().getString("Arenas." + name + ".Creator");
	}

	public void setCreator(String maker) {
		Files.getArenas().set("Arenas." + name + ".Creator", maker);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<String> getSpawns() {
		List<String> spawns = Files.getArenas().getStringList("Arenas." + name + ".Spawns");
		return spawns;
	}

	public List<Player> getPlayers() {
		return ArenaManager.getPlayers(this);
	}

	public HashMap<Location, Inventory> getChests() {
		return chests;
	}

	public Inventory getChest(Location loc) {
		return chests.get(loc);
	}

	public void setChest(Location loc, Inventory inv) {
		chests.put(loc, inv);
	}

	public Material getBlock(Location loc) {
		return blocks.get(loc);
	}

	public HashMap<Location, Material> getBlocks() {
		return blocks;
	}

	public void setBlock(Location loc, Material mat) {
		blocks.put(loc, mat);
	}

	public Timer getTimer() {
		return Timer;
	}

}

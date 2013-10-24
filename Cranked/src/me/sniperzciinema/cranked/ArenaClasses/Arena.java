
package me.sniperzciinema.cranked.ArenaClasses;

import java.util.List;

import org.bukkit.entity.Player;

import me.sniperzciinema.cranked.Tools.Files;


public class Arena {

	private String name;
	private State state = State.Waiting;
	// private HashMap<Location, Inventory> chests = new HashMap<Location,
	// Inventory>();
	// private HashMap<Location, Material> blocks = new HashMap<Location,
	// Material>();

	
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
	public List<Player> getPlayers(){
		return ArenaManager.getPlayers(this);
	}

}

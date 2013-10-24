package me.sniperzciinema.cranked.Tools.Handlers;
 
import java.util.List;

import me.sniperzciinema.cranked.Tools.Files;

import org.bukkit.entity.Player;
 
public class Arena {

	  private String name;
	  private State state = State.Waiting;
	//private HashMap<Location, Inventory> chests = new HashMap<Location, Inventory>();
	//private HashMap<Location, Material> blocks = new HashMap<Location, Material>();
 
	private PlayerManager playerManager; // This is to manage all of the players
 
	public Arena(String name) {
		this.name = name;
 
		// Now get a new PlayerManager
		playerManager = new PlayerManager(this);
	}

	public String getName() {
		return name;
	}
	public String getCreator() {
		return Files.getArenas().getString("Arenas."+ name +".Creator");
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public List<Player> getPlayers() {
		return playerManager.getPlayers();
	}
 	public List<String> getSpawns() {
 		List<String> spawns = Files.getArenas().getStringList("Arenas."+ name +".Spawns");
		return spawns;
	}
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
}
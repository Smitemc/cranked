package me.sniperzciinema.cranked.PlayerClasses;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import me.sniperzciinema.cranked.Tools.Handlers.LocationHandler;

import org.bukkit.entity.Player;
 
public class CrackedPlayerManager {
 
  // An list to hold all of the arenas
	private static List<CrackedPlayer> players = new ArrayList<CrackedPlayer>();
 
	public static List<CrackedPlayer> getPlayers(){
		return players;
	}

	// Method to register an arena if not registered, just adds it to the list
	public static CrackedPlayer getCrackedPlayer(Player player) {
		for(CrackedPlayer p : players){
			if(p.name.equalsIgnoreCase(player.getName()))
				return p;
		}
		return null;
	}
	public static void loadCrackedPlayer(CrackedPlayer cp) {
		if (!isCrackedPlayer(cp)) {
			players.add(cp);
		}
	}
	
	public static void createCrackedPlayer(Player player) {
		CrackedPlayer cp = new CrackedPlayer(player);
		players.add(cp);
	}
	
 	public static void deleteCrackedPlayer(CrackedPlayer cp) {
		players.remove(cp);
	}
 
	public static boolean isCrackedPlayer(CrackedPlayer cp) {
		return players.contains(cp);
	}
	public static void setInfo(CrackedPlayer cp){
		cp.setInfo();
	}
	public static void reset(CrackedPlayer cp){
		cp.reset();
	}
	
	public static void respawn(Player p){
		p.setHealth(20.0);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		Random r = new Random();
		int i = r.nextInt(getCrackedPlayer(p).getArena().getSpawns().size());
		String loc = getCrackedPlayer(p).getArena().getSpawns().get(i);
		
		p.teleport(LocationHandler.getPlayerLocation(loc));
	}
	public static void respawn(CrackedPlayer cp){
		Player p = cp.getPlayer();
		p.setHealth(20.0);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		Random r = new Random();
		int i = r.nextInt(cp.getArena().getSpawns().size());
		String loc = cp.getArena().getSpawns().get(i);
		
		p.teleport(LocationHandler.getPlayerLocation(loc));
	}
	
	public static boolean arenaRegistered(Player player) {
		return (getCrackedPlayer(player) != null);
	}
}

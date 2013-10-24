package me.sniperzciinema.cranked.PlayerClasses;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import me.sniperzciinema.cranked.Tools.Handlers.LocationHandler;

import org.bukkit.entity.Player;
 
public class CrankedPlayerManager {
 
  // An list to hold all of the arenas
	private static List<CrankedPlayer> players = new ArrayList<CrankedPlayer>();
 
	public static List<CrankedPlayer> getPlayers(){
		return players;
	}

	// Method to register an arena if not registered, just adds it to the list
	public static CrankedPlayer getCrackedPlayer(Player player) {
		for(CrankedPlayer p : players){
			if(p.name.equalsIgnoreCase(player.getName()))
				return p;
		}
		return null;
	}
	public static void loadCrackedPlayer(CrankedPlayer cp) {
		if (!isCrackedPlayer(cp)) {
			players.add(cp);
		}
	}
	
	public static void createCrackedPlayer(Player player) {
		CrankedPlayer cp = new CrankedPlayer(player);
		players.add(cp);
	}
	
 	public static void deleteCrackedPlayer(CrankedPlayer cp) {
		players.remove(cp);
	}
 
	public static boolean isCrackedPlayer(CrankedPlayer cp) {
		return players.contains(cp);
	}
	public static void setInfo(CrankedPlayer cp){
		cp.setInfo();
	}
	public static void reset(CrankedPlayer cp){
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
	public static void respawn(CrankedPlayer cp){
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

package me.sniperzciinema.cranked.PlayerHandlers;
 
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
 
public class CPlayerManager {
 
  // An list to hold all of the arenas
	private static List<CPlayer> players = new ArrayList<CPlayer>();
 
	public static List<CPlayer> getPlayers(){
		return players;
	}

	// Method to register an arena if not registered, just adds it to the list
	public static CPlayer getCrackedPlayer(Player player) {
		for(CPlayer p : players){
			if(p.name.equalsIgnoreCase(player.getName()))
				return p;
		}
		return null;
	}
	public static void loadCrackedPlayer(CPlayer cp) {
		if (!isCrackedPlayer(cp)) {
			players.add(cp);
		}
	}
	
	public static void createCrackedPlayer(Player player) {
		CPlayer cp = new CPlayer(player);
		players.add(cp);
	}
	
 	public static void deleteCrackedPlayer(CPlayer cp) {
		players.remove(cp);
	}

	public static boolean isCrackedPlayer(CPlayer cp) {
		return players.contains(cp);
	}
	public static boolean isCrackedPlayer(Player p) {
		return players.contains(getCrackedPlayer(p));
	}
	public static void setInfo(CPlayer cp){
		cp.setInfo();
	}
	public static void reset(CPlayer cp){
		cp.reset();
	}
	
	
	public static boolean arenaRegistered(Player player) {
		return (getCrackedPlayer(player) != null);
	}
}


package me.sniperzciinema.cranked.PlayerHandlers;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;


public class CPlayerManager {

	// An list to hold all of the arenas
	private static List<CPlayer> players = new ArrayList<CPlayer>();

	public static List<CPlayer> getPlayers() {
		return players;
	}

	// Method to register an arena if not registered, just adds it to the list
	public static CPlayer getCrackedPlayer(Player player) {
		for (CPlayer p : players)
		{
			if (p.name.equalsIgnoreCase(player.getName()))
				return p;
		}
		return null;
	}

	// Add the cracked player to the players list
	public static void loadCrackedPlayer(CPlayer cp) {
		if (!isCrackedPlayer(cp.getPlayer()))
		{
			players.add(cp);
		}
	}

	// Create a CPlayer for a player
	public static void createCrackedPlayer(Player player) {
		CPlayer cp = new CPlayer(player);
		players.add(cp);
	}

	// Delete the cracked player
	public static void deleteCrackedPlayer(CPlayer cp) {
		players.remove(cp);
	}

	// Check if the player is a cracked player
	public static boolean isCrackedPlayer(Player p) {
		return players.contains(getCrackedPlayer(p));
	}

	// Set the CPlayer's info
	public static void setInfo(CPlayer cp) {
		cp.setInfo();
	}

	// Reset the CPlayer
	public static void reset(CPlayer cp) {
		cp.reset();
	}

	// Check if a player is in an arena
	public static boolean isInArena(Player player) {
		return (getCrackedPlayer(player).getArena() != null);
	}
}

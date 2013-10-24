package me.sniperzciinema.cranked.ArenaClasses;
 
import java.util.ArrayList;
import java.util.List;

import me.sniperzciinema.cranked.Messages.StringUtil;
import me.sniperzciinema.cranked.PlayerClasses.CrackedPlayer;
import me.sniperzciinema.cranked.PlayerClasses.CrackedPlayerManager;
import me.sniperzciinema.cranked.Tools.Files;
import me.sniperzciinema.cranked.Tools.Handlers.LocationHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;
 
public class ArenaManager {
 
  // An list to hold all of the arenas
	private static List<Arena> arenas = new ArrayList<Arena>();
 
	public static List<Arena> getArenas(){
		return arenas;
	}

	public static String getPossibleArenas(){

		ArrayList<String> possible = new ArrayList<String>();
		for (Arena arena : arenas)
		{
			if(ArenaManager.arenaRegistered(arena)){
				if(isArenaValid(arena.getName()))
					possible.add(arena.getName());
			}
		}

		StringBuilder possibleString = new StringBuilder();
		for (String s : possible)
		{
			possibleString.append(s);
			if (possible.size() > 1)
				possibleString.append(", ");
		}
		return possible.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}

	public static String getNotPossibleArenas(){

		ArrayList<String> notpossible = new ArrayList<String>();
		for (Arena arena : arenas)
		{
			if(ArenaManager.arenaRegistered(arena)){
				if(!isArenaValid(arena.getName()))
					notpossible.add(arena.getName());
			}
		}

		StringBuilder notpossibleString = new StringBuilder();
		for (String s : notpossible)
		{
			notpossibleString.append(s);
			if (notpossible.size() > 1)
				notpossibleString.append(", ");
		}
		return notpossible.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}

	// Method to register an arena if not registered, just adds it to the list
	public static void loadArena(Arena arena) {
		if (!arenaRegistered(arena)) {
			arenas.add(arena);
		}
	}
	
	public static void createArena(String name) {
		name = StringUtil.getWord(name);
		loadArena(new Arena(name));
	}
 
	/*
	 * Removing a registered arena, a case of this would be a command to delete
	 * an arena
	 */
	public static void unloadArena(Arena arena) {
		if (arenaRegistered(arena)) {
			arenas.remove(arena);
		}
	}
	public static void removeArena(String name) {
		name = StringUtil.getWord(name);
		Files.arenas.set("Arenas."+ name , null);
		Files.saveArenas();
		unloadArena(getArena(name));
	}
 
	// Method to check if the arena has already been registered
	public static boolean arenaRegistered(Arena arena) {
		return arenas.contains(arena);
	}
 
	/*
	 * Method to check if an arena by a name has been registered. Example case
	 * would be a creation command saying that you can't have two arenas by the
	 * same name registered
	 */
	public static boolean arenaRegistered(String name) {
		name = StringUtil.getWord(name);
		return (getArena(name) != null);
	}
 
	public static boolean isArenaValid(String name){
		name = StringUtil.getWord(name);
		return !Files.getArenas().getStringList("Arenas."+ name +".Spawns").isEmpty();
	}
	/*
	 * Gets the arena by it's name. If you don't want it to return null, check
	 * if it exists first
	 */
	public static Arena getArena(String name) {
		name = StringUtil.getWord(name);
		for (Arena arena : arenas) {
			if (arena.getName().equals(name)) {
				return arena;
			}
		}
		return null;
	}
	public static Arena getArena(CrackedPlayer cp){
		return cp.getArena();
	}
	
	public static void setSpawn(String arena, Location loc){
		arena = StringUtil.getWord(arena);
		List<String> spawns = Files.getArenas().getStringList("Arenas."+arena+".Spawns");
		spawns.add(LocationHandler.getLocationToString(loc));
		Files.getArenas().set("Arenas."+arena+".Spawns", spawns);
		Files.saveArenas();
	}

	public static String getArenaCreator(Arena arena){
		if(arena.getCreator() != null)
			return arena.getCreator();
		else 
			return "Unkown";
	}
	public static List<Player> getPlayers(Arena arena){
		List<Player> players = new ArrayList<Player>();
		for(CrackedPlayer cp : CrackedPlayerManager.getPlayers()){
			if(cp.getArena() == arena)
				players.add(cp.getPlayer());
		}
		return players;
	}


	
}

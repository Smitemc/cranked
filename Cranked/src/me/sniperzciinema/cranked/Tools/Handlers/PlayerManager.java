package me.sniperzciinema.cranked.Tools.Handlers;
 
import java.util.ArrayList;
import java.util.List;
 
import me.sniperzciinema.cranked.Tools.Msgs;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


//TODO: Make PlayerManager not dependant on Arena(Make PlayerManager it's own thing)


public class PlayerManager {
 
  private Arena arena; 
	private List<PlayerData> players = new ArrayList<PlayerData>();
 
	public PlayerManager(Arena arena) {
		this.arena = arena;
	}
 
	public void addPlayer(Player p) {

		p.sendMessage(Msgs.Game_You_Joined_A_Game.getString());
		for(Player ppl : arena.getPlayers()){
			ppl.sendMessage(Msgs.Game_They_Joined_A_Game.getString("<player>", p.getName()));
		}
		
		players.add(new PlayerData(p));
		
		p.setFallDistance(0);
		LocationHandler.respawn(p, arena.getName());
	}
 
	public void removePlayer(Player p) {
		PlayerData pd = null;
		for (PlayerData data : players) {
			if (data.name.equalsIgnoreCase(p.getName())) {
				pd = data;
				data.reset();
				break;
			}
		}
		players.remove(pd);
		p.sendMessage(Msgs.Game_You_Left_A_Game.getString());
		for(Player ppl : arena.getPlayers()){
			ppl.sendMessage(Msgs.Game_They_Left_A_Game.getString("<player>", p.getName()));
		}
	}
 
	public boolean playerPlaying(Player p) {
		for (PlayerData data : players) {
			if (data.name.equalsIgnoreCase(p.getName())) {
				return true;
			}
		}
		return false;
	}
	public PlayerData getPlayerData(Player p){
		for (PlayerData data : players) {
			if (data.name.equalsIgnoreCase(p.getName())) {
				return data;
			}
		}
		return null;
	}
	public List<Player> getPlayers(){
		List<Player> player = new ArrayList<Player>();
		
		for (PlayerData data : players) {
			player.add(Bukkit.getPlayer(data.name));
			
		}
		return player;
	}
 
	private class PlayerData {
		//private long timeJoined;
		private GameMode gm;
		private int lvl;
		private float exp;
		private double hp;
		private int food;
		private ItemStack[] armor;
		private ItemStack[] inventory;
		private Location loc;
		String name;
 
		public PlayerData(Player p) {
			loc = p.getLocation();
			name = p.getName();
			gm = p.getGameMode();
			lvl = p.getLevel();
			exp = p.getExp();
			hp = p.getHealth();
			food = p.getFoodLevel();
			inventory = p.getInventory().getContents();
			armor = p.getInventory().getArmorContents();
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			
		}
 
		@SuppressWarnings("deprecation")
		public void reset() {
			Player p = Bukkit.getPlayerExact(name);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setGameMode(gm);
			p.setLevel(lvl);
			p.setExp(exp);
			p.setHealth(hp);
			p.setFoodLevel(food);
			p.getInventory().setContents(inventory);
			p.getInventory().setArmorContents(armor);
			p.updateInventory();
			p.setFallDistance(0);
			p.teleport(loc);
		}
	}
}
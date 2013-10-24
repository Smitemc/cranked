
package me.sniperzciinema.cranked.PlayerClasses;

import me.sniperzciinema.cranked.ArenaClasses.Arena;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CrackedPlayer {
	private int killstreak = 0;
	private long timeJoined;
	private GameMode gamemode;
	private int level;
	private float exp;
	private double health;
	private int food;
	private ItemStack[] armor;
	private ItemStack[] inventory;
	private Location location;
	private Arena arena;
	private String creating;
	private Player player;
	String name;

	public CrackedPlayer(Player p) {
		location = p.getLocation();
		name = p.getName();
		gamemode = p.getGameMode();
		level = p.getLevel();
		exp = p.getExp();
		health = p.getHealth();
		food = p.getFoodLevel();
		inventory = p.getInventory().getContents();
		armor = p.getInventory().getArmorContents();
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		player = p;
	}
	public void setInfo(){
		location = player.getLocation();
		name = player.getName();
		gamemode = player.getGameMode();
		level = player.getLevel();
		exp = player.getExp();
		health = player.getHealth();
		food = player.getFoodLevel();
		inventory = player.getInventory().getContents();
		armor = player.getInventory().getArmorContents();
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
	}

	@SuppressWarnings("deprecation")
	public void reset() {
		Player p = Bukkit.getPlayerExact(name);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setGameMode(gamemode);
		p.setLevel(level);
		p.setExp(exp);
		p.setHealth(health);
		p.setFoodLevel(food);
		p.getInventory().setContents(inventory);
		p.getInventory().setArmorContents(armor);
		p.updateInventory();
		p.setFallDistance(0);
		p.teleport(location);
		location = null;
		gamemode = null;
		level = 0;
		exp = 0;
		health = 20;
		food = 20;
		inventory = null;
		armor = null;
		arena = null;
	}
	
	public int getKillstreak() {
		return killstreak;
	}
	
	public void setKillstreak(int killstreak) {
		this.killstreak = killstreak;
	}
	
	public long getTimeJoined() {
		return timeJoined;
	}
	
	public void setTimeJoined(long timeJoined) {
		this.timeJoined = timeJoined;
	}
	
	public GameMode getGamemode() {
		return gamemode;
	}
	
	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public float getExp() {
		return exp;
	}
	
	public void setExp(float exp) {
		this.exp = exp;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public int getFood() {
		return food;
	}
	
	public void setFood(int food) {
		this.food = food;
	}
	
	public ItemStack[] getArmor() {
		return armor;
	}
	
	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}
	
	public ItemStack[] getInventory() {
		return inventory;
	}
	
	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public void setArena(Arena arena) {
		this.arena = arena;
	}
	
	public String getCreating() {
		return creating;
	}
	
	public void setCreating(String creating) {
		this.creating = creating;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}

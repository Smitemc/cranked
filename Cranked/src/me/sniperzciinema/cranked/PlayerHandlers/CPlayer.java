
package me.sniperzciinema.cranked.PlayerHandlers;

import java.util.Random;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.GameMechanics.Agility;
import me.sniperzciinema.cranked.GameMechanics.Equip;
import me.sniperzciinema.cranked.GameMechanics.Stats;
import me.sniperzciinema.cranked.Tools.Handlers.LocationHandler;
import me.sniperzciinema.cranked.Extras.ScoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;


public class CPlayer {
	private Player player;
	String name;
	private int points = 0;
	private int killstreak = 0;
	private CPlayerTimers PlayerTimer = new CPlayerTimers(this);
	private ScoreBoard ScoreBoard = new ScoreBoard(player);
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
	private Player lastDamager;

	public CPlayer(Player p) {
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
		
		player.setGameMode(GameMode.ADVENTURE);
		player.setLevel(0);
		player.setExp(0.0F);
		player.setHealth(20);
		player.setFoodLevel(20);
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
		p.setWalkSpeed(0.2F);
		for (PotionEffect effect : player.getActivePotionEffects())
	        player.removePotionEffect(effect.getType());
		points = 0;
		getTimer().stopTimer();
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
	public void setLastDamager(Player p){
		lastDamager = p;
	}
	public Player getLastDamager(){
		return lastDamager;
	}

	public void respawn(){
		Player p = getPlayer();
		p.setHealth(20.0);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		p.setExp(1.0F);
		Random r = new Random();
		int i = r.nextInt(getArena().getSpawns().size());
		String loc = getArena().getSpawns().get(i);
		p.teleport(LocationHandler.getPlayerLocation(loc));
		Equip.equipPlayer(p);
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
	
	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	public int getScore(){
		return Stats.getScore(getName());
	}
	public int getKills(){
		return Stats.getKills(getName());
	}
	public int getDeaths(){
		return Stats.getDeaths(getName());
	}
	
	public void updateSpeed(){
		Agility.speedUp(getPlayer(), true);
	}
	public void resetSpeed(){
		Agility.resetSpeed(getPlayer());
	}
	public void updateStats(int kills, int deaths, int score){
		if(kills != 0)
			Stats.setKills(getName(), getKills() + kills);
		if(deaths != 0)
			Stats.setDeaths(getName(), getDeaths() + deaths);
		if(score != 0)
			Stats.setScore(getName(), getScore() + score);
	}
	public ScoreBoard getScoreBoard(){
		return ScoreBoard;
	}
	public CPlayerTimers getTimer(){
		return PlayerTimer;
	}

}


package me.sniperzciinema.cranked.ArenaClasses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.sniperzciinema.cranked.Game;
import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.Messages.Time;
import me.sniperzciinema.cranked.Tools.Files;


public class Timer {

	private Arena arena;
	private int timeLeft;
	private int pregame;
	private int game;

	public Timer(Arena arena)
	{
		this.arena = arena;
	}

	public Arena getArena() {
		return arena;
	}

	public int getTimePreGame() {
		return 10;
	}

	public int getGameTime() {
		return Files.getArenas().getInt("Game.Time.Game");
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public void stopPreGameTimer(){
		Bukkit.getScheduler().cancelTask(pregame);
	}
	public void stopGameTimer(){
		Bukkit.getScheduler().cancelTask(game);
	}
	
	public void startPreGameTimer(){
		timeLeft = getTimePreGame();
		for(Player player : arena.getPlayers()){
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,  Integer.MAX_VALUE, 10), true);
		}
		pregame = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable() {
			@Override
			public void run() {
				if(timeLeft != -1) {
					timeLeft -= 1;
					for(Player player : arena.getPlayers()){
						player.sendMessage(Msgs.Game_PreGame_Time_Left.getString("<time>", Time.getTime((long)timeLeft)));
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,  Integer.MAX_VALUE, timeLeft), true);
					}
				}
				//GAME STARTS
				else if(timeLeft == -1) {
						startGameTimer();
					}
				}
		}, 0L, 20L);
	}
	public void startGameTimer(){
		timeLeft = getGameTime();
		game = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable() {
			@Override
			public void run() {
				if(timeLeft != -1) {
					timeLeft -= 1;
					
				}
				//GAME STARTS
				else if(timeLeft == -1) {
						Game.end(arena);
					}
				}
		}, 0L, 20L);
	}
}

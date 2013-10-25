
package me.sniperzciinema.cranked.PlayerClasses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.sniperzciinema.cranked.Main;


public class PlayerTimer {

	private CrankedPlayer cp;
	private int timeSinceLastKill = 0;
	private int timer;
	
	public PlayerTimer(CrankedPlayer cp)
	{
		this.cp = cp;
	}

	public CrankedPlayer getCrankedPlayer() {
		return cp;
	}

	public int getTimeSinceLastKill(){
		return timeSinceLastKill;
	}
	public void stopTimer(){
		Bukkit.getScheduler().cancelTask(timer);
	}
	public void reset(){
		timeSinceLastKill = 0;
	}
	public void startTimer(){

		final Player player = getCrankedPlayer().getPlayer();
		player.setExp(0.99F);
		timer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable() {
			@Override
			public void run() {
				if(timeSinceLastKill != 30) {
					timeSinceLastKill += 1;
					
					if(player.getExp() != 0.0F)
						player.setExp(player.getExp() - 0.0333333333333333333333333333333333333333F);
				}
				//GAME STARTS
				else if(timeSinceLastKill == 30) {
						//TODO: Kill Player
					timeSinceLastKill = 0;
					player.setExp(0.99F);
					}
				}
		}, 0L, 20L);
	}
}

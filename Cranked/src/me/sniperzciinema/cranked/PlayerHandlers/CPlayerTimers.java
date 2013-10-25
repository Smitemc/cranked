
package me.sniperzciinema.cranked.PlayerHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.sniperzciinema.cranked.Main;


public class CPlayerTimers {

	private CPlayer cp;
	private int timeSinceLastKill = 0;
	private int timer;
	
	public CPlayerTimers(CPlayer cp)
	{
		this.cp = cp;
	}

	public CPlayer getCrankedPlayer() {
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

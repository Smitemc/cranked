
package me.sniperzciinema.cranked.PlayerHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.GameMechanics.DeathTypes;
import me.sniperzciinema.cranked.GameMechanics.Deaths;


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
	public void restartTimer(){
		stopTimer();
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.me, new Runnable() {
				@Override
				public void run() {
					
					startTimer();
				}
		 },1L);
	}
	public void startTimer(){
		timeSinceLastKill = 0;
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
					Deaths.playerDies(null, player, DeathTypes.OutOfTime);
					player.getWorld().createExplosion(player.getLocation(), -1);
					}
				}
		}, 0L, 20L);
	}
}

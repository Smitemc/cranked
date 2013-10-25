
package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.Messages.DeathMessages;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import org.bukkit.entity.Player;


public class Deaths {

	public static void playerDies(Player killer, Player killed, DeathTypes death) {
		if (killer != null)
		{
			CPlayer cKiller = CPlayerManager.getCrackedPlayer(killer);

			cKiller.getTimer().restartTimer();
			cKiller.updateStats(1, 0, cKiller.getScore() + cKiller.getArena().getSettings().getScorePerKill());
			cKiller.setPoints(cKiller.getPoints() + (cKiller.getKillstreak() > 0 ? 1 : 0));
			cKiller.setKillstreak(cKiller.getKillstreak() + 1);
			cKiller.updateSpeed();	
		}
		
		CPlayer cKilled = CPlayerManager.getCrackedPlayer(killed);
		cKilled.setKillstreak(0);
		cKilled.updateSpeed();
		cKilled.getTimer().restartTimer();
		cKilled.updateStats(0, 1, 0);
		cKilled.respawn();
		
		
		for(Player p : cKilled.getArena().getPlayers())
			p.sendMessage(DeathMessages.getDeathMessage(death, killer.getName(), killed.getName()));
	}

}

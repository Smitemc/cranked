
package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;

import org.bukkit.entity.Player;


public class Deaths {

	public static void playerDies(Player killer, Player killed) {
		CPlayer cKiller = CPlayerManager.getCrackedPlayer(killer);
		CPlayer cKilled = CPlayerManager.getCrackedPlayer(killed);

		cKiller.setKillstreak(cKiller.getKillstreak() + 1);
		cKiller.updateSpeed();
		cKiller.getTimer().stopTimer();
		cKiller.getTimer().startTimer();
		cKiller.updateStats(1 , 0);
		cKiller.setPoints(cKiller.getPoints());

		cKilled.setKillstreak(0);
		cKilled.updateSpeed();
		cKilled.getTimer().stopTimer();
		cKilled.getTimer().startTimer();
		cKilled.updateStats(0 , 1);
	}


}


package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.Messages.DeathMessages;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import org.bukkit.entity.Player;


public class Deaths {

	// Method that handles all deaths in the arenas
	public static void playerDies(Player killer, Player killed, DeathTypes death) {

		// Only do the killers parts if the killer isn't here, this way we can
		// still make them suicide safetly
		if (killer != null)
		{
			CPlayer cKiller = CPlayerManager.getCrackedPlayer(killer);
			// Restart their last kill timer
			cKiller.getTimer().restartTimer();
			// Update their stats
			cKiller.updateStats(1, 0, cKiller.getScore() + cKiller.getArena().getSettings().getScorePerKill());
			// Set the players Points accordingly
			cKiller.setPoints(cKiller.getPoints() + (cKiller.getKillstreak() > 0 ? 1 : 0));
			// Set the players killstreak to add one
			cKiller.setKillstreak(cKiller.getKillstreak() + 1);
			// Update their speed depending on their new killStreak total
			cKiller.updateSpeed();
		}

		CPlayer cKilled = CPlayerManager.getCrackedPlayer(killed);
		cKilled.setKillstreak(0);
		cKilled.updateSpeed();
		cKilled.getTimer().stopTimer();
		cKilled.updateStats(0, 1, 0);
		// Respawn the player
		cKilled.respawn();

		// Tell players that <killer> killed <killed>
		for (Player p : cKilled.getArena().getPlayers())
			p.sendMessage(DeathMessages.getDeathMessage(killer, killed, death));
	}

}


package me.sniperzciinema.cranked.GameMechanics;

import me.sniperzciinema.cranked.Game;
import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.Messages.DeathMessages;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import org.bukkit.entity.Player;


public class Deaths {

	// Method that handles all deaths in the arenas
	public static void playerDies(Player killer, Player killed, DeathTypes death) {
		
		CPlayer cKiller = null;
		CPlayer cKilled = null;
		Arena arena = null;
		// Only do the killers parts if the killer isn't here, this way we can
		// still make them suicide safetly
		if (killer != null)
		{
			cKiller = CPlayerManager.getCrankedPlayer(killer);
			arena = cKiller.getArena();
			// Restart their last kill timer
			cKiller.getTimer().restartTimer();
			// Update their stats
			cKiller.updateStats(1, 0, cKiller.getScore() + cKiller.getArena().getSettings().getScorePerKill());
			cKiller.setKills(cKiller.getKills() + 1);
			// Set the players Points accordingly
			cKiller.setPoints(cKiller.getPoints() + 1 +(cKiller.getKillstreak() > 0 ? 1 : 0));
			// Set the players killstreak to add one
			cKiller.setKillstreak(cKiller.getKillstreak() + 1);
			// Update their speed depending on their new killStreak total
			cKiller.updateSpeed();
			cKiller.getScoreBoard().showStats();
		}
		if (killed != null)
		{
			if (death == DeathTypes.OutOfTime)
			killed.getWorld().createExplosion(killed.getLocation(), -1);

			cKilled = CPlayerManager.getCrankedPlayer(killed);
			arena = cKilled.getArena();
			cKilled.setKillstreak(0);
			cKilled.updateSpeed();
			cKilled.getTimer().stopTimer();
			cKilled.updateStats(0, 1, 0);
			cKilled.setKills(cKilled.getDeaths() + 1);
			// Respawn the player
			cKilled.respawn();
			cKilled.getScoreBoard().showStats();
		}

		// Tell players that <killer> killed <killed>
		for (Player p : arena.getPlayers())
			p.sendMessage(DeathMessages.getDeathMessage(killer, killed, death));

		// Check if they reached the max kills, if so end the game
		if (cKiller != null)
		{
			if (cKiller.getPoints() >= cKiller.getArena().getSettings().getPointsToWin())
				Game.end(cKiller.getArena(), false);
		}
	}

}

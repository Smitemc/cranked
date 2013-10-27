
package me.sniperzciinema.cranked.Listeners;

import me.sniperzciinema.cranked.Extras.ScoreBoard.ScoreBoards;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;


//TODO: Set up breaking/placing blocks to work with listeners

public class RankingsToggle implements Listener {

	// Disable dropping items if the player is in game
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
		if (CPlayerManager.getCrankedPlayer(e.getPlayer()).getArena() != null){
			CPlayer cp = CPlayerManager.getCrankedPlayer(e.getPlayer());
				if(cp.getScoreBoard().getShowing() == ScoreBoards.Rankings)
					cp.getScoreBoard().showStats();
				else
					cp.getScoreBoard().showRankings();
				e.setCancelled(true);
				e.getPlayer().setSneaking(false);
		}
	}
}
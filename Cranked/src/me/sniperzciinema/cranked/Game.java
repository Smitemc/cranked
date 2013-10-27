
package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.ArenaHandlers.States;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import me.sniperzciinema.cranked.Tools.Settings;
import me.sniperzciinema.cranked.Tools.Sort;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;


public class Game {

	public static void start(Arena arena) {
		// Respawn all the players
		for (Player p : arena.getPlayers())
			CPlayerManager.getCrackedPlayer(p).respawn();

		// Start the pregame timer
		arena.getTimer().startPreGameTimer();
	}

	public static void end(Arena arena, Boolean timeRanOut) {

		// Reset the timers and state
		arena.getTimer().resetGame();
		arena.setState(States.Waiting);

		Player[] winners = Sort.top3(arena.getPlayers());
		int place = 0;
		// Reset all players, inform them the game ended
		for (Player p : arena.getPlayers())
		{
			CPlayer cp = CPlayerManager.getCrackedPlayer(p);
			p.sendMessage(Msgs.Format_Line.getString());
			if (timeRanOut)
				p.sendMessage(Msgs.GameOver_Times_Up.getString());

			p.sendMessage("");
			for (Player winner : winners)
			{
				if (winner != null)
					p.sendMessage(Msgs.GameOver_Winners.getString("<place>", String.valueOf(place+1), "<player>", winner.getName()));
				place++;
			}

			p.sendMessage("");
			p.sendMessage(Msgs.Format_Line.getString());
			leave(cp);
		}

	}

	public static void join(CPlayer cp, Arena arena) {

		// Get the new arenas settings
		Settings Settings = new Settings(arena);
		Player p = cp.getPlayer();

		// If theres no one in there, then lets start the
		// timer so theres one going for the new player
		if (arena.getPlayers().size() == 0)
			arena.getTimer().startUpdaterTimer();

		// Set the players info
		cp.setInfo();
		cp.setArena(arena);
		cp.getScoreBoard().updateScoreBoard();

		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());

		// Spawn in the player
		p.setFallDistance(0);
		cp.respawn();

		// See if autostart happens yet
		if (arena.getPlayers().size() >= Settings.getRequiredPlayers())
		{
			start(arena);
		}
	}

	public static void leave(CPlayer cp) {
		Arena arena = cp.getArena();
		// Reset the player
		cp.reset();
		cp.getScoreBoard().updateScoreBoard();

		// If there's noone left in the arena, reset it
		if (arena.getPlayers().size() <= 1)
			
			arena.getTimer().stopUpdaterTimer();
			arena.getTimer().stopGameTimer();
			arena.getTimer().stopPreGameTimer();
			for(Player p : arena.getPlayers()){
				p.sendMessage(Msgs.Error_Not_Enough_Players.getString());
				CPlayer cpp = CPlayerManager.getCrackedPlayer(p);
				cpp.reset();
				cpp.getScoreBoard().updateScoreBoard();

			}
	}

}
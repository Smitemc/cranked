
package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.ArenaHandlers.States;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import me.sniperzciinema.cranked.Tools.Settings;

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

	public static void end(Arena arena) {

		// Reset the timers and state
		arena.getTimer().resetGame();
		arena.setState(States.Waiting);

		// Reset all players, inform them the game ended
		for (Player p : arena.getPlayers())
		{
			CPlayer cp = CPlayerManager.getCrackedPlayer(p);

			removePlayer(cp);
			p.sendMessage(Msgs.Game_Ended.getString());
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

		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		// Info the players of their current situation
		p.sendMessage(Msgs.Game_You_Joined_A_Game.getString("<arena>", cp.getArena().getName()));

		for (Player ppl : cp.getArena().getPlayers())
			if (ppl != cp.getPlayer())
				ppl.sendMessage(Msgs.Game_They_Joined_A_Game.getString("<player>", p.getName(), "<arena>", cp.getArena().getName()));
		// Spawn in the player
		p.setFallDistance(0);
		cp.respawn();

		// See if autostart happens yet
		if (arena.getPlayers().size() >= Settings.getRequiredPlayers())
		{
			start(arena);
		}
	}

	public static void removePlayer(CPlayer cp) {
		Arena arena = cp.getArena();

		// Reset the player
		cp.reset();
		cp.getScoreBoard().updateScoreBoard();

		// Tell the player they left
		cp.getPlayer().sendMessage(Msgs.Game_You_Left_A_Game.getString("<arena>", arena.getName()));

		// Update the other players on the situation
		for (Player ppl : arena.getPlayers())
		{
			CPlayerManager.getCrackedPlayer(ppl).getScoreBoard().updateScoreBoard();
			ppl.sendMessage(Msgs.Game_They_Left_A_Game.getString("<player>", cp.getName(), "<arena>", arena.getName()));
		}

		// If there's noone left in the arena, reset it
		if (arena.getPlayers().size() == 0)
			arena.getTimer().stopUpdaterTimer();
	}

}

package me.sniperzciinema.cranked.ArenaHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.sniperzciinema.cranked.Game;
import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.GameMechanics.Agility;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.Messages.Time;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;


public class ArenaTimers {

	private Arena arena;
	private int timeLeft;
	private int pregame;
	private int game;
	private int updater;
	private int updateTime;
	private boolean updateScoreBoard = true;

	public ArenaTimers(Arena arena)
	{
		this.arena = arena;
	}

	public Arena getArena() {
		return arena;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public void stopPreGameTimer() {
		Bukkit.getScheduler().cancelTask(pregame);
	}

	public void stopGameTimer() {
		Bukkit.getScheduler().cancelTask(game);
	}

	public void stopUpdaterTimer() {
		Bukkit.getScheduler().cancelTask(updater);
	}

	public int getTimePreGame() {
		return arena.getSettings().getPregameTime();
	}

	public int getWaitingStatusUpdateTime() {
		return arena.getSettings().getWaitingStatusUpdateTime();
	}

	public void restartUpdaterTimer() {
		stopUpdaterTimer();
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.me, new Runnable()
		{

			@Override
			public void run() {

				startUpdaterTimer();
			}
		}, 1L);
	}

	public void startUpdaterTimer() {
		if (arena.getSettings().isRequiredPlayersEnabled())
		{
			updateTime = getWaitingStatusUpdateTime();
			updater = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable()
			{

				@Override
				public void run() {
					if (updateTime != -1)
					{
						updateTime -= 1;

						updateScoreBoard = !updateScoreBoard;
						if(updateScoreBoard)	
							for (Player player : arena.getPlayers())
								CPlayerManager.getCrackedPlayer(player).getScoreBoard().updateScoreBoard();
					}

					// Send update
					else if (updateTime == -1)
					{
						for (Player player : arena.getPlayers())
						{
							player.sendMessage(Msgs.Arena_StatusUpdate.getString("<current>", String.valueOf(arena.getPlayers().size()), "<needed>", String.valueOf(arena.getSettings().getRequiredPlayers())));
						}
						restartUpdaterTimer();
					}
				}
			}, 0L, 20L);
		}
	}

	public int getGameTime() {
		return arena.getSettings().getGameTime();
	}

	public void resetGame() {
		stopPreGameTimer();
		stopGameTimer();
		timeLeft = getTimePreGame();
	}

	public void startPreGameTimer() {
		stopUpdaterTimer();
		timeLeft = getTimePreGame();
		arena.setState(States.PreGame);

		for (Player player : arena.getPlayers())
		{
			CPlayerManager.getCrackedPlayer(player).respawn();
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,
					Integer.MAX_VALUE, 128));
			player.addPotionEffect(new PotionEffect(
					PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1), true);
		}
		pregame = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable()
		{

			@Override
			public void run() {
				if (timeLeft != -1)
				{
					timeLeft -= 1;
					for (Player player : arena.getPlayers())
					{

						player.setLevel(timeLeft);
						player.setWalkSpeed(0.0F);
						if (timeLeft == (getGameTime() / 4) * 3 || timeLeft == getGameTime() / 2 || timeLeft == getGameTime() / 4 || timeLeft == 5 || timeLeft == 4 || timeLeft == 3 || timeLeft == 2 || timeLeft == 1)
							player.sendMessage(Msgs.Game_PreGame_Time_Left.getString("<time>", Time.getTime((long) timeLeft)));
					}
				}
				// GAME STARTS
				else if (timeLeft == -1)
				{
					startGameTimer();
				}
			}
		}, 0L, 20L);
	}

	public void startGameTimer() {
		stopPreGameTimer();
		arena.setState(States.Started);
		timeLeft = getGameTime();
		for (Player p : arena.getPlayers())
		{
			CPlayerManager.getCrackedPlayer(p).getTimer().startTimer();
			Agility.resetSpeed(p);
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());
		}
		game = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.me, new Runnable()
		{

			@Override
			public void run() {
				if (timeLeft != -1)
				{
					timeLeft -= 1;

					updateScoreBoard = !updateScoreBoard;

					for (Player player : arena.getPlayers())
					{
						player.setLevel(timeLeft);
						if(updateScoreBoard)	
								CPlayerManager.getCrackedPlayer(player).getScoreBoard().updateScoreBoard();

					}

					if (timeLeft == (getGameTime() / 4) * 3 || timeLeft == getGameTime() / 2 || timeLeft == getGameTime() / 4 || timeLeft == 60 || timeLeft == 10 || timeLeft == 9 || timeLeft == 8 || timeLeft == 7 || timeLeft == 6 || timeLeft == 5 || timeLeft == 4 || timeLeft == 3 || timeLeft == 2 || timeLeft == 1)
						for (Player player : arena.getPlayers())
						{
							player.sendMessage(Msgs.Game_Time_Left.getString("<time>", Time.getTime((long) timeLeft)));
						}

				}
				// GAME STARTS
				else if (timeLeft == -1)
				{
					Game.end(arena);
				}
			}
		}, 0L, 20L);
	}
}

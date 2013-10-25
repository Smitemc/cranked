
package me.sniperzciinema.cranked.Listeners;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.ArenaHandlers.States;
import me.sniperzciinema.cranked.GameMechanics.DeathTypes;
import me.sniperzciinema.cranked.GameMechanics.Deaths;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public class DamageEvents implements Listener {

	public Main plugin;

	public DamageEvents(Main instance)
	{
		this.plugin = instance;
	}

	// Player is Damaged, User is Damager
	// When entity is damaged
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player)
		{
			Player victim = (Player) e.getEntity();

			if (ArenaManager.getArena(victim) != null)// Get the attacker
			{
				if (e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE)
				{
					Arena arena = ArenaManager.getArena(victim);

					// If the attack happened before the game started
					if (arena.getState() != States.Started)
					{
						e.setDamage(0);
						e.setCancelled(true);
					}

					// Before a zombie is chosen

					// If the game has fully started
					else
					{
						CPlayer cv = CPlayerManager.getCrackedPlayer(victim);
						Player killer = null;
						if (cv.getLastDamager() != null)
							killer = cv.getLastDamager();

						if ((victim != null) && (killer != null))
						{
							// Saves who hit the person last
							cv.setLastDamager(killer);

							// If it was enough to kill the player
							if (victim.getHealth() - e.getDamage() <= 0)
							{
								Deaths.playerDies(killer, victim, DeathTypes.Melee);
								e.setDamage(0);
							}
						}

					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageByEntityEvent e) {

		// Is the victim a player?
		if (e.getEntity() instanceof Player)
		{
			Player victim = (Player) e.getEntity();
			Player killer = null;

			// If they're in the game
			if (ArenaManager.getArena(victim) != null)
			{
				DeathTypes death = DeathTypes.Melee;

				// Get the attacker
				if (e.getDamager() instanceof Player)
					killer = (Player) e.getDamager();

				else if (e.getDamager() instanceof Arrow)
				{
					victim = (Player) e.getEntity();
					Arrow arrow = (Arrow) e.getDamager();

					if (arrow.getShooter() instanceof Player)
						killer = (Player) arrow.getShooter();
					death = DeathTypes.Arrow;
				}

				if (killer instanceof Player)
				{

					Arena arena = ArenaManager.getArena(victim);

					if (arena.getState() != States.Started)
					{
						e.setDamage(0);
						e.setCancelled(true);
					}

					// If the game has fully started
					else
					{
						CPlayer cv = CPlayerManager.getCrackedPlayer(victim);

						// Saves who hit the person last
						cv.setLastDamager(killer);

						// If it was enough to kill the player
						if (victim.getHealth() - e.getDamage() <= 0)
						{
							e.setDamage(0);
							Deaths.playerDies(killer, victim, death);
						}

					}
				}
			}

		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {

		Player victim = (Player) e.getEntity();
		Player killer = null;

		// If they're in the game
		if (ArenaManager.getArena(victim) != null)
		{

			for (ItemStack is : e.getDrops())
			{
				is.setType(Material.AIR);
			}
			e.setDeathMessage("");
			e.getDrops().clear();
			e.setKeepLevel(true);
			e.getEntity().setHealth(20);

			DeathTypes death = DeathTypes.Melee;

			if (e.getEntity().getKiller() instanceof Player)
				killer = (Player) e.getEntity().getKiller();

			else if (victim.getKiller() instanceof Arrow)
			{
				victim = (Player) e.getEntity();
				Arrow arrow = (Arrow) victim.getKiller();

				if (arrow.getShooter() instanceof Player)
					killer = (Player) arrow.getShooter();
				death = DeathTypes.Arrow;
			} else
			{
				CPlayer cv = CPlayerManager.getCrackedPlayer(victim);
				killer = cv.getLastDamager();
			}

			if (killer instanceof Player)
			{
				Deaths.playerDies(killer, victim, death);
			}
		}
	}

}

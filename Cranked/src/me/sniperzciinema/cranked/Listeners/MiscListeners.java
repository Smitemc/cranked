
package me.sniperzciinema.cranked.Listeners;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;


//TODO: Set up breaking/placing blocks to work with listeners

public class MiscListeners implements Listener {

	private Plugin plugin;

	public MiscListeners(Main instance)
	{
		this.plugin = instance;
	}

	// Disable dropping items if the player is in game
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena() != null)
			if (!CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena().getSettings().canDropBlocks())
				e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		if (!e.isCancelled())
			if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena() != null)
			{
				e.getBlock().getDrops().clear();
				if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena().getBlock(e.getBlock().getLocation()) == null)
					ArenaManager.getArena(e.getPlayer()).setBlock(e.getBlock().getLocation(), e.getBlock().getType());
				else
					ArenaManager.getArena(e.getPlayer()).setBlock(e.getBlock().getLocation(), null);
				e.getBlock().getDrops().clear();
			}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!e.isCancelled())
			if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena() != null)
			{
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					Block b = e.getClickedBlock();
					if (e.getClickedBlock().getType() == Material.CHEST)
					{
						Chest chest = (Chest) b.getState();
						if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena().getChest(b.getLocation()) == null)
							ArenaManager.getArena(e.getPlayer()).setChest(b.getLocation(), chest.getBlockInventory());
					}
				}
			}
	}

	// Check to make sure they arn't trying to place a block in game
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerBlockPlace(BlockPlaceEvent e) {
		if (!e.isCancelled())
			if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena() != null)
			{
				if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena().getBlock(e.getBlock().getLocation()) == null)
					ArenaManager.getArena(e.getPlayer()).setBlock(e.getBlock().getLocation(), Material.AIR);
			}
	}

	// When a player uses a command make sure it does, what its supposed to
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandAttempt(PlayerCommandPreprocessEvent e) {
		String msg = null;
		if (e.getMessage().contains(" "))
		{
			String[] ss = e.getMessage().split(" ");
			msg = ss[0];
		} else
		{
			msg = e.getMessage();
		}

		if (CPlayerManager.getCrackedPlayer(e.getPlayer()).getArena() != null)
			if (!e.getPlayer().isOp())
			{

				// If a player tries a command but is in infected, block all
				// that aren't /inf
				if (plugin.getConfig().getStringList("Blocked Commands").contains(msg.toLowerCase()))
				{
					e.getPlayer().sendMessage(Msgs.Error_Cant_Use_Command.getString());
					e.setCancelled(true);
				} else if (!(plugin.getConfig().getStringList("Allowed Commands").contains(msg.toLowerCase()) || e.getMessage().toLowerCase().contains("inf")))
				{
					e.getPlayer().sendMessage(Msgs.Error_Cant_Use_Command.getString());
					e.setCancelled(true);
				}
			}
	}

	// When a Living Entitie targets another block it if its in a game
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityAtkPlayerLivingEntity(EntityTargetLivingEntityEvent e) {
		if (e.getTarget() instanceof Player)
		{
			Player player = (Player) e.getTarget();

			if (CPlayerManager.getCrackedPlayer(player).getArena() != null)
				e.setCancelled(true);
		}
	}

	// When a Entity targets another, block it if it's in a game
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityAtk(EntityTargetEvent e) {
		if (e.getTarget() instanceof Player)
		{
			Player player = (Player) e.getTarget();

			if (CPlayerManager.getCrackedPlayer(player).getArena() != null)
				e.setCancelled(true);
		}
	}

	// If theres no other plugin that interfers with Food level, do "stuff"
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerHunger(FoodLevelChangeEvent e) {
		if (!e.isCancelled())
		{
			Player player = (Player) e.getEntity();
			if (CPlayerManager.getCrackedPlayer(player).getArena() != null)
				if (!CPlayerManager.getCrackedPlayer(player).getArena().getSettings().canLooseHunger())
					e.setCancelled(true);
		}
	}

	// Block enchantment tables if they're just for show
	@EventHandler(priority = EventPriority.LOW)
	public void PlayerTryEnchant(PrepareItemEnchantEvent e) {
		Player player = e.getEnchanter();

		if (CPlayerManager.getCrackedPlayer(player).getArena() != null)
			e.setCancelled(true);
	}

	// If an entity shoots a bow do "Stuff"
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerShootBow(EntityShootBowEvent e) {
		if (!e.isCancelled())
			if (e.getEntity() instanceof Player)
			{
				Player player = (Player) e.getEntity();
				if (CPlayerManager.getCrackedPlayer(player).getArena() != null)
				{
					e.getProjectile().remove();
					e.setCancelled(true);
					player.updateInventory();
				} else
				{
					e.setCancelled(false);
				}
			}
	}

}
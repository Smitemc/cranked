/**
 * 
 * For more help visit:
 * http://wiki.bukkit.org/Event_API_Reference
 * 
 * For a list on events look into:
 * https://github.com/Bukkit/Bukkit/tree/master/src/main/java/org/bukkit/event
 * 
 * To learn what Priority you should use look at:
 * http://forums.bukkit.org/threads/getting-your-priorities-straight-the-plugin-version.788/
 * 
 */
package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.GameMechanics.CrackedPlayer;
import me.sniperzciinema.cranked.GameMechanics.CrackedPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener{
	
	Main plugin;
	
	public Listeners(Main instance){
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoinCreateCrackedPlayer(PlayerJoinEvent e){
		Player p = e.getPlayer();
		CrackedPlayer cp= new CrackedPlayer(p);
		CrackedPlayerManager.loadCrackedPlayer(cp);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onLeaveDeleteCrackedPlayer(PlayerQuitEvent e){
		CrackedPlayer cp = CrackedPlayerManager.getCrackedPlayer(e.getPlayer());
		CrackedPlayerManager.deleteCrackedPlayer(cp);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onKickedDeleteCrackedPlayer(PlayerKickEvent e){
		CrackedPlayer cp = CrackedPlayerManager.getCrackedPlayer(e.getPlayer());
		CrackedPlayerManager.deleteCrackedPlayer(cp);
	}

}

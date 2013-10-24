
package me.sniperzciinema.cranked.Listeners;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.PlayerClasses.CrackedPlayer;
import me.sniperzciinema.cranked.PlayerClasses.CrackedPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RegisterAndUnRegister implements Listener{
	
	Main plugin;
	
	public RegisterAndUnRegister(Main instance){
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

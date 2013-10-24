
package me.sniperzciinema.cranked.Listeners;

import me.sniperzciinema.cranked.Main;
import me.sniperzciinema.cranked.PlayerClasses.CrankedPlayer;
import me.sniperzciinema.cranked.PlayerClasses.CrankedPlayerManager;

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
		CrankedPlayer cp= new CrankedPlayer(p);
		CrankedPlayerManager.loadCrackedPlayer(cp);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onLeaveDeleteCrackedPlayer(PlayerQuitEvent e){
		CrankedPlayer cp = CrankedPlayerManager.getCrackedPlayer(e.getPlayer());
		CrankedPlayerManager.deleteCrackedPlayer(cp);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onKickedDeleteCrackedPlayer(PlayerKickEvent e){
		CrankedPlayer cp = CrankedPlayerManager.getCrackedPlayer(e.getPlayer());
		CrankedPlayerManager.deleteCrackedPlayer(cp);
	}

}

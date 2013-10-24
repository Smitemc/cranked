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

import org.bukkit.event.Listener;

public class Listeners implements Listener{
	
	Main plugin;
	
	public Listeners(Main instance){
		plugin = instance;
	}

//	@EventHandler(priority = EventPriority.NORMAL)
//	public void onBlockBreak(BlockBreakEvent e){
//		if(Tron.inGameContains(e.getPlayer().getName()) || Tron.inLobbyContains(e.getPlayer().getName()))
//			e.setCancelled(true);
//	}

}

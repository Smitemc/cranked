package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.ArenaClasses.Arena;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.PlayerClasses.CrankedPlayer;
import me.sniperzciinema.cranked.PlayerClasses.CrankedPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Game
{

    public static void start(Arena arena)
    {
    	arena.getTimer().startPreGameTimer();
    }
    public static void end(Arena arena)
    {
    	for(Player p : arena.getPlayers())
    	removePlayer(CrankedPlayerManager.getCrackedPlayer(p));
    }

	public static void join(CrankedPlayer cp, Arena arena) {
		Player p = cp.getPlayer();
		
		cp.setInfo();
		cp.setArena(arena);
		for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
		
		p.sendMessage(Msgs.Game_You_Joined_A_Game.getString());
		
		for(Player ppl : cp.getArena().getPlayers())
			if(ppl != cp.getPlayer())
				ppl.sendMessage(Msgs.Game_They_Joined_A_Game.getString("<player>", p.getName()));
		
		p.setFallDistance(0);
		CrankedPlayerManager.respawn(cp);
		
		if(arena.getPlayers().size() >= 1){
			start(arena);
		}
	}
 
	public static void removePlayer(CrankedPlayer cp) {
		
		cp.getPlayer().sendMessage(Msgs.Game_You_Left_A_Game.getString());
		
		for(Player ppl : cp.getArena().getPlayers())
			if(ppl != cp.getPlayer())
				ppl.sendMessage(Msgs.Game_They_Left_A_Game.getString("<player>", cp.getName()));
			
		cp.reset();
			
		
	}
 
}
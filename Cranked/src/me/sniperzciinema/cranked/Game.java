package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import me.sniperzciinema.cranked.Tools.Settings;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Game
{
	
	public static void start(Arena arena)
    {
    	for(Player p : arena.getPlayers())
    		CPlayerManager.getCrackedPlayer(p).respawn();
    	arena.getTimer().startPreGameTimer();
    }
    public static void end(Arena arena)
    {
    	arena.getTimer().reset();
    	for(Player p : arena.getPlayers()){
    		CPlayer cp = CPlayerManager.getCrackedPlayer(p);
    		
    		removePlayer(cp);
    		p.sendMessage(Msgs.Game_Ended.getString());
    		
    	}
	}

	public static void join(CPlayer cp, Arena arena) {
		Settings Settings = new Settings(arena);
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
		cp.respawn();
		
		if( Settings.isRequiredPlayersEnabled() && arena.getPlayers().size() >= Settings.getRequiredPlayers()){
			start(arena);
		}
	}
 
	public static void removePlayer(CPlayer cp) {
		
		cp.getPlayer().sendMessage(Msgs.Game_You_Left_A_Game.getString());
		
		for(Player ppl : cp.getArena().getPlayers())
			if(ppl != cp.getPlayer())
				ppl.sendMessage(Msgs.Game_They_Left_A_Game.getString("<player>", cp.getName()));
			
		cp.reset();
			
		
	}
 
}
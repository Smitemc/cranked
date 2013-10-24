package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.ArenaClasses.Arena;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.PlayerClasses.CrackedPlayer;
import me.sniperzciinema.cranked.PlayerClasses.CrackedPlayerManager;

import org.bukkit.entity.Player;

public class Game
{

    public static void Start()
    {

    }

	public static void join(CrackedPlayer cp, Arena arena) {
		Player p = cp.getPlayer();
		
		cp.setInfo();
		cp.setArena(arena);
		
		p.sendMessage(Msgs.Game_You_Joined_A_Game.getString());
		
		for(Player ppl : cp.getArena().getPlayers())
			if(ppl != cp.getPlayer())
				ppl.sendMessage(Msgs.Game_They_Joined_A_Game.getString("<player>", p.getName()));
		
		p.setFallDistance(0);
		CrackedPlayerManager.respawn(cp);
	}
 
	public static void removePlayer(CrackedPlayer cp) {
		
		cp.getPlayer().sendMessage(Msgs.Game_You_Left_A_Game.getString());
		
		for(Player ppl : cp.getArena().getPlayers())
			if(ppl != cp.getPlayer())
				ppl.sendMessage(Msgs.Game_They_Left_A_Game.getString("<player>", cp.getName()));
			
		cp.reset();
			
		
	}
 
}
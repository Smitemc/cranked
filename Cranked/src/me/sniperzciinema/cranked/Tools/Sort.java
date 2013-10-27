
package me.sniperzciinema.cranked.Tools;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;


public class Sort {

	public static Player[] top3(List<Player> list) {

		HashMap<Player, Integer> points = new HashMap<Player, Integer>();
		// Get all the players and put them and their score in a new hashmap for
		// Player, Score
		Player[] top = { null };
		for (Player p : list)
		{
			CPlayer cp = CPlayerManager.getCrackedPlayer(p);
			points.put(cp.getPlayer(), cp.getPoints());
		}

		int maxValueInMap = (Collections.max(points.values()));
		int place = 0;

		//keep going through an getting the 1,2,and 3rd but not 4th
		while (place != 3)
		{
			//If the list still has players in it, find the top player
			try
			{
				for (Entry<Player, Integer> e : points.entrySet())
					if (e.getValue() == maxValueInMap)
						top[place] = e.getKey();
			} catch (ArrayIndexOutOfBoundsException AIOBE)
			{
				// Do nothing because that just means it'll be null
			}
			place++;
		}

		return top;
	}
}

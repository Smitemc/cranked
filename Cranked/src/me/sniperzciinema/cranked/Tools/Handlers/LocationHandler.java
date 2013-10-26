
package me.sniperzciinema.cranked.Tools.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;


public class LocationHandler {

	// Get an objects location from a string
	public static Location getObjectLocation(String loc) {
		String[] floc = loc.split(",");
		World world = Bukkit.getServer().getWorld(floc[0]);
		Location Loc = new Location(world, Double.valueOf(floc[1]),
				Double.valueOf(floc[2]), Double.valueOf(floc[3]));
		return Loc;
	}

	// Get a players location from a string(different then getObjectiveLocation
	// because this uses pitch,yaw,checks chunk, and adds +0.5D to Y value)
	public static Location getPlayerLocation(String loc) {
		String[] floc = loc.split(",");
		World world = Bukkit.getServer().getWorld(floc[0]);
		Location Loc = new Location(world, Double.valueOf(floc[1]),
				Double.valueOf(floc[2]) + 0.5D, Double.valueOf(floc[3]),
				Float.valueOf(floc[4]), Float.valueOf(floc[5]));

		if (!Bukkit.getServer().getWorld(world.getName()).getChunkAt(Loc).isLoaded())
			Bukkit.getServer().getWorld(world.getName()).getChunkAt(Loc).load();
		return Loc;
	}

	// Get the location to a string
	public static String getLocationToString(Location loc) {
		double ix = loc.getX();
		double iy = loc.getY();
		double iz = loc.getZ();
		World world = loc.getWorld();
		float yaw = loc.getYaw();
		float pitch = loc.getPitch();
		String s = world.getName() + "," + ix + "," + iy + "," + iz + "," + yaw + "," + pitch;
		return s;
	}
}

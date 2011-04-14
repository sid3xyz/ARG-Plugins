package biz.argirc.Minecraft;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HelperFunctions {

	public static boolean isAdmin(Player player) {
		if (player.isOp()) {
			return true;
		} else {

			return false;
		}
	}

	public static boolean isInt(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static String directionFacing(Player player) {
		Location loc = player.getLocation();
		// player.sendMessage(loc.getBlockX() + " N, " + loc.getBlockZ() +
		// " E, " + loc.getBlockY() + " H");
		String facing[] = { "W", "NW", "N", "NE", "E", "SE", "S", "SW" };
		double yaw = ((loc.getYaw() + 22.5) % 360);
		if (yaw < 0) yaw += 360;
		return facing[(int) (yaw / 45)];

	}
}

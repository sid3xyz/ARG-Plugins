package biz.argirc.Minecraft;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HelperFunctions {

	public static Player getPlayer(String player) {
		List<Player> players = Bukkit.getServer().matchPlayer(player);
		if (players.isEmpty()) {
			return null;
		} else {
			return players.get(0);
		}
	}

	public static boolean isAdmin(Player player) {
		if (player.isOp()) {
			return true;
		} else {

			return false;
		}
	}

}

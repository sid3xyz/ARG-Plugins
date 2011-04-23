package biz.argirc.Minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetDirectionCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		Location loc = player.getLocation();
		player.sendMessage(loc.getBlockX() + " N, " + loc.getBlockZ() + " E, " + loc.getBlockY() + " H");
		String facing[] = { "W", "NW", "N", "NE", "E", "SE", "S", "SW" };
		double yaw = ((loc.getYaw() + 22.5) % 360);
		if (yaw < 0) yaw += 360;
		player.sendMessage("Facing " + ChatColor.RED + facing[(int) (yaw / 45)]);
		return true;
	}
}

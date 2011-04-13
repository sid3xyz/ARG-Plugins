package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.HelperFunctions;

public class SetSpawnLocationCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (!HelperFunctions.isAdmin(player)) {
			player.sendMessage("You are not an admin.");
			return true;
		}

		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		return player.getWorld().setSpawnLocation(x, y, z);

	}

}

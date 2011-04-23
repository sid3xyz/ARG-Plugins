package biz.argirc.Minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.Functions.ArenaFunctions;
import biz.argirc.Minecraft.Functions.HelperFunctions;

public class RestoreInventoryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!HelperFunctions.isAdmin((Player) sender)) {
			sender.sendMessage("You are not an admin.");
			return true;
		}
		Player player = Bukkit.getServer().getPlayer(args[0]);
		ArenaFunctions.restoreInventory(player);

		return true;
	}

}

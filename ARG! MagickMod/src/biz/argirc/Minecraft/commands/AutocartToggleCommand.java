package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.MagickMod;
import biz.argirc.Minecraft.Functions.HelperFunctions;

public class AutocartToggleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (!HelperFunctions.isAdmin(player)) {
			player.sendMessage("You are not an admin.");
			return true;
		}

		if (args[0].equalsIgnoreCase("on")) {
			MagickMod.setAutocartStatus(true);

		}
		if (args[0].equalsIgnoreCase("off")) {
			MagickMod.setAutocartStatus(false);

		}
		sender.sendMessage("Autocarts are " + MagickMod.autocartStatus);

		return true;
	}

}

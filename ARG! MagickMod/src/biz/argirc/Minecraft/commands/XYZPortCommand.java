package biz.argirc.Minecraft.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.MagickMod;

public class XYZPortCommand implements CommandExecutor {
	private final MagickMod	plugin;

	public XYZPortCommand(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender.isOp() == false) {
			sender.sendMessage("You must be an Op to do this...");
			return false;
		}
		if (args.length == 3) {
			int x = Integer.valueOf(args[0]);
			int y = Integer.valueOf(args[1]);
			int z = Integer.valueOf(args[2]);
			if (plugin.checkSafe(x, y, z)) {
				Location targetLocation = this.plugin.getServer().getWorld("world").getBlockAt(x, y, z).getLocation();
				// Player player = (Player) sender;
				targetLocation.setY(y + 1);
				sender.sendMessage("Teleporting now...");
				((Player) sender).teleport(targetLocation);
				return true;
			} else {
				sender.sendMessage("It would be unwise to teleport there...");
				return true;
			}
		}
		sender.sendMessage("Invalid location.");
		return true;
	}
}

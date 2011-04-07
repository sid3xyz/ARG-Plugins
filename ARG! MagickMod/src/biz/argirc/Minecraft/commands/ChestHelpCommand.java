package biz.argirc.Minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestHelpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		Player player = (Player) sender;
		player.sendMessage(ChatColor.GOLD + "ARG! Chest protect help");
		player.sendMessage(ChatColor.WHITE + "Chests are locked to you on placement.");
		player.sendMessage(ChatColor.WHITE + "To unlock a chest, target it, and type /unlock");
		player.sendMessage(ChatColor.WHITE + "Target chest will become PUBLIC and open to everyone.");
		return false;
	}

}

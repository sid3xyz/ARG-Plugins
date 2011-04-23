package biz.argirc.Minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String target = args[0];
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase(target)) {
				// zap this player
			} else {
				sender.sendMessage("Invalid Player Name");
			}
		}
		return true;
	}
}

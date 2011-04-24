package biz.argirc.Minecraft.commands;

import java.lang.reflect.Array;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (Array.getLength(args) > 0) {
			for (String player : args) {
				Player p = Bukkit.getServer().getPlayer(player);
				p.getWorld().strikeLightning(p.getLocation());
			}

		}

		return true;
	}
}

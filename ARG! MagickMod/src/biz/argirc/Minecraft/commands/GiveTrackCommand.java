package biz.argirc.Minecraft.commands;

import java.lang.reflect.Array;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import biz.argirc.Minecraft.HelperFunctions;

public class GiveTrackCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (!HelperFunctions.isAdmin(player)) {
			player.sendMessage("You are not an admin.");
			return true;
		}
		if (Array.getLength(args) == 0) {
			Player p = (Player) sender;
			ItemStack rails = new ItemStack(Material.RAILS, 64);
			sender.getServer().getWorld("world").dropItem(p.getLocation(), rails);
		} else if (HelperFunctions.isInt(args[0])) {
			Player p = (Player) sender;
			for (int i = 0; i <= Integer.parseInt(args[0]); i++) {
				ItemStack rails = new ItemStack(Material.RAILS, 64);
				sender.getServer().getWorld("world").dropItem(p.getLocation(), rails);
			}
		}
		Player p = Bukkit.getServer().getPlayer(args[0]);
		if (p != null) {

			for (int i = 0; i <= Integer.parseInt(args[1]); i++) {
				ItemStack rails = new ItemStack(Material.RAILS, 64);
				sender.getServer().getWorld("world").dropItem(p.getLocation(), rails);
			}
		}

		return true;
	}

}

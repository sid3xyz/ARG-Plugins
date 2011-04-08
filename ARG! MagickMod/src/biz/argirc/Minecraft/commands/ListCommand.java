package biz.argirc.Minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.MagickMod;

public class ListCommand implements CommandExecutor {
	private final MagickMod	plugin;

	public ListCommand(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		String tempList = "";
		int x = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if (p != null && x + 1 == playerCount()) {
				tempList += p.getName();
				x++;
			}
			if (p != null && x < playerCount()) {
				tempList += p.getName() + ", ";
				x++;
			}
		}
		// Output the player list
		player.sendMessage(ChatColor.RED + "Player List (" + ChatColor.WHITE + tempList + ChatColor.RED + ")");
		player.sendMessage(ChatColor.RED + "Total Players: " + ChatColor.GREEN + playerCount());
		return true;
	}

	public int playerCount() {
		Player players[] = plugin.getServer().getOnlinePlayers();
		int x = 0;
		for (@SuppressWarnings("unused")
		Player p : players) {
			x++;
		}
		return x;
	}
}

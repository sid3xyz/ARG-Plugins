package biz.argirc.Minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		String tempList = "";
		int x = 0;
		for (Player p : player.getServer().getOnlinePlayers()) {
			if (p != null && x + 1 == playerCount(player.getServer())) {
				tempList += p.getName();
				x++;
			}
			if (p != null && x < playerCount(player.getServer())) {
				tempList += p.getName() + ", ";
				x++;
			}
		}
		// Output the player list
		player.sendMessage(ChatColor.RED + "Player List (" + ChatColor.WHITE + tempList + ChatColor.RED + ")");
		player.sendMessage(ChatColor.RED + "Total Players: " + ChatColor.GREEN + playerCount(player.getServer()));
		return true;
	}

	public int playerCount(Server server) {
		Player players[] = server.getOnlinePlayers();
		int x = 0;
		for (@SuppressWarnings("unused")
		Player p : players) {
			x++;
		}
		return x;
	}
}

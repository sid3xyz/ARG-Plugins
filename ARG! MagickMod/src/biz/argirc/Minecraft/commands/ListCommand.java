package biz.argirc.Minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {
	private final Server	server;

	public ListCommand(Server server) {
		this.server = server;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		String tempList = "";
		int x = 0;
		for (Player p : server.getOnlinePlayers()) {
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
		Player players[] = server.getOnlinePlayers();
		int x = 0;
		for (@SuppressWarnings("unused")
		Player p : players) {
			x++;
		}
		return x;
	}
}

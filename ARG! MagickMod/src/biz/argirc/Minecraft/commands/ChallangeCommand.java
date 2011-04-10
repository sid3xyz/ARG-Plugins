package biz.argirc.Minecraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.MagickMod;

public class ChallangeCommand implements CommandExecutor {

	public final MagickMod	plugin;

	public ChallangeCommand(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		List<Player> players = new ArrayList<Player>();
		players.add(player);

		for (String target : args) {
			Player targetPlayer = getPlayer(sender, target);
			players.add(targetPlayer);
			targetPlayer.sendMessage("You have been challenged to an Arena battle by " + player.getName() + "!");
			targetPlayer.sendMessage("Arena battle system almost complete!");
		}

		return true;
	}

	public Player getPlayer(CommandSender sender, String player) {
		List<Player> players = sender.getServer().matchPlayer(player);
		if (players.isEmpty()) {
			return null;
		} else {
			return players.get(0);
		}
	}
}

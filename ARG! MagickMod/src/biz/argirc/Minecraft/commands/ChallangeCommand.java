package biz.argirc.Minecraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.ArenaFunctions;
import biz.argirc.Minecraft.HelperFunctions;

public class ChallangeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player player = (Player) sender;
		if (!HelperFunctions.isAdmin(player)) {
			player.sendMessage("You are not an admin.");
			return true;
		}

		List<Player> players = new ArrayList<Player>();
		players.add(player);

		for (String target : args) {
			Player targetPlayer = Bukkit.getServer().getPlayer(target);
			targetPlayer.sendMessage("You have been challenged to an Arena battle by " + player.getName() + "!");
			targetPlayer.sendMessage("Arena battle system almost complete!");
		}

		ArenaFunctions.StartBattle(players);
		return true;
	}
}

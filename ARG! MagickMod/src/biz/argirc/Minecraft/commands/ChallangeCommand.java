package biz.argirc.Minecraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			Player targetPlayer = HelperFunctions.getPlayer(target);
			players.add(targetPlayer);
			targetPlayer.sendMessage("You have been challenged to an Arena battle by " + player.getName() + "!");
			targetPlayer.sendMessage("Arena battle system almost complete!");
		}
		// plugin.arenaFunctions.storeInventories(players);
		// plugin.arenaFunctions.StartBattle(players);
		return true;
	}
}

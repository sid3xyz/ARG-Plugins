package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.Functions.HelperFunctions;
import biz.argirc.Minecraft.Functions.RankFunctions;

public class SetRankCommand implements CommandExecutor {
	private final RankFunctions	rankFunctions;

	public SetRankCommand(RankFunctions rankFunctions) {
		this.rankFunctions = rankFunctions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (!HelperFunctions.isAdmin(player)) {
			player.sendMessage("You are not an admin.");
			return true;
		}
		rankFunctions.setRank(args[0], Integer.parseInt(args[1]));
		return true;
	}
}

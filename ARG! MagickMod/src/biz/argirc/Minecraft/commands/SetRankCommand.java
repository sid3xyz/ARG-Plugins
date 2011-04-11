package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import biz.argirc.Minecraft.RankFunctions;

public class SetRankCommand implements CommandExecutor {
	private final RankFunctions	rankFunctions;

	public SetRankCommand(RankFunctions rankFunctions) {
		this.rankFunctions = rankFunctions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.isOp()) {
			return false;
		}
		rankFunctions.setRank(args[0], Integer.parseInt(args[1]));
		return true;
	}
}

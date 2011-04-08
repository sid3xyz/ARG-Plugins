package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import biz.argirc.Minecraft.MagickMod;

public class SetRankCommand implements CommandExecutor {
	private final MagickMod	plugin;

	public SetRankCommand(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		plugin.rankFunctions.setRank(args[0], Integer.parseInt(args[1]));
		return true;
	}

}

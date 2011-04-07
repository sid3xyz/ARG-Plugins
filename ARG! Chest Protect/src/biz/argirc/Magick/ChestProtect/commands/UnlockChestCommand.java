package biz.argirc.Magick.ChestProtect.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import biz.argirc.Magick.ChestProtect.ChestProtect;

public class UnlockChestCommand implements CommandExecutor {
	private final ChestProtect	plugin;

	public UnlockChestCommand(ChestProtect plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}

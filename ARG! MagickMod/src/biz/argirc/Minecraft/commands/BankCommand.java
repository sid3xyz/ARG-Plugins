package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.MagickMod;
import biz.argirc.Minecraft.database.BankData;

public class BankCommand implements CommandExecutor {
	public final MagickMod	plugin;

	public BankCommand(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		BankData bankAccount = plugin.getDatabase().find(BankData.class).where().ieq("playerName", player.getName()).findUnique();

		player.sendMessage("Account Balance: " + bankAccount.getBalance());

		return true;
	}
}

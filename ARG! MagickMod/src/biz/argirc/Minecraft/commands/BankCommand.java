package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.database.BankData;

import com.avaje.ebean.EbeanServer;

public class BankCommand implements CommandExecutor {
	public final EbeanServer	database;

	public BankCommand(EbeanServer database) {
		this.database = database;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		BankData bankAccount = database.find(BankData.class).where().ieq("playerName", player.getName()).findUnique();

		player.sendMessage("Account Balance: " + bankAccount.getBalance());

		return false;
	}
}

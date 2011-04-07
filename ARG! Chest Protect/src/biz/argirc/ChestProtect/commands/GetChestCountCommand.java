package biz.argirc.ChestProtect.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.ChestProtect.ChestProtect;
import biz.argirc.ChestProtect.database.ChestData;

public class GetChestCountCommand implements CommandExecutor {
	private final ChestProtect	plugin;

	public GetChestCountCommand(ChestProtect plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		getUserChestCount(player, args[0]);
		return true;
	}

	public void getUserChestCount(Player player, String userstring) {
		List<ChestData> ChestList = plugin.getDatabase().find(ChestData.class).where().ieq("playerName", userstring).findList();
		if (ChestList.isEmpty()) {
			player.sendMessage("That player has no chests!");
		} else {
			player.sendMessage("Player has : " + ChestList.size() + " chests");
		}
	}
}

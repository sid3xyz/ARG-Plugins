package biz.argirc.Minecraft.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.ChestFunctions;
import biz.argirc.Minecraft.database.ChestData;

public class GetChestCountCommand implements CommandExecutor {
	private final ChestFunctions	chestFunctions;

	public GetChestCountCommand(ChestFunctions chestFunctions) {
		this.chestFunctions = chestFunctions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		getUserChestCount(player, args[0]);
		return true;
	}

	public void getUserChestCount(Player player, String userstring) {
		List<ChestData> ChestList = chestFunctions.plugin.getDatabase().find(ChestData.class).where().ieq("playerName", userstring).findList();
		if (ChestList.isEmpty()) {
			player.sendMessage("That player has no chests!");
		} else {
			player.sendMessage("Player has : " + ChestList.size() + " chests");
		}
	}
}

package biz.argirc.Magick.ChestProtect.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Magick.ChestProtect.ChestProtect;
import biz.argirc.Magick.ChestProtect.database.ChestData;

public class UnlockChestCommand implements CommandExecutor {
	private final ChestProtect	plugin;

	public UnlockChestCommand(ChestProtect plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		Block chestToLock = player.getTargetBlock(null, 5);

		if (chestToLock.getTypeId() == 54) {
			String owner = plugin.getOwner(chestToLock.getLocation());
			if (owner.equalsIgnoreCase(player.getName())) {
				ChestData myChest = plugin.getChest(chestToLock.getLocation());
				myChest.setName("public");
				plugin.getDatabase().save(myChest);
			}

		}

		return true;
	}
}

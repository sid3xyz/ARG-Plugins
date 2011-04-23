package biz.argirc.Minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.Functions.ChestFunctions;
import biz.argirc.Minecraft.database.ChestData;

public class UnlockChestCommand implements CommandExecutor {
	private final ChestFunctions	chestFunctions;

	public UnlockChestCommand(ChestFunctions chestFunctions) {
		this.chestFunctions = chestFunctions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		Block chestToLock = player.getTargetBlock(null, 5);

		if (chestToLock.getTypeId() == 54) {
			String owner = chestFunctions.getOwner(chestToLock.getLocation());
			if (owner.equalsIgnoreCase(player.getName())) {
				ChestData myChest = chestFunctions.getChest(chestToLock.getLocation());
				myChest.setName("public");
				chestFunctions.plugin.getDatabase().save(myChest);
				BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
				for (BlockFace blockFace : faces) {
					Block face = chestToLock.getFace(blockFace);
					if (face.getTypeId() == 54) {
						ChestData thatChest = chestFunctions.getChest(face.getLocation());
						thatChest.setName("public");
						chestFunctions.plugin.getDatabase().save(thatChest);
					}
				}
				player.sendMessage(ChatColor.DARK_PURPLE + "this is now a PUBLIC chest");
			}

		}

		return true;
	}
}
package biz.argirc.ChestProtect.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import biz.argirc.ChestProtect.ChestProtect;
import biz.argirc.ChestProtect.database.ChestData;

public class ChestListener extends BlockListener {

	private final ChestProtect	plugin;

	public ChestListener(ChestProtect plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {

		switch (event.getBlock().getType()) {
			case CHEST:
				if (!plugin.chestFunctions.doesUserOwnChest(event.getPlayer().getName(), event.getBlock().getLocation())) {
					event.setCancelled(true);
					event.getPlayer().sendMessage("You do not own this chest");
					return;
				}
			default:
				return;
		}
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		Block placedBlock = event.getBlockPlaced();
		switch (placedBlock.getType()) {
			case CHEST:
				Player player = event.getPlayer();
				String myOwner = player.getName();
				boolean allclear = false;
				BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
				String thatOwner = "";
				for (BlockFace blockFace : faces) {
					Block face = placedBlock.getFace(blockFace);
					if (face.getTypeId() == 54) {
						thatOwner = plugin.chestFunctions.getOwner(face.getLocation());
						if (!thatOwner.equalsIgnoreCase(player.getName())) {
							myOwner = thatOwner;
							allclear = false;
							event.setCancelled(true);
							return;
						}
						if (thatOwner.equalsIgnoreCase("public")) {
							myOwner = "public";
							allclear = true;
						}
					}
				}
				if (allclear) {
					ChestData chest = new ChestData();
					chest.setName(myOwner);
					chest.setPlayerName(player.getName());
					chest.setLocation(placedBlock.getLocation().toString());
					plugin.chestFunctions.saveData(chest);
					if (!myOwner.equals("public")) {
						player.sendMessage(ChatColor.GOLD + "You are now the owner of this chest");
						return;
					} else {
						player.sendMessage("You have expanded a public chest");
						return;
					}
				} else {
					player.sendMessage("Unable to place chest here");
					return;
				}
			default:
				return;
		}
	}
}
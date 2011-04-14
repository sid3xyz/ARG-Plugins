package biz.argirc.Minecraft.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import biz.argirc.Minecraft.HelperFunctions;
import biz.argirc.Minecraft.MagickMod;
import biz.argirc.Minecraft.database.ChestData;

public class ChestBlockListener extends BlockListener {

	private final MagickMod	plugin;

	public ChestBlockListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		switch (event.getBlock().getType()) {
			case CHEST:
				Player player = event.getPlayer();
				if (HelperFunctions.isAdmin(player)) {
					player.sendMessage("You have destroid " + plugin.chestFunctions.getOwner(event.getBlock().getLocation()) + "chest");
					return;
				}

				if (plugin.chestFunctions.isPublicChest(event.getBlock().getLocation())) {
					System.out.println("Removed chest @" + event.getBlock().getLocation().toString());
					return;
				}

				if (plugin.chestFunctions.doesUserOwnChest(event.getPlayer().getName(), event.getBlock().getLocation())) {
					System.out.println("Removed chest @" + event.getBlock().getLocation().toString());
					return;
				}

				event.setCancelled(true);
				event.getPlayer().sendMessage("You may not remove this chest");
				return;

			default:
				return;
		}
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Block placedBlock = event.getBlockPlaced();
		switch (placedBlock.getType()) {
			case CHEST:
				Player player = event.getPlayer();
				String myOwner = player.getName();
				boolean allclear = true;
				BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
				String thatOwner = "";
				for (BlockFace blockFace : faces) {
					Block face = placedBlock.getFace(blockFace);
					if (face.getTypeId() == 54) {
						thatOwner = plugin.chestFunctions.getOwner(face.getLocation());
						if (!thatOwner.equalsIgnoreCase(player.getName())) {
							if (thatOwner.equalsIgnoreCase("public")) {
								myOwner = "public";
								allclear = true;
							} else {
								myOwner = thatOwner;
								player.sendMessage("You can not expand this chest");
								allclear = false;
								event.setCancelled(true);
								return;
							}
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
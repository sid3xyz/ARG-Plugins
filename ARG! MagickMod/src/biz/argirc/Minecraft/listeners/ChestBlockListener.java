package biz.argirc.Minecraft.listeners;

import org.bukkit.Location;
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
				System.out.println("Remove chest start");
				Player player = event.getPlayer();
				if (plugin.chestFunctions.getOwner(event.getBlock().getLocation()) == "null") {
					System.out.println("Removed chest");
					return;
				}

				if (HelperFunctions.isAdmin(player)) {
					player.sendMessage("You have destroid " + plugin.chestFunctions.getOwner(event.getBlock().getLocation()) + "chest");
					plugin.chestFunctions.deleteChest(event.getBlock().getLocation());
					return;
				}

				if (plugin.chestFunctions.isPublicChest(event.getBlock().getLocation())) {
					System.out.println("Removed chest @" + event.getBlock().getLocation().toString());
					plugin.chestFunctions.deleteChest(event.getBlock().getLocation());
					return;
				}

				if (plugin.chestFunctions.doesUserOwnChest(event.getPlayer().getName(), event.getBlock().getLocation())) {
					System.out.println("Removed chest @" + event.getBlock().getLocation().toString());
					plugin.chestFunctions.deleteChest(event.getBlock().getLocation());
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
				BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
				String thatOwner = "";
				for (BlockFace blockFace : faces) {
					Block face = placedBlock.getFace(blockFace);
					if (face.getTypeId() == 54) {
						thatOwner = plugin.chestFunctions.getOwner(face.getLocation());
						String chestOwner = thatOwner;
						if (chestOwner.equalsIgnoreCase(player.getName())) {
							placeChest(chestOwner, placedBlock.getLocation());
							return;
						}
						if (chestOwner.equalsIgnoreCase("public")) {
							placeChest("public", placedBlock.getLocation());
							player.sendMessage("You have expanded a public Chest");
							return;
						}
						player.sendMessage("You can not place a chest here.");
						event.setCancelled(true);
					}

				}
				placeChest(player.getName(), placedBlock.getLocation());
			default:
				return;
		}
	}

	public void placeChest(String owner, Location chestLocation) {
		ChestData chest = new ChestData();
		chest.setName(owner);
		chest.setPlayerName(owner);
		chest.setLocation(chestLocation.toString());
		plugin.chestFunctions.saveData(chest);

	}
}
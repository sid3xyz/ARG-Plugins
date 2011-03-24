package com.bukkit.Magick.ARGAntiPirate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class ARG_ThiefProtect {
	private ARGAntiPirate	plugin;

	private Properties		ChestDatabase	= null;

	public ARG_ThiefProtect(final ARGAntiPirate plugin, Properties ChestDatabase) {
		this.plugin = plugin;
		this.ChestDatabase = ChestDatabase;
		try {
			if (!ARGAntiPirate.ChestData.exists()) {
				new File(ARGAntiPirate.maindirectory).mkdir();
				ARGAntiPirate.ChestData.createNewFile();
			}
			FileInputStream inn = new FileInputStream(ARGAntiPirate.ChestData);
			ChestDatabase.load(inn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getOwner(Location l) {
		String myLocation = l.toString();
		String owner = ChestDatabase.getProperty(myLocation);
		if (owner == null) {
			return "null";
		}
		return owner;

	}

	public boolean lockIt(Player p, Block placedBlock) {
		try {

			BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
			for (BlockFace blockFace : faces) {
				Block face = placedBlock.getFace(blockFace);
				if (face.getTypeId() == 54) {
					String myOwner = ChestDatabase.getProperty(face.getLocation().toString());
					if (myOwner.equalsIgnoreCase("Public")) {
						p.sendMessage(ChatColor.GOLD + "You have expanded a public chest.");
						ChestDatabase.setProperty(placedBlock.getLocation().toString(), "Public");
						ChestDatabase.store(new FileOutputStream(ARGAntiPirate.ChestData), null);
						return true;
					} else if (!myOwner.equalsIgnoreCase(p.getName())) {
						p.sendMessage(ChatColor.RED + "You do not have permission to place a chest here");

						return false;
					}
				}
			}
			p.sendMessage(ChatColor.GOLD + "You are now the owner of this chest");
			ChestDatabase.setProperty(placedBlock.getLocation().toString(), p.getName());
			ChestDatabase.store(new FileOutputStream(ARGAntiPirate.ChestData), null);
			return true;
		} catch (IOException e) {
			p.sendMessage(ChatColor.RED + "Something went wrong!");
			return false;
		}
	}

	public boolean openChest(Player player, Block targetChest) {

		String myOwner = getOwner(targetChest.getLocation());
		player.sendMessage("Owner: " + myOwner);
		if (myOwner.equals(player.getName()) || plugin.rankMachine.getRank(player) > 4) {

			return true;
		} else if (myOwner.equals("null") || myOwner.equalsIgnoreCase("Public")) {
			player.sendMessage("This is a Public Chest");
			return true;
		}
		return false;
	}

	public boolean removeChest(Player p, Block chestLocation) {
		try {
			String myOwner = getOwner(chestLocation.getLocation());
			if (myOwner.equals(p.getName()) || myOwner.equals("Public") || myOwner.equals("null")) {
				ChestDatabase.remove(chestLocation.getLocation().toString());
				ChestDatabase.store(new FileOutputStream(ARGAntiPirate.ChestData), null);
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			p.sendMessage(ChatColor.RED + "Something went wrong");
			return false;
		}

	}

	public boolean unlockIt(Player p) {

		Block chestToLock = p.getTargetBlock(null, 5);
		if (chestToLock.getTypeId() == 54) {
			String myOwner = getOwner(chestToLock.getLocation());
			if (myOwner.equalsIgnoreCase(p.getName())) {

				try {

					BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
					for (BlockFace blockFace : faces) {
						Block face = chestToLock.getFace(blockFace);
						// They're placing it beside a chest
						if (face.getTypeId() == 54) {
							if (myOwner.toString().equalsIgnoreCase(p.getName())) {
								ChestDatabase.setProperty(face.getLocation().toString(), "Public");
							}
						}
					}
					p.sendMessage(ChatColor.RED + "This Chest is now PUBLIC.");
					ChestDatabase.setProperty(chestToLock.getLocation().toString(), "Public");
					ChestDatabase.store(new FileOutputStream(ARGAntiPirate.ChestData), null);
				} catch (IOException e) {
					p.sendMessage(ChatColor.RED + "Something went wrong!");
				}
				return true;
			} else {
				p.sendMessage(ChatColor.RED + "This is not your chest");
				return true;
			}
		} else {
			return true;
		}
	}
}
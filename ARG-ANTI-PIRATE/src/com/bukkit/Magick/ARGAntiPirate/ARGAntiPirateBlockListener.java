package com.bukkit.Magick.ARGAntiPirate;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ARGAntiPirateBlockListener extends BlockListener {
	// private ARGAntiPirate plugin;
	public ARGAntiPirateBlockListener(final ARGAntiPirate plugin) {
		// this.plugin = plugin;
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		if (ARGAntiPirate.rankMachine.getRank(p) <= 0) {
			p.sendMessage(ChatColor.RED + "You do not have permission to place blocks, please contact an Admin");
			event.setCancelled(true);
			return;
		}
		Block placedBlock = event.getBlock();
		Location chestLocation = placedBlock.getLocation();

		if (placedBlock.getTypeId() == 54) {
			if (ARGAntiPirate.chestMachine.lockIt(p, placedBlock)) {
				return;
			} else {
				event.setCancelled(true);
			}
		} else if ((event.getItemInHand().getTypeId() == 54 && event.getBlockReplacedState().getTypeId() == 78)) {
			double Ypos = placedBlock.getLocation().getY();
			chestLocation.setY(Ypos - 1);
			placedBlock = placedBlock.getWorld().getBlockAt(chestLocation);
			if (ARGAntiPirate.chestMachine.lockIt(p, placedBlock)) {
				return;
			} else {
				event.setCancelled(true);
			}
		}

	}

	@Override
	public void onBlockInteract(BlockInteractEvent event) {
		// if the event is caused by a player
		if (event.isPlayer()) {
			Player player = (Player) event.getEntity();
			if (ARGAntiPirate.rankMachine.getRank(player) <= 0) {
				player.sendMessage(ChatColor.RED
						+ "You do not have permission to place blocks, please contact an Admin");
				event.setCancelled(true);
				return;
			}
			if (event.getBlock().getTypeId() == 54
					&& ARGAntiPirate.chestMachine.openChest(player, event.getBlock()) == true) {
				player.sendMessage(ChatColor.GREEN + "Access Granted");
				return;
			} else if (event.getBlock().getTypeId() == 54) {
				player.sendMessage(ChatColor.RED + "You do not have permission to access this chest");
				event.setCancelled(true);
				return;
			}
		}
	}

	@Override
	public void onBlockDamage(BlockDamageEvent event) {

	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		if (ARGAntiPirate.rankMachine.getRank(p) <= 0) {
			p.sendMessage(ChatColor.RED + "You do not have permission to break blocks, please contact an Admin");
			event.setCancelled(true);
			return;
		} else {
			Block damagedBlock = event.getBlock();
			if (damagedBlock.getTypeId() == 54) {
				if (ARGAntiPirate.chestMachine.removeChest(p, damagedBlock) == true) {
					p.sendMessage(ChatColor.RED + "Chest has been removed from the database.");
					return;
				} else {
					p.sendMessage(ChatColor.RED + "You are not allowed to remove this chest.");
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
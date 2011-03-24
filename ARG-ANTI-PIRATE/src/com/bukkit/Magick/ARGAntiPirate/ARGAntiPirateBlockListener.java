package com.bukkit.Magick.ARGAntiPirate;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRightClickEvent;

public class ARGAntiPirateBlockListener extends BlockListener {
	private final ARGAntiPirate	plugin;

	public ARGAntiPirateBlockListener(final ARGAntiPirate plugin) {
		this.plugin = plugin;
	}

	boolean inSpawn(Player player) {

		double x = plugin.Spawn.getX() - player.getLocation().getX();
		double y = plugin.Spawn.getY() - player.getLocation().getY();
		double z = plugin.Spawn.getZ() - player.getLocation().getZ();
		double distance = x * x + y * y + z * z;

		return (distance <= 100 * 100);

	}

	@SuppressWarnings("unused")
	private boolean isPlayerWithinRadius(Player player, Location loc, double radius) {
		double x = loc.getX() - player.getLocation().getX();
		double y = loc.getY() - player.getLocation().getY();
		double z = loc.getZ() - player.getLocation().getZ();
		double distance = x * x + y * y + z * z;

		return (distance <= radius * radius);
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player p = event.getPlayer();
		Block damagedBlock = event.getBlock();
		// System.out.println(p.getName()+ ": destroyed -> "+
		// event.getBlock().getType());
		int myRank = plugin.rankMachine.getRank(p);
		if (myRank <= 0) {
			p.sendMessage(ChatColor.RED + "You do not have permission to break blocks, please contact an Admin");
			event.setCancelled(true);
			return;
		}
		if (plugin.isWorldProtected() && myRank < 4) {
			event.setCancelled(true);
			return;

		}
		if (inSpawn(p) && myRank < 4) {
			p.sendMessage(ChatColor.RED + "You are in the Protected Habitat");
			event.setCancelled(true);
			return;
		}
		if (damagedBlock.getTypeId() == 54) {
			if (plugin.chestMachine.removeChest(p, damagedBlock) == true) {
				p.sendMessage(ChatColor.RED + "Chest has been removed from the database.");
				return;
			} else {
				p.sendMessage(ChatColor.RED + "You are not allowed to remove this chest.");

				event.setCancelled(true);
				return;
			}
		}
	}

	@Override
	public void onBlockBurn(BlockBurnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		event.setCancelled(true);
		return;/*
				 * if (plugin.isWorldProtected()) { event.setCancelled(true);
				 * return; } if(event.getBlock().getTypeId() == 18){
				 * event.setCancelled(true); return; }
				 */
	}

	@Override
	public void onBlockDamage(BlockDamageEvent event) {

	}

	@Override
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (event.isCancelled()) {
			return;
		}
		event.setCancelled(true);
		return;/*
				 * // If this block is a leaf block, cancel the ignite, no
				 * matter what - // this will stop forest fires and people using
				 * fire // to clear trees if (plugin.isWorldProtected()) {
				 * event.setCancelled(true); return; } /* IgniteCause cause =
				 * event.getCause(); if (cause == IgniteCause.SPREAD) {
				 * 
				 * event.setCancelled(true); return; } if (cause !=
				 * IgniteCause.FLINT_AND_STEEL) {
				 * 
				 * event.setCancelled(true); return; }
				 */
	}

	@Override
	public void onBlockRightClick(BlockRightClickEvent event) {

		if ((event.getBlock().getType() != Material.CHEST && event.getBlock().getType() != Material.WORKBENCH && event.getBlock().getType() != Material.FURNACE && event.getBlock().getType() != Material.DISPENSER && event.getBlock().getType() != Material.STONE_BUTTON && event.getBlock().getType() != Material.LEVER && event.getBlock().getType() != Material.WOODEN_DOOR && event.getBlock().getType() != Material.IRON_DOOR_BLOCK && event.getBlock().getType() != Material.FIRE && event.getBlock().getType() != Material.CAKE_BLOCK)) {
			if (event.getItemInHand().getType() == Material.FENCE) {
				Block b = event.getBlock().getFace(event.getDirection(), 1);
				if (b.getTypeId() == 0) {

					BlockState oldState = b.getState();
					b.setType(event.getItemInHand().getType());

					BlockPlaceEvent placeEvent = new BlockPlaceEvent(Type.BLOCK_PLACED, b, oldState, event.getBlock(), event.getItemInHand(), event.getPlayer(), true);
					plugin.getServer().getPluginManager().callEvent(placeEvent);

					if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
						b.setType(oldState.getType());
						b.setData(oldState.getData().getData());
					} else {
						int am = event.getItemInHand().getAmount();
						if (am > 1)
							event.getItemInHand().setAmount(am - 1);
						else
							event.getPlayer().getInventory().remove(event.getItemInHand());
					}
				}
			}
		}
	}

	@Override
	public void onBlockInteract(BlockInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		// if the event is caused by a player

		if (event.isPlayer()) {
			Player player = (Player) event.getEntity();

			if (event.getBlock().getTypeId() == 54 && plugin.chestMachine.openChest(player, event.getBlock()) == true) {
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
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player p = event.getPlayer();

		if (!plugin.rankMachine.canBuild(p)) {
			p.sendMessage(ChatColor.RED + "You are in a Protected Habitat");
			event.setCancelled(true);
			return;
		} else {
			Block placedBlock = event.getBlock();
			switch (placedBlock.getTypeId()) {
				case 46: // TNT placement
					if (!plugin.rankMachine.isAdmin(p.getName())) {
						event.setCancelled(true);
						return;
					}
					return;
				case 54: // Chest placement
					if (plugin.chestMachine.lockIt(p, placedBlock)) {
						return;
					}
					event.setCancelled(true);
					return;
			}/*
			 * // special case for placing chest on snow if
			 * ((event.getItemInHand().getTypeId() == 54 &&
			 * event.getBlockReplacedState().getTypeId() == 78)) { Location
			 * chestLocation = placedBlock.getLocation(); double Ypos =
			 * placedBlock.getLocation().getY(); chestLocation.setY(Ypos - 1);
			 * placedBlock = placedBlock.getWorld().getBlockAt(chestLocation);
			 * if (plugin.chestMachine.lockIt(p, placedBlock)) { return; } else
			 * { event.setCancelled(true); } }
			 */
		}
	}
}
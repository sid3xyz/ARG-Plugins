package biz.argirc.Magick.ARGAntiPirate;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class ARGAntiPirateBlockListener extends BlockListener {
	private final ARGAntiPirate	plugin;

	public ARGAntiPirateBlockListener(final ARGAntiPirate plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Random rand = new Random();
		ItemStack apple = new ItemStack(Material.APPLE, 1);
		if (rand.nextInt(100) <= 2) {
			event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), apple);
			return;
		}

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
		if (plugin.inSpawn(p.getLocation()) && myRank < 4) {
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
		// event.setCancelled(true);
		// return;

		if (plugin.isWorldProtected()) {
			event.setCancelled(true);
			return;
		}
		IgniteCause cause = event.getCause();
		if (cause == IgniteCause.SPREAD) {

			event.setCancelled(true);
			return;
		}

		if (cause != IgniteCause.FLINT_AND_STEEL) {

			event.setCancelled(true);
			return;
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
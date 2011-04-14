package biz.argirc.Minecraft.listeners;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import biz.argirc.Minecraft.MagickMod;

public class WorldProtectListener extends BlockListener {
	private final MagickMod	plugin;

	public WorldProtectListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if (!plugin.rankFunctions.canBuild(event.getPlayer().getName())) {
			event.setCancelled(true);
			return;
		}
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!plugin.rankFunctions.canBuild(event.getPlayer().getName())) {
			event.setCancelled(true);
			return;
		}
	}

	@Override
	public void onBlockCanBuild(BlockCanBuildEvent event) {
		// no player object passed here, but i wonder what i could do here
	}

	@Override
	public void onBlockBurn(BlockBurnEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (event.getBlock().getType() == Material.LEAVES) {
			event.setCancelled(true);

			return;
		}

	}

	@Override
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (event.isCancelled()) {
			return;
		}
		// event.setCancelled(true);
		// return;

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

}

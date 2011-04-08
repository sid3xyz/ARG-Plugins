package biz.argirc.Minecraft.listeners;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
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
}

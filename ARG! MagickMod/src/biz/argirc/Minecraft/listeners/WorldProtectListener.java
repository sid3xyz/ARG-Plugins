package biz.argirc.Minecraft.listeners;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

import biz.argirc.Minecraft.MagickMod;

public class WorldProtectListener extends BlockListener {
	private final MagickMod	plugin;

	public WorldProtectListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {

	}
}
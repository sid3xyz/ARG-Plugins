package biz.argirc.Minecraft.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import biz.argirc.Minecraft.MagickMod;

public class ChestInteractListener extends PlayerListener {
	private final MagickMod	plugin;

	public ChestInteractListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		switch (event.getAction()) {
			case RIGHT_CLICK_BLOCK:
				Block myBlock = event.getClickedBlock();
				if (myBlock.getTypeId() == 54) {
					Player player = event.getPlayer();
					if (plugin.chestFunctions.doesUserOwnChest(player.getName(), event.getClickedBlock().getLocation())) {
						player.sendMessage("-Access Granted-");
						return;
					} else {
						player.sendMessage("-Access Denied-");
						event.setCancelled(true);
						return;
					}
				}
			default:
				return;
		}
	}

}

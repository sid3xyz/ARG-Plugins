package biz.argirc.Minecraft.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import biz.argirc.Minecraft.MagickMod;

public class FenceListener extends PlayerListener {
	private final MagickMod	plugin;

	public FenceListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {

		switch (event.getAction()) {
			case RIGHT_CLICK_BLOCK:
				Player player = event.getPlayer();

				if ((event.getClickedBlock().getType() != Material.CHEST && event.getClickedBlock().getType() != Material.WORKBENCH && event.getClickedBlock().getType() != Material.FURNACE && event.getClickedBlock().getType() != Material.DISPENSER && event.getClickedBlock().getType() != Material.STONE_BUTTON && event.getClickedBlock().getType() != Material.LEVER && event.getClickedBlock().getType() != Material.WOODEN_DOOR && event.getClickedBlock().getType() != Material.IRON_DOOR_BLOCK && event.getClickedBlock().getType() != Material.FIRE && event.getClickedBlock().getType() != Material.CAKE_BLOCK)) {
					if (player.getItemInHand().getType() == Material.FENCE) {
						Block b = event.getClickedBlock().getFace(event.getBlockFace(), 1);
						if (b.getTypeId() == 0) {

							BlockState oldState = b.getState();
							b.setType(player.getItemInHand().getType());
							BlockPlaceEvent placeEvent = new BlockPlaceEvent(b, oldState, event.getClickedBlock(), player.getItemInHand(), event.getPlayer(), true);
							plugin.getServer().getPluginManager().callEvent(placeEvent);

							if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
								b.setType(oldState.getType());
								b.setData(oldState.getData().getData());
							} else {
								int am = player.getItemInHand().getAmount();
								if (am > 1)
									player.getItemInHand().setAmount(am - 1);
								else
									event.getPlayer().getInventory().remove(player.getItemInHand());
							}
						}
					}
				}
		}
	}
}

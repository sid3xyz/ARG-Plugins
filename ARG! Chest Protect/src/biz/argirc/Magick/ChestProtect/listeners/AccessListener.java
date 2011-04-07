package biz.argirc.Magick.ChestProtect.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import biz.argirc.Magick.ChestProtect.ChestProtect;

public class AccessListener extends PlayerListener {
	private final ChestProtect	plugin;

	public AccessListener(ChestProtect plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {

		Block myBlock = event.getClickedBlock();

		if (myBlock.getTypeId() == 54) {
			Player player = event.getPlayer();
			if (plugin.doesUserOwnChest(player.getName(), event.getClickedBlock().getLocation())) {
				player.sendMessage("-Access Granted-");
				return;
			} else {
				player.sendMessage("-Access Denied-");
				event.setCancelled(true);
				return;
			}
		}
	}

}

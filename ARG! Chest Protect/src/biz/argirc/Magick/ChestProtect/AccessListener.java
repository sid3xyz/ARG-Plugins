package biz.argirc.Magick.ChestProtect;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

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
			if (!doesUserOwnChest(player.getName(), event.getClickedBlock().getLocation())) {
				player.sendMessage("-Access Denied-");
				event.setCancelled(true);
				return;
			} else {
				player.sendMessage("-Access Granted-");
				return;
			}
		}
	}

	public boolean doesUserOwnChest(String userstring, Location chestLocation) {

		List<ChestData> myChests = plugin.getDatabase().find(ChestData.class).where().ieq("playerName", userstring).findList();
		for (ChestData chest : myChests) {
			if (chest.getLocation().equals(chestLocation)) {
				return true;
			}
		}

		return false;
	}
}

package biz.argirc.Magick.ARGAntiPirate;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ARGAntiPiratePlayerListener extends PlayerListener {
	private final ARGAntiPirate	plugin;

	public ARGAntiPiratePlayerListener(final ARGAntiPirate plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerChat(PlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();

		int myRank = plugin.rankMachine.getRank(player);
		if (player.getName().equalsIgnoreCase("skwerl12")) {
			player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
			return;
		} else {
			switch (myRank) {
				case 0:
					player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
					return;
				case 1:
					player.setDisplayName(ChatColor.GRAY + player.getName() + ChatColor.WHITE);
					return;
				case 2:
					player.setDisplayName(ChatColor.DARK_GRAY + player.getName() + ChatColor.WHITE);
					return;
				case 3:
					player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
					return;
				case 4:
					player.setDisplayName(ChatColor.GREEN + player.getName() + ChatColor.WHITE);
					return;
				case 5:
					player.setDisplayName(ChatColor.GOLD + player.getName() + ChatColor.WHITE);
					return;
			}
			player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
		}
	}

	@Override
	public void onPlayerLogin(PlayerLoginEvent event) {

		Player player = event.getPlayer();
		int myRank = plugin.rankMachine.getRank(player);
		if (myRank == -1) {
			String[] args = new String[2];
			args[0] = player.getName();
			args[1] = "" + 0;
			// add player as rank 0
			plugin.rankMachine.setRank(args);
			plugin.rankMachine.Save();
		}

	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		Block myBlock = event.getClickedBlock();
		if (myBlock.getTypeId() == 54 && plugin.chestMachine.openChest(player, myBlock) == true) {
			player.sendMessage(ChatColor.GREEN + "Access Granted");
			return;
		} else if (event.getClickedBlock().getTypeId() == 54) {
			player.sendMessage(ChatColor.RED + "You do not have permission to access this chest");
			event.setCancelled(true);
			return;
		}

		// fence code
		if ((event.getClickedBlock().getType() != Material.CHEST && event.getClickedBlock().getType() != Material.WORKBENCH && event.getClickedBlock().getType() != Material.FURNACE && event.getClickedBlock().getType() != Material.DISPENSER && event.getClickedBlock().getType() != Material.STONE_BUTTON && event.getClickedBlock().getType() != Material.LEVER && event.getClickedBlock().getType() != Material.WOODEN_DOOR && event.getClickedBlock().getType() != Material.IRON_DOOR_BLOCK && event.getClickedBlock().getType() != Material.FIRE && event.getClickedBlock().getType() != Material.CAKE_BLOCK)) {
			if (!event.hasItem()) {
				return;
			}
			if (event.getItem().getType() == Material.FENCE) {
				Block b = event.getClickedBlock().getFace(event.getBlockFace(), 1);
				if (b.getTypeId() == 0) {

					BlockState oldState = b.getState();
					b.setType(event.getItem().getType());

					BlockPlaceEvent placeEvent = new BlockPlaceEvent(b, oldState, event.getClickedBlock(), event.getItem(), event.getPlayer(), true);
					plugin.getServer().getPluginManager().callEvent(placeEvent);

					if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
						b.setType(oldState.getType());
						b.setData(oldState.getData().getData());
					} else {
						int amnt = event.getItem().getAmount();
						if (amnt > 1)
							event.getItem().setAmount(amnt - 1);
						else
							event.getPlayer().getInventory().remove(event.getItem());
					}
				}
			}
		}
	}
}

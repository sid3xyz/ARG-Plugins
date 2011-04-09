package biz.argirc.Minecraft.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import biz.argirc.Minecraft.GeneralSettings;
import biz.argirc.Minecraft.MagickMod;

public class PlayerDeathListener extends EntityListener {
	private final MagickMod	plugin;

	public PlayerDeathListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			doPlayerDeath(player);
		}
	}

	public void doPlayerDeath(Player player) {
		if (plugin.bankFunctions.hasAccount(player.getName()) == true) {
			int newbalance = plugin.bankFunctions.getBalance(player.getName()) - 75;
			if (newbalance < 0) {
				newbalance = 0;
			}
			GeneralSettings.getDeathPenalty();
			plugin.bankFunctions.setBalance(player.getName(), newbalance);

			player.sendMessage("You Died " + GeneralSettings.deathpenalty + " " + GeneralSettings.credit);
			player.sendMessage("Total: " + newbalance + " " + GeneralSettings.credit);
		}
		Block signBlock = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
		if (signBlock.getType() == Material.RAILS || signBlock.getType() == Material.STEP) {
			return;
		}
		signBlock.setType(Material.SIGN_POST);
		BlockState state = signBlock.getState();
		if (state instanceof Sign) {
			Sign sign = (Sign) state;
			sign.setLine(0, "[RIP]");
			sign.setLine(1, player.getName());
		}
	}
}

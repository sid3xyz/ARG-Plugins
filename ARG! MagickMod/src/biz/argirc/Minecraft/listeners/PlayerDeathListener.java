package biz.argirc.Minecraft.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import biz.argirc.Minecraft.LoadSettings;
import biz.argirc.Minecraft.MagickMod;
import biz.argirc.Minecraft.database.Accounting;

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
		boolean hasaccount = Accounting.containskey(player, plugin.Accounts);
		if (hasaccount == true) {
			int newbalance = Accounting.getBalance(player, plugin.Accounts) - 75;
			if (newbalance < 0) {
				newbalance = 0;
			}
			LoadSettings.getDeathPenalty();
			Accounting.write(player, newbalance, plugin.Accounts);
			player.sendMessage("You Died " + LoadSettings.deathpenalty + " " + LoadSettings.credit);
			player.sendMessage("Total: " + newbalance + " " + LoadSettings.credit);
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

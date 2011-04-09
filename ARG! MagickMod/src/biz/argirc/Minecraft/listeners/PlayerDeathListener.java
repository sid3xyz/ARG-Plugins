package biz.argirc.Minecraft.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import biz.argirc.Minecraft.EconomySettings;
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
		boolean hasaccount = plugin.MagickBank.containsKey(player.getName());
		if (hasaccount == true) {
			int newbalance = Integer.parseInt(plugin.MagickBank.getProperty(player.getName())) - 75;
			if (newbalance < 0) {
				newbalance = 0;
			}
			EconomySettings.getDeathPenalty();
			plugin.MagickBank.setProperty(player.getName(), Integer.toString(newbalance));
			player.sendMessage("You Died " + EconomySettings.deathpenalty + " " + EconomySettings.credit);
			player.sendMessage("Total: " + newbalance + " " + EconomySettings.credit);
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

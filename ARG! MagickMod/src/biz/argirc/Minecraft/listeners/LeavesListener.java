package biz.argirc.Minecraft.listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class LeavesListener extends BlockListener {

	@Override
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Random rand = new Random();
		ItemStack apple = new ItemStack(Material.APPLE, 1);
		if (rand.nextInt(100) <= 1) {
			event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), apple);
			return;
		}

	}

}

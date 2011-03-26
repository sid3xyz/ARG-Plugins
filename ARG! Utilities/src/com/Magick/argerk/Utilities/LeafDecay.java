package com.Magick.argerk.Utilities;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class LeafDecay extends BlockListener {
	private final ARGUtilities plugin;

	public LeafDecay(final ARGUtilities plugin) {
		this.plugin = plugin;
	}

	 public void onLeavesDecay(LeavesDecayEvent event) {
		World myWorld = plugin.getServer().getWorld("world");
		Random rand = new Random();
		int selector = rand.nextInt(100);
		 if(selector <= 5 ){
			 ItemStack myApple = new ItemStack(Material.APPLE, 1);
			 myWorld.dropItemNaturally(event.getBlock().getLocation(),myApple);
			 
		 }
	    }
 
}
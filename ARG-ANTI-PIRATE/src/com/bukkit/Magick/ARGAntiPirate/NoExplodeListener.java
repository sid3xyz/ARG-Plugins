package com.bukkit.Magick.ARGAntiPirate;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ExplosionPrimedEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NoExplodeListener extends EntityListener {
	public static ARGAntiPirate	plugin;

	public NoExplodeListener(ARGAntiPirate instance) {
		plugin = instance;
	}

	public void onExplosionPrimed(ExplosionPrimedEvent event) {
		if (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
			event.setCancelled(true);
		} else if (event.getEntity().getClass().getName().contains("CraftCreeper")) {
			Creeper creeper = (Creeper) event.getEntity();
			
			
			Location Spawn = creeper.getServer().getWorld("world").getSpawnLocation();
			double myx = Math.abs(creeper.getLocation().getX());
			double myz = Math.abs(creeper.getLocation().getZ());

			double SpawnX = Math.abs(Spawn.getX());
			double SpawnZ = Math.abs(Spawn.getZ());

			double protectedX = SpawnX + 100; // actual size is 2x
			double protectedZ = SpawnZ + 100;

			if (myx <= protectedX && myz <= protectedZ) {
				
				creeper.setHealth(0);
				event.setCancelled(true);
				return;
			}
			creeper.shootArrow();
			return;
		}
		/*
		 * else if
		 * (event.getEntity().getClass().getName().contains("CraftFireball")) {
		 * event.setCancelled(true); }
		 */
	}

	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
			event.setCancelled(true);
		} else if (event.getEntity().getClass().getName().contains("CraftCreeper")) {
			Creeper creeper = (Creeper) event.getEntity();

			Location Spawn = creeper.getServer().getWorld("world").getSpawnLocation();
			double myx = Math.abs(creeper.getLocation().getX());
			double myz = Math.abs(creeper.getLocation().getZ());

			double SpawnX = Math.abs(Spawn.getX());
			double SpawnZ = Math.abs(Spawn.getZ());

			double protectedX = SpawnX + 100; // actual size is 2x
			double protectedZ = SpawnZ + 100;

			if (myx <= protectedX && myz <= protectedZ) {
				
				creeper.throwEgg();
				creeper.throwEgg();
				creeper.throwEgg();
				creeper.throwEgg();
				creeper.setHealth(0);
				event.setCancelled(true);
			}
			return;
		}
		/*
		 * else if (
		 * event.getEntity().getClass().getName().contains("CraftFireball")) {
		 * event.setCancelled(true); }
		 */
	}
}

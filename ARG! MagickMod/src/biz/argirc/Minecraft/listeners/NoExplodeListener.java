package biz.argirc.Minecraft.listeners;

import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import biz.argirc.Minecraft.MagickMod;

public class NoExplodeListener extends EntityListener {
	public final MagickMod	plugin;

	public NoExplodeListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onEntityExplode(EntityExplodeEvent event) {
		/*
		 * if (event.isCancelled()) { return; } if
		 * (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
		 * event.setCancelled(true); } else if
		 * (event.getEntity().getClass().getName().contains("CraftCreeper")) {
		 * Creeper creeper = (Creeper) event.getEntity(); Location Spawn =
		 * plugin.getServer().getWorld("world").getSpawnLocation(); double x =
		 * Spawn.getX() - creeper.getLocation().getX(); double y = Spawn.getY()
		 * - creeper.getLocation().getY(); double z = Spawn.getZ() -
		 * creeper.getLocation().getZ(); double distance = x * x + y * y + z *
		 * z;
		 * 
		 * if (distance <= 100 * 100) {
		 * 
		 * creeper.throwEgg(); creeper.throwEgg(); creeper.throwEgg();
		 * creeper.throwEgg(); creeper.setHealth(0); event.setCancelled(true); }
		 * return; } /* else if (
		 * event.getEntity().getClass().getName().contains("CraftFireball")) {
		 * event.setCancelled(true); }
		 */

	}

	@Override
	public void onExplosionPrime(ExplosionPrimeEvent event) {

		/*
		 * if (event.isCancelled()) { return; }if
		 * (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
		 * event.setCancelled(true); } else if
		 * (event.getEntity().getClass().getName().contains("CraftCreeper")) {
		 * Creeper creeper = (Creeper) event.getEntity(); Location Spawn =
		 * plugin.getServer().getWorld("world").getSpawnLocation(); double x =
		 * Spawn.getX() - creeper.getLocation().getX(); double y = Spawn.getY()
		 * - creeper.getLocation().getY(); double z = Spawn.getZ() -
		 * creeper.getLocation().getZ(); double distance = x * x + y * y + z *
		 * z;
		 * 
		 * if (distance <= 100 * 100) {
		 * 
		 * creeper.throwEgg(); creeper.throwEgg(); creeper.throwEgg();
		 * creeper.throwEgg(); creeper.setHealth(0); event.setCancelled(true); }
		 * return; } /* else if
		 * (event.getEntity().getClass().getName().contains("CraftFireball")) {
		 * event.setCancelled(true); }
		 */
	}
}

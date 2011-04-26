package biz.argirc.Minecraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class NoExplodeListener extends EntityListener {

	@Override
	public void onEntityExplode(EntityExplodeEvent event) {

		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
			event.setCancelled(true);
		} else if (event.getEntity().getClass().getName().contains("CraftCreeper")) {
			Creeper creeper = (Creeper) event.getEntity();
			Location Spawn = Bukkit.getServer().getWorld("world").getSpawnLocation();
			double x = Spawn.getX() - creeper.getLocation().getX();
			double y = Spawn.getY() - creeper.getLocation().getY();
			double z = Spawn.getZ() - creeper.getLocation().getZ();
			double distance = x * x + y * y + z * z;

			if (distance <= 500 * 500) {
				creeper.setHealth(0);
				event.setCancelled(true);
			}
			return;
		}

	}

	@Override
	public void onExplosionPrime(ExplosionPrimeEvent event) {

		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
			event.setCancelled(true);
		} else if (event.getEntity().getClass().getName().contains("CraftCreeper")) {
			Creeper creeper = (Creeper) event.getEntity();
			Location Spawn = Bukkit.getServer().getWorld("world").getSpawnLocation();
			double x = Spawn.getX() - creeper.getLocation().getX();
			double y = Spawn.getY() - creeper.getLocation().getY();
			double z = Spawn.getZ() - creeper.getLocation().getZ();
			double distance = x * x + y * y + z * z;

			if (distance <= 500 * 500) {

				creeper.throwEgg();
				creeper.throwEgg();
				creeper.throwEgg();
				creeper.throwEgg();
				creeper.setHealth(0);
				event.setCancelled(true);
			}
			return;
		}

	}

}

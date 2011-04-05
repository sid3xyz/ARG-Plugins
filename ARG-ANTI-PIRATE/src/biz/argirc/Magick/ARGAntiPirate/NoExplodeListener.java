package biz.argirc.Magick.ARGAntiPirate;

import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class NoExplodeListener extends EntityListener {
	public static ARGAntiPirate	plugin;

	public NoExplodeListener(ARGAntiPirate instance) {
		plugin = instance;
	}

	@Override
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
			event.setCancelled(true);
		} else if (event.getEntity().getClass().getName().contains("CraftCreeper")) {
			Creeper creeper = (Creeper) event.getEntity();
			double x = plugin.Spawn.getX() - creeper.getLocation().getX();
			double y = plugin.Spawn.getY() - creeper.getLocation().getY();
			double z = plugin.Spawn.getZ() - creeper.getLocation().getZ();
			double distance = x * x + y * y + z * z;

			if (distance <= 100 * 100) {

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

	@Override
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity().getClass().getName().contains("CraftTNTPrimed")) {
			event.setCancelled(true);
		} else if (event.getEntity().getClass().getName().contains("CraftCreeper")) {
			Creeper creeper = (Creeper) event.getEntity();

			double x = plugin.Spawn.getX() - creeper.getLocation().getX();
			double y = plugin.Spawn.getY() - creeper.getLocation().getY();
			double z = plugin.Spawn.getZ() - creeper.getLocation().getZ();
			double distance = x * x + y * y + z * z;

			if (distance <= 100 * 100) {

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
		 * else if
		 * (event.getEntity().getClass().getName().contains("CraftFireball")) {
		 * event.setCancelled(true); }
		 */
	}
}

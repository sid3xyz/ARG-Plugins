package com.Magick.argerk.AntiPirate;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

public class ArgEntityListener extends EntityListener {
	private final ARGAntiPirate	plugin;

	public ArgEntityListener(final ARGAntiPirate plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getCreatureType() == CreatureType.PIG) {
			Entity myEntity = event.getEntity();
			myEntity.getWorld().spawnCreature(myEntity.getLocation(), CreatureType.valueOf("PIG_ZOMBIE"));
			return;
		}

		/*
		 * if (plugin.inSpawn(event.getEntity().getLocation())) {
		 * event.setCancelled(true); return; }
		 */
	}
}

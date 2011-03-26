package com.bukkit.Magick.ARGUtilities;

import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.PluginManager;

public class SpawnManager extends EntityListener {
	private final ARGUtilities	plugin;

	public SpawnManager(final ARGUtilities plugin) {
		this.plugin = plugin;
	}
	
	 public void onCreatureSpawn(CreatureSpawnEvent event) {
	   if(event.getCreatureType().equals(CreatureType.SPIDER)){
		//   PluginManager pm = getServer().getPluginManager();
		//   pm
		   
	   }
	 }
}
package com.bukkit.Magick.ARGUtilities;


import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * ARGEcon for Bukkit
 * 
 * @author Magick
 */
public class ARGUtilities extends JavaPlugin {
	private final Utilities					myUtilities	= new Utilities(this);
	private AutoSaveThread					saveThread	= null;
	
	
	
	public void onEnable() {
		// Register events
		//PluginManager pm = getServer().getPluginManager();

		//Start the auto save thread
		startSaveThread();
		System.out.println("ARG! Utilities is enabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		String commandName = command.getName().toLowerCase();
		if (commandName.equals("setcompass")) {
			return myUtilities.setCompass(sender, args);
		} else if (commandName.equals("who") | commandName.equals("list")) {
			return myUtilities.showPlayerList(sender, args);
		} else if (commandName.equals("spawnmob")) {
			return myUtilities.spawnMob(sender, args);
		} else if (commandName.equals("setspawn")) {
			return myUtilities.setSpawnLocation(sender, args);
		} else if (commandName.equalsIgnoreCase("teleport")) {
			return myUtilities.telePlayer(sender);
		}else if (commandName.equalsIgnoreCase("explode")) {
			return myUtilities.explodeMe(sender);
		}else if (commandName.equalsIgnoreCase("xyzport")) {
			return myUtilities.xyzport(sender,args);
		} else if (commandName.equalsIgnoreCase("killallmobs")){
			return myUtilities.KillMobs(sender);
		} else if (commandName.equalsIgnoreCase("killallanimals")){
			return myUtilities.Killanimals(sender);
		}
		return false;
	}
	
	public void onDisable() {
		stopSaveThread();
		System.out.println("ARG! Utilities Disabeled.");
	}
	
	public boolean startSaveThread() {
		saveThread = new AutoSaveThread(this);
		saveThread.start();
		return true;
	}

	public boolean stopSaveThread() {
		saveThread.setRun(false);
		try {
			saveThread.join(5000);
			saveThread = null;
			return true;
		} catch (InterruptedException e) {
			// log.info("Could not stop AutoSaveThread");
			return false;
		}
	}

	public int saveWorlds() {
		// Save our worlds
		int i = 0;
		List<World> worlds = this.getServer().getWorlds();
		for (World world : worlds) {
			this.getServer().broadcastMessage("Saving World and Player Data...");
			this.getServer().savePlayers();
			world.save();
			i++;
		}
		return i;
	}
	
}
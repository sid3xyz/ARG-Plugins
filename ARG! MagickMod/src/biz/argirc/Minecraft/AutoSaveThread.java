package biz.argirc.Minecraft;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;

public class AutoSaveThread implements Runnable {

	private MagickMod	plugin	= null;

	AutoSaveThread(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		System.out.println("AutoSave Started");
		saveWorlds();
		System.out.println("AutoSave Complete");
		plugin.getServer().broadcastMessage(String.format("%s%s", ChatColor.BLUE, "World and Player data saved"));
	}

	public void saveWorlds() {
		// Save our worlds
		List<World> worlds = plugin.getServer().getWorlds();
		for (World world : worlds) {
			plugin.getServer().broadcastMessage("Saving World and Player Data...");
			plugin.getServer().savePlayers();
			world.save();
		}
	}
}
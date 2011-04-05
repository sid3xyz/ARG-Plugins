package biz.argirc.Magick.ARGUtilities;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ARGUtilities extends JavaPlugin {
	private final Utilities					myUtilities	= new Utilities(this);
	private final MinecartMachine minecartMachine = new MinecartMachine();
	private AutoSaveThread					saveThread	= null;
	
public void onEnable() {

		System.out.println("Auto Save Starting...");
		startSaveThread();
		PluginManager pm = getServer().getPluginManager();
		Plugin[] pluginList = pm.getPlugins();
		for (Plugin plugin : pluginList) {
			System.out.println("Detected Plugin:");
			System.out.println(plugin.getDescription().getName());
		}
		pm.registerEvent(Event.Type.VEHICLE_MOVE, this.minecartMachine, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.VEHICLE_COLLISION_BLOCK, this.minecartMachine, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.VEHICLE_COLLISION_ENTITY, this.minecartMachine, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.VEHICLE_EXIT, this.minecartMachine, Event.Priority.Highest, this);
		
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
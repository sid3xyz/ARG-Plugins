package biz.argirc.Magick.ChestProtect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import biz.argirc.Magick.ChestProtect.commands.GetChestCountCommand;
import biz.argirc.Magick.ChestProtect.commands.UnlockChestCommand;
import biz.argirc.Magick.ChestProtect.database.ChestData;
import biz.argirc.Magick.ChestProtect.listeners.AccessListener;
import biz.argirc.Magick.ChestProtect.listeners.ChestListener;

public class ChestProtect extends JavaPlugin {
	public final ChestFunctions		chestFunctions	= new ChestFunctions();
	private final AccessListener	accessListener	= new AccessListener(chestFunctions);
	private final ChestListener		blockListener	= new ChestListener(chestFunctions);

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		// set up our database
		chestFunctions.setupDatabase();
		// register our events
		registerEvents();
		// get our commands
		getCommand("chestcount").setExecutor(new GetChestCountCommand(this));
		getCommand("unlock").setExecutor(new UnlockChestCommand(this));
		// do this stuff so people can tell that we are ready to go!
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_LOGIN, accessListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, accessListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Highest, this);
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ChestData.class);
		return list;
	}
}

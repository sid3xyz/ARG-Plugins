package biz.argirc.Minecraft;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import biz.argirc.Minecraft.commands.GetChestCountCommand;
import biz.argirc.Minecraft.commands.UnlockChestCommand;
import biz.argirc.Minecraft.database.ChestData;
import biz.argirc.Minecraft.listeners.ChestInteractListener;
import biz.argirc.Minecraft.listeners.ChestBlockListener;

public class MagickMod extends JavaPlugin {
	public final ChestFunctions		chestFunctions	= new ChestFunctions(this);
	private final ChestInteractListener	chestInteractListener	= new ChestInteractListener(this);
	private final ChestBlockListener		blockListener	= new ChestBlockListener(this);

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		// set up our database
		setupDatabase();
		// register our events
		registerEvents();
		// get our commands
		getCommand("chesthelp").setExecutor(new ChestHelpCommand());
		getCommand("chestcount").setExecutor(new GetChestCountCommand(this));
		getCommand("unlock").setExecutor(new UnlockChestCommand(this));
		// do this stuff so people can tell that we are ready to go!
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void setupDatabase() {
		try {
			getDatabase().find(ChestData.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
			installDDL();
		}
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, chestInteractListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Highest, this);
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ChestData.class);
		return list;
	}
}

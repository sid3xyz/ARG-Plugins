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
import biz.argirc.Minecraft.database.RankData;
import biz.argirc.Minecraft.listeners.ChestBlockListener;
import biz.argirc.Minecraft.listeners.ChestInteractListener;

public class MagickMod extends JavaPlugin {
	public final ChestFunctions			chestFunctions			= new ChestFunctions(this);
	private final ChestInteractListener	chestInteractListener	= new ChestInteractListener(this);
	private final ChestBlockListener	chestBlockListener		= new ChestBlockListener(this);
	private final OnJoinListener		onJoinListener			= new OnJoinListener(this);

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		setupDatabase();
		registerEvents();
		getCommand("chesthelp").setExecutor(new ChestHelpCommand());
		getCommand("chestcount").setExecutor(new GetChestCountCommand(this));
		getCommand("unlock").setExecutor(new UnlockChestCommand(this));
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void setupDatabase() {
		try {
			getDatabase().find(ChestData.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Initializing database for " + getDescription().getName() + " chest protection");
			installDDL();
		}
		try {
			getDatabase().find(RankData.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Initializing database for " + getDescription().getName() + " rank system");
			installDDL();
		}
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		// Block events
		pm.registerEvent(Event.Type.BLOCK_PLACE, chestBlockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, chestBlockListener, Priority.Highest, this);
		// Player Events
		pm.registerEvent(Event.Type.PLAYER_INTERACT, chestInteractListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, onJoinListener, Priority.Normal, this);

	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ChestData.class);
		return list;
	}
}

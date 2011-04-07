package biz.argirc.Magick.ChestProtect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestProtect extends JavaPlugin {

	private final AccessListener	playerListener	= new AccessListener(this);
	private final ChestListener		blockListener	= new ChestListener(this);

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_BURN, blockListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.LEAVES_DECAY, blockListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Event.Priority.Highest, this);
		// pm.registerEvent(Event.Type.EXPLOSION_PRIME, explodeListener,
		// Event.Priority.Highest, this);
		// pm.registerEvent(Event.Type.ENTITY_EXPLODE, explodeListener,
		// Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Highest, this);
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
		// set up our database
		setupDatabase();

		// get our commands
		getCommand("chestcount").setExecutor(new GetChestCountCommand(this));
		getCommand("lock").setExecutor(new LockChestCommand(this));
		getCommand("unlock").setExecutor(new UnlockChestCommand(this));
	}

	private void setupDatabase() {
		try {
			getDatabase().find(ChestData.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
			installDDL();
		}
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ChestData.class);
		return list;
	}

}

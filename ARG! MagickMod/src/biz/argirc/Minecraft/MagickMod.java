package biz.argirc.Minecraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import biz.argirc.Minecraft.commands.BankCommand;
import biz.argirc.Minecraft.commands.BuyCommand;
import biz.argirc.Minecraft.commands.ChallangeCommand;
import biz.argirc.Minecraft.commands.ChestHelpCommand;
import biz.argirc.Minecraft.commands.DieCommand;
import biz.argirc.Minecraft.commands.GetChestCountCommand;
import biz.argirc.Minecraft.commands.GiveTrackCommand;
import biz.argirc.Minecraft.commands.KillFarmAnimalsCommand;
import biz.argirc.Minecraft.commands.KillHostileMobsCommand;
import biz.argirc.Minecraft.commands.MakeCartTunnelCommand;
import biz.argirc.Minecraft.commands.RegenChunkCommand;
import biz.argirc.Minecraft.commands.SetCompassCommand;
import biz.argirc.Minecraft.commands.SetRankCommand;
import biz.argirc.Minecraft.commands.SetSpawnLocationCommand;
import biz.argirc.Minecraft.commands.SpawnMobCommand;
import biz.argirc.Minecraft.commands.StoreCommand;
import biz.argirc.Minecraft.commands.TeleportCommand;
import biz.argirc.Minecraft.commands.UnlockChestCommand;
import biz.argirc.Minecraft.commands.WallChunkCommand;
import biz.argirc.Minecraft.commands.WhoCommand;
import biz.argirc.Minecraft.database.BankData;
import biz.argirc.Minecraft.database.ChestData;
import biz.argirc.Minecraft.database.RankData;
import biz.argirc.Minecraft.listeners.ChestBlockListener;
import biz.argirc.Minecraft.listeners.ChestInteractListener;
import biz.argirc.Minecraft.listeners.LeavesListener;
import biz.argirc.Minecraft.listeners.MinecartListener;
import biz.argirc.Minecraft.listeners.MobDeathListener;
import biz.argirc.Minecraft.listeners.NoExplodeListener;
import biz.argirc.Minecraft.listeners.OnJoinListener;
import biz.argirc.Minecraft.listeners.PlayerDeathListener;
import biz.argirc.Minecraft.listeners.WorldProtectListener;

public class MagickMod extends JavaPlugin {
	public static HashMap<String, List<String>>	deathevents				= new HashMap<String, List<String>>();
	public final ChestFunctions					chestFunctions			= new ChestFunctions(this);
	public final RankFunctions					rankFunctions			= new RankFunctions(this);
	public final BankFunctions					bankFunctions			= new BankFunctions(this);
	public final ArenaFunctions					arenaFunctions			= new ArenaFunctions(this);
	private final NoExplodeListener				noExplodeListener		= new NoExplodeListener(this);
	private final ChestInteractListener			chestInteractListener	= new ChestInteractListener(this);
	private final ChestBlockListener			chestBlockListener		= new ChestBlockListener(this);
	private final OnJoinListener				onJoinListener			= new OnJoinListener(this);
	private final WorldProtectListener			worldProtectListener	= new WorldProtectListener(this);
	public final MobDeathListener				mobDeathListener		= new MobDeathListener(this);
	public final PlayerDeathListener			playerDeathListener		= new PlayerDeathListener(this);
	public final LeavesListener					leavesListener			= new LeavesListener();
	private final MinecartListener				minecartListener		= new MinecartListener();
	public static String						maindirectory			= "plugins/ARG MagickMod/";
	public final PluginSettings					pluginSettings			= new PluginSettings(maindirectory + "MagickMod.conf");
	public final ShopFunctions					shopFunctions			= new ShopFunctions(maindirectory + "ItemShop.conf");
	public File									shopFile				= new File(maindirectory + "ItemShop.conf");
	public File									PluginFile				= new File(maindirectory + "MagickMod.conf");

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {

		@SuppressWarnings("unused")
		int saveTaskID = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AutoSaveThread(this), 120 * 21L, 900 * 21L);
		System.out.println("Auto Save Thread Starting...");

		setupDatabase();
		// rankFunctions.convertDB();
		// chestFunctions.convertDB();
		// chestFunctions.convertDB();
		registerEvents();
		getCommand("makecarttunnel").setExecutor(new MakeCartTunnelCommand());
		getCommand("givetrack").setExecutor(new GiveTrackCommand());
		getCommand("regenChunk").setExecutor(new RegenChunkCommand(this));
		getCommand("wallchunk").setExecutor(new WallChunkCommand());
		getCommand("store").setExecutor(new StoreCommand(shopFunctions));
		getCommand("buy").setExecutor(new BuyCommand(bankFunctions, shopFunctions));
		getCommand("challenge").setExecutor(new ChallangeCommand());
		getCommand("die").setExecutor(new DieCommand(this));
		getCommand("bank").setExecutor(new BankCommand(this));
		getCommand("setcompass").setExecutor(new SetCompassCommand());
		getCommand("setserverspawn").setExecutor(new SetSpawnLocationCommand());
		getCommand("killfriendly").setExecutor(new KillFarmAnimalsCommand());
		getCommand("killhostile").setExecutor(new KillHostileMobsCommand());
		getCommand("spawnmob").setExecutor(new SpawnMobCommand());
		getCommand("who").setExecutor(new WhoCommand());
		getCommand("teleport").setExecutor(new TeleportCommand());
		getCommand("setrank").setExecutor(new SetRankCommand(rankFunctions));
		getCommand("chesthelp").setExecutor(new ChestHelpCommand());
		getCommand("chestcount").setExecutor(new GetChestCountCommand(chestFunctions));
		getCommand("unlock").setExecutor(new UnlockChestCommand(chestFunctions));

		PluginDescriptionFile pdfFile = this.getDescription();
		deathevents.put("player", getConfiguration().getStringList("player", null));
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

		if (!PluginFile.exists()) {
			try {
				new File(maindirectory).mkdir();
				PluginFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!shopFile.exists()) {
			try {
				new File(maindirectory).mkdir();
				shopFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setupDatabase() {
		// List<Class<?>> dbList = getDatabaseClasses();

		/*
		 * for (Class<?> dbclass : dbList) { String name = dbclass.getName(); //
		 * System.out.println("found database " + name); // int last =
		 * name.lastIndexOf("."); // String mydbClass = name.substring(last + 1)
		 * + ".class"; // System.out.println("class name: " + mydbClass);
		 * 
		 * try { getDatabase().find(Class.forName(name)).findRowCount();
		 * System.out.println(Class.forName(name).getName() + " Loaded"); }
		 * catch (PersistenceException ex) {
		 * System.out.println("Initializing database for " + name); //
		 * installDDL(); } catch (ClassNotFoundException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * }
		 */

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
		try {
			getDatabase().find(BankData.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Initializing database for " + getDescription().getName() + "Banking system");
			installDDL();
		}
		/*
		 * try { getDatabase().find(InventoryData.class).findRowCount(); } catch
		 * (PersistenceException ex) {
		 * System.out.println("Initializing database for " +
		 * getDescription().getName() + "Arena"); installDDL(); }
		 */
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		// Block events
		pm.registerEvent(Event.Type.ENTITY_EXPLODE, noExplodeListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.EXPLOSION_PRIME, noExplodeListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.LEAVES_DECAY, leavesListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, playerDeathListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, mobDeathListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_CANBUILD, worldProtectListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, chestBlockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, chestBlockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, worldProtectListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, worldProtectListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.BLOCK_IGNITE, worldProtectListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.BLOCK_BURN, worldProtectListener, Priority.Lowest, this);
		// Player Events
		pm.registerEvent(Event.Type.ENTITY_DEATH, playerDeathListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, mobDeathListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, onJoinListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, chestInteractListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.VEHICLE_MOVE, this.minecartListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.VEHICLE_COLLISION_BLOCK, this.minecartListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.VEHICLE_COLLISION_ENTITY, this.minecartListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.VEHICLE_EXIT, this.minecartListener, Event.Priority.Highest, this);

	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ChestData.class);
		list.add(RankData.class);
		list.add(BankData.class);
		// list.add(InventoryData.class);
		return list;
	}

}

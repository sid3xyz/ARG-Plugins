// giant monsters
package com.bukkit.Magick.ARGAntiPirate;

import java.io.File;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ARGAntiPirate extends JavaPlugin {

	private final ARGAntiPiratePlayerListener	playerListener	= new ARGAntiPiratePlayerListener(this);
	private final ARGAntiPirateBlockListener	blockListener	= new ARGAntiPirateBlockListener(this);
	private final NoExplodeListener				explodeListener	= new NoExplodeListener(this);
	public static final String					maindirectory	= "ARGPlugins/";
	public static final File					ChestData		= new File(maindirectory + "Chest.dat");
	public static final File					playerRanksFile	= new File(maindirectory + "playerranks.data");
	// static final File ChestLogger = new File(maindirectory + "Chest.log");
	// public static Logger suspiciousLog;
	public Properties							PlayerRanks		= new Properties();
	public Properties							ChestDatabase	= new Properties();
	public final ARG_Rank						rankMachine		= new ARG_Rank(this, PlayerRanks);
	public final ARG_ThiefProtect				chestMachine	= new ARG_ThiefProtect(this, ChestDatabase);
	public boolean								globalProtect	= false;
	public ARGAntiPirate						plugin;
	public Location								Spawn			= null;

	public boolean isWorldProtected() {
		return globalProtect;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		String[] trimmedArgs = args;
		String commandName = command.getName().toLowerCase();
		Player p = (Player) sender;
		int rank = rankMachine.getRank(p);
		if (rank == 5) {
			// admin commands
			if (commandName.equals("adduser")) {
				if (rankMachine.setRank(args) > -1) {
					sender.sendMessage(trimmedArgs[0] + " has been given rank " + args[1]);
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "Player already exists");
					return true;
				}
			} else if (commandName.equals("argreload") && rank > 4) {
				this.getServer().broadcastMessage("Reloading chest and rank data");

				// chestMachine.Load();
				rankMachine.Load();
				return true;
			} else if (commandName.equals("argsave") && rank > 4) {
				this.getServer().broadcastMessage("Saving chest and rank data");
				// chestMachine.Save();
				rankMachine.Save();
				return true;

			} else if (commandName.equals("removeplayer") && rank > 4) {
				rankMachine.removeUser(trimmedArgs[0].toString());
				return true;
			} else if (commandName.equals("globalprotect") && rank > 4) {

				if (args[0].equalsIgnoreCase("on")) {
					globalProtect = true;
					this.getServer().broadcastMessage("Global world protect on");
					return true;
				} else if (args[0].equalsIgnoreCase("off")) {
					globalProtect = false;
					this.getServer().broadcastMessage("Global world protect off");
					return true;
				}

				return true;
			}
		}
		// user commands
		if (rank >= 1) {
			if (commandName.equals("unlock")) {
				return chestMachine.unlockIt(p);
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have permission to do that.");
			return true;
		}
		return false;
	}

	@Override
	public void onDisable() {
		// chestMachine.Save();

		rankMachine.Save();
		System.out.println("ARGAntiPirate Disabeled.");
	}

	@Override
	public void onEnable() {
		Spawn = this.getServer().getWorld("world").getSpawnLocation();
		/*
		 * try { boolean append = true; FileHandler fh = new
		 * FileHandler(maindirectory + "suspicious.Log", append);
		 * fh.setFormatter(new Formatter() { public String format(LogRecord rec)
		 * { StringBuffer buf = new StringBuffer(1000); buf.append(new
		 * java.util.Date()); buf.append(' '); buf.append(rec.getLevel());
		 * buf.append(' '); buf.append(formatMessage(rec)); buf.append('\n');
		 * return buf.toString(); } }); //suspiciousLog =
		 * Logger.getLogger("suspiciousLogger"); //suspiciousLog.addHandler(fh);
		 * //suspiciousLog.info("Logging Started...");
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_BURN, this.blockListener, Event.Priority.Highest, this);
		// pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKE, this.blockListener,
		// Event.Priority.Highest, this);

		pm.registerEvent(Event.Type.BLOCK_IGNITE, this.blockListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.EXPLOSION_PRIME, explodeListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.ENTITY_EXPLODE, explodeListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, blockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Highest, this);
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

	}

	public void setWorldProtect(boolean state) {
		globalProtect = state;

	}

	boolean inSpawn(Location myLocation) {

		double x = Spawn.getX() - myLocation.getX();
		double y = Spawn.getY() - myLocation.getY();
		double z = Spawn.getZ() - myLocation.getZ();
		double distance = x * x + y * y + z * z;

		return (distance <= 100 * 100);

	}

	@SuppressWarnings("unused")
	private boolean isPlayerWithinRadius(Player player, Location loc, double radius) {
		double x = loc.getX() - player.getLocation().getX();
		double y = loc.getY() - player.getLocation().getY();
		double z = loc.getZ() - player.getLocation().getZ();
		double distance = x * x + y * y + z * z;

		return (distance <= radius * radius);
	}
}

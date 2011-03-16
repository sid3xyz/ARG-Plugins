package com.bukkit.Magick.ARGEconomy;

//import java.io.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ARGEcon for Bukkit
 * 
 * @author Magick
 */
public class ARGEcon extends JavaPlugin {
	// private final ARGEconPlayerListener playerListener = new
	// ARGEconPlayerListener(this);
	private final ItemStore						myStore			= new ItemStore();
	private final DeathListener					deathListener	= new DeathListener(this);
	public static HashMap<String, List<String>>	deathevents		= new HashMap<String, List<String>>();
	private final ARGEconPlayerListener			playerListener	= new ARGEconPlayerListener(this);
	static String								maindirectory	= "ARGPlugins/";
	static File									playerRanksFile	= new File(maindirectory + "playerranks.data");
	static File									Accounts		= new File(maindirectory + "user.accounts");
	static File									ItemStore		= new File(maindirectory + "store.properties");
	FileInputStream								rankInput		= null;
	Properties									PlayerRanks		= new Properties();

	// public Properties LockedChests = new Properties();
	// private final Properties PlayerRanks = new Properties();

	public void onEnable() {

	
		
		if (!ItemStore.exists()) {
			try {
				new File(maindirectory).mkdir();
				ItemStore.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!Accounts.exists()) {
			try {
				new File(maindirectory).mkdir();
				Accounts.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		deathevents.put("player", getConfiguration().getStringList("player", null));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DAMAGED, deathListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_LOGIN, this.playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, this.deathListener, Priority.Normal, this);

		// other crap
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		// String[] trimmedArgs = args;
		String commandName = command.getName().toLowerCase();
		if (commandName.equals("store")) {
			Player p = (Player) sender;
			return this.myStore.getStore(p);
		} else if (commandName.equals("buy")) {
			return this.myStore.buyCommand(sender, args);
		}
		if (commandName.equals("bank")) {
			return this.myStore.getCurrencyTotal(sender);
		}
		return false;
	}

	public void onDisable() {
		System.out.println("ARGEcon Disabeled.");
	}

	public int getRank(Player p) {
		String player = p.getName().toLowerCase();
		String string = PlayerRanks.getProperty(player);
		int rank = Integer.parseInt(string);
		if (rank > -1) {
			return rank;
		} else {
			// return -1 if something goes wrong
			return -1;
		}

	}

}

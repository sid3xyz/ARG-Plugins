package com.argerk.Magick.ARGEconomy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.entity.Player;

public class ATM_Machine {
	public static File				atmFile	= null;
	public static FileInputStream	atmStream;
	public static Properties		atmAccounts;

	public ATM_Machine(final File atmFile) {
		this.atmFile = atmFile;
		try {
			if (!atmFile.exists()) {
				new File(ARGEcon.maindirectory).mkdir();
				atmFile.createNewFile();
			}
			atmStream = new FileInputStream(atmFile);
			atmAccounts.load(atmStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean containskey(Player p, File file) {
		String player = p.getName();
		if (atmAccounts.containsKey(player)) {
			return true;
		}

		return false;
	}

	public static void write(Player p, int startingB, File file) {

		String startingvalue = new Integer(startingB).toString();
		String player = p.getName();
		try {
			atmAccounts.load(atmStream);
			atmAccounts.setProperty(player, startingvalue);
			atmAccounts.store(new FileOutputStream(atmFile), null);
		} catch (IOException e) {
			p.sendMessage("Error writing to accounting file");
		}
	}

	public static int getBalance(Player p, File file) {
		String player = p.getName();
		try {
			FileInputStream in = new FileInputStream(file);
			atmAccounts.load(in);
			String string = atmAccounts.getProperty(player);
			int balance = Integer.parseInt(string);
			return balance;
		} catch (IOException e) {
				p.sendMessage("Error getting balance.");
		}
		return 0;
	}
}

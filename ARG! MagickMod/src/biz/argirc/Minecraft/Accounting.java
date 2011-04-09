package biz.argirc.Minecraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.entity.Player;

// this file contains code to read/write to the accounting file
public class Accounting {
	private static File	file;

	public Accounting(File file) {
		this.setFile(file);
	}

	public static boolean containskey(Player p) {
		Properties pro = new Properties();
		String player = p.getName();
		try {
			FileInputStream in = new FileInputStream(getFile());
			pro.load(in);
			if (pro.containsKey(player)) {
				return true;
			}
		} catch (IOException e) {

		}
		return false;
	}

	public static void write(Player p, int startingB) {
		Properties pro = new Properties();
		String startingvalue = new Integer(startingB).toString();
		String player = p.getName();
		try {
			FileInputStream in = new FileInputStream(getFile());
			pro.load(in);
			pro.setProperty(player, startingvalue);
			pro.store(new FileOutputStream(getFile()), null);
		} catch (IOException e) {

		}
	}

	public static int getBalance(Player p) {
		Properties pro = new Properties();
		String player = p.getName();
		try {
			FileInputStream in = new FileInputStream(getFile());
			pro.load(in);
			String string = pro.getProperty(player);
			int balance = Integer.parseInt(string);
			return balance;
		} catch (IOException e) {

		}
		return 0;
	}

	public void setFile(File file) {
		Accounting.file = file;
	}

	public static File getFile() {
		return file;
	}

}
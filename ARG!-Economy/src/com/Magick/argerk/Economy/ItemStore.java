package com.Magick.argerk.Economy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

// This file contains code for Item Store Read/write

public class ItemStore {

	public ItemStore() {
		// load the store properties
		LoadSettings.loadStore();
	}

	public Boolean getCurrencyTotal(CommandSender sender) {

		Player player = (Player) sender;
		boolean hasaccount = Accounting.containskey(player, ARGEcon.Accounts);
		if (hasaccount == false) {
			Accounting.write(player, LoadSettings.startingbalance, ARGEcon.Accounts);
		}
		int balance = Accounting.getBalance(player, ARGEcon.Accounts);
		player.sendMessage(ChatColor.GREEN + "You have " + balance + " " + LoadSettings.credit);
		return true;

	}

	public Boolean buyCommand(CommandSender sender, String[] args) {

		Player player = (Player) sender;
		String item = args[0];

		if (Accounting.containskey(player, ARGEcon.Accounts)) {
			boolean IsForSale = ItemStore.containsItem(item, ARGEcon.ItemStore);

			if (IsForSale) {
				int Price = ItemStore.getPrice(item, ARGEcon.ItemStore);
				int count = ItemStore.getCount(item, ARGEcon.ItemStore);
				int ItemID = ItemStore.getItemID(item, ARGEcon.ItemStore);
				int balance = Accounting.getBalance(player, ARGEcon.Accounts);
				
				if (balance - Price >= 0) {
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "Your inventiry is full");
					} else {
						ItemStack stack = new ItemStack(ItemID, count);
						player.getInventory().addItem(stack);

						int newbalance = balance - Price;
						Accounting.write(player, newbalance, ARGEcon.Accounts);
						player.sendMessage(ChatColor.GREEN + "->You got " + count + " " + item + " for " + Price + " "
								+ LoadSettings.credit);
						player.sendMessage(ChatColor.GREEN + " ->You have " + newbalance + " " + LoadSettings.credit
								+ " left");
						return true;
					}
				} else {
					player.sendMessage(ChatColor.RED + "You need more " + LoadSettings.credit + " man.");
					return false;
				}
			}
		}
		return false;
	}

	public boolean getStore(Player p) {

		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream(ARGEcon.ItemStore);
			pro.load(in);
			@SuppressWarnings("rawtypes")
			Enumeration e = pro.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				int count = ItemStore.getCount(key, ARGEcon.ItemStore);
				int price = ItemStore.getPrice(key, ARGEcon.ItemStore);

				p.sendMessage(count + " " + key + " for " + price + " " + LoadSettings.credit);
			}
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	public static boolean containsItem(String i, File file) {
		Properties pro = new Properties();

		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			if (pro.containsKey(i)) {
				return true;
			}
		} catch (IOException e) {

		}
		return false;
	}

	public static void write(String i, int startingB, File file) {
		Properties pro = new Properties();
		String startingvalue = new Integer(startingB).toString();

		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			pro.setProperty(i, startingvalue);
			pro.store(new FileOutputStream(file), null);
		} catch (IOException e) {

		}
	}

	public static int getPrice(String i, File file) {
		Properties pro = new Properties();

		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			String[] string = pro.getProperty(i).split(",");

			int price = Integer.parseInt(string[0]);
			return price;
		} catch (IOException e) {

		}
		return 0;
	}

	public static int getCount(String i, File file) {
		Properties pro = new Properties();

		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			String[] string = pro.getProperty(i).split(",");

			int count = Integer.parseInt(string[1]);
			return count;
		} catch (IOException e) {

		}
		return 0;
	}

	public static int getItemID(String i, File file) {
		Properties pro = new Properties();

		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			String[] string = pro.getProperty(i).split(",");

			int id = Integer.parseInt(string[2]);
			return id;
		} catch (IOException e) {

		}
		return 0;
	}

}
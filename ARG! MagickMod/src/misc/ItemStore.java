package misc;

import biz.argirc.Minecraft.GeneralSettings;
import biz.argirc.Minecraft.MagickMod;


// This file contains code for Item Store Read/write

public class ItemStore {
	private final MagickMod	plugin;

	public ItemStore(MagickMod plugin) {
		this.plugin = plugin;
		// load the store properties
		GeneralSettings.loadStore();
	}
	/*
	 * public Boolean getCurrencyTotal(CommandSender sender) {
	 * 
	 * Player player = (Player) sender; boolean hasaccount =
	 * Accounting.containskey(player); if (hasaccount == false) {
	 * Accounting.write(player, GeneralSettings.startingbalance); } int balance
	 * = Accounting.getBalance(player); player.sendMessage(ChatColor.GREEN +
	 * "You have " + balance + " " + GeneralSettings.credit); return true;
	 * 
	 * }
	 * 
	 * public Boolean buyCommand(CommandSender sender, String[] args) {
	 * 
	 * Player player = (Player) sender; String item = args[0];
	 * 
	 * if (Accounting.containskey(player)) { boolean IsForSale =
	 * ItemStore.containsItem(item, plugin.ItemStoreFile);
	 * 
	 * if (IsForSale) { int Price = ItemStore.getPrice(item,
	 * plugin.ItemStoreFile); int count = ItemStore.getCount(item,
	 * plugin.ItemStoreFile); int ItemID = ItemStore.getItemID(item,
	 * plugin.ItemStoreFile); int balance = Accounting.getBalance(player);
	 * 
	 * if (balance - Price >= 0) { if (player.getInventory().firstEmpty() == -1)
	 * { player.sendMessage(ChatColor.RED + "Your inventiry is full"); } else {
	 * ItemStack stack = new ItemStack(ItemID, count);
	 * player.getInventory().addItem(stack);
	 * 
	 * int newbalance = balance - Price; Accounting.write(player, newbalance);
	 * player.sendMessage(ChatColor.GREEN + "->You got " + count + " " + item +
	 * " for " + Price + " " + GeneralSettings.credit);
	 * player.sendMessage(ChatColor.GREEN + " ->You have " + newbalance + " " +
	 * GeneralSettings.credit + " left"); return true; } } else {
	 * player.sendMessage(ChatColor.RED + "You need more " +
	 * GeneralSettings.credit + " man."); return false; } } } return false; }
	 * 
	 * public boolean getStore(Player p) {
	 * 
	 * Properties pro = new Properties(); try { FileInputStream in = new
	 * FileInputStream(plugin.ItemStoreFile); pro.load(in);
	 * 
	 * @SuppressWarnings("rawtypes") Enumeration e = pro.propertyNames(); while
	 * (e.hasMoreElements()) { String key = (String) e.nextElement(); int count
	 * = ItemStore.getCount(key, plugin.ItemStoreFile); int price =
	 * ItemStore.getPrice(key, plugin.ItemStoreFile);
	 * 
	 * p.sendMessage(count + " " + key + " for " + price + " " +
	 * GeneralSettings.credit); } return true; } catch (IOException e) { return
	 * false; }
	 * 
	 * }
	 * 
	 * public static boolean containsItem(String i, File file) { Properties pro
	 * = new Properties();
	 * 
	 * try { FileInputStream in = new FileInputStream(file); pro.load(in); if
	 * (pro.containsKey(i)) { return true; } } catch (IOException e) {
	 * 
	 * } return false; }
	 * 
	 * public static void write(String i, int startingB, File file) { Properties
	 * pro = new Properties(); String startingvalue = new
	 * Integer(startingB).toString();
	 * 
	 * try { FileInputStream in = new FileInputStream(file); pro.load(in);
	 * pro.setProperty(i, startingvalue); pro.store(new FileOutputStream(file),
	 * null); } catch (IOException e) {
	 * 
	 * } }
	 * 
	 * public static int getPrice(String i, File file) { Properties pro = new
	 * Properties();
	 * 
	 * try { FileInputStream in = new FileInputStream(file); pro.load(in);
	 * String[] string = pro.getProperty(i).split(",");
	 * 
	 * int price = Integer.parseInt(string[0]); return price; } catch
	 * (IOException e) {
	 * 
	 * } return 0; }
	 * 
	 * public static int getCount(String i, File file) { Properties pro = new
	 * Properties();
	 * 
	 * try { FileInputStream in = new FileInputStream(file); pro.load(in);
	 * String[] string = pro.getProperty(i).split(",");
	 * 
	 * int count = Integer.parseInt(string[1]); return count; } catch
	 * (IOException e) {
	 * 
	 * } return 0; }
	 * 
	 * public static int getItemID(String i, File file) { Properties pro = new
	 * Properties();
	 * 
	 * try { FileInputStream in = new FileInputStream(file); pro.load(in);
	 * String[] string = pro.getProperty(i).split(",");
	 * 
	 * int id = Integer.parseInt(string[2]); return id; } catch (IOException e)
	 * {
	 * 
	 * } return 0; }
	 */
}
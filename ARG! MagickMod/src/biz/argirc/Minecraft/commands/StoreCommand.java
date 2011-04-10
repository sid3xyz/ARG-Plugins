package biz.argirc.Minecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StoreCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		/*
		 * Player p = (Player) sender; Properties pro = new Properties(); try {
		 * FileInputStream in = new FileInputStream(ARGEcon.StoreFunctions);
		 * pro.load(in);
		 * 
		 * @SuppressWarnings("rawtypes") Enumeration e = pro.propertyNames();
		 * while (e.hasMoreElements()) { String key = (String) e.nextElement();
		 * int count = StoreFunctions.getCount(key, ARGEcon.StoreFunctions); int
		 * price = StoreFunctions.getPrice(key, ARGEcon.StoreFunctions);
		 * 
		 * p.sendMessage(count + " " + key + " for " + price + " " +
		 * LoadSettings.credit); } return true; } catch (IOException e) { return
		 * false; }
		 * 
		 * return false; }
		 */
		return false;
	}
}
package biz.argirc.Minecraft.commands;

import java.util.Enumeration;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.Functions.ShopFunctions;

public class StoreCommand implements CommandExecutor {
	public final ShopFunctions	shopFunctions;

	public StoreCommand(ShopFunctions shopFunctions) {
		this.shopFunctions = shopFunctions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		Enumeration<?> itemList = shopFunctions.ListShopItems();
		while (itemList.hasMoreElements()) {
			String key = (String) itemList.nextElement();
			int count = shopFunctions.getCount(key);
			int price = shopFunctions.getPrice(key);
			p.sendMessage(count + " " + key + " for " + price + " " + shopFunctions.getCurrancyName());
		}

		return true;
	}
}
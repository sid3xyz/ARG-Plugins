package biz.argirc.Minecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import biz.argirc.Minecraft.BankFunctions;
import biz.argirc.Minecraft.ShopFunctions;

public class BuyCommand implements CommandExecutor {
	private final BankFunctions	bankFunctions;
	private final ShopFunctions	shopFunctions;

	public BuyCommand(BankFunctions bankFunctions, ShopFunctions shopFunctions) {
		this.bankFunctions = bankFunctions;
		this.shopFunctions = shopFunctions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		String playerName = player.getName();
		String item = args[0];
		int accountBalance = bankFunctions.getBalance(playerName);
		if (!bankFunctions.hasAccount(playerName)) {
			player.sendMessage("You do not have an account");
			return true;
		} else if (!shopFunctions.containsItem(item)) {
			player.sendMessage("No item " + item + " for sale");
			return true;
		} else if (accountBalance - shopFunctions.getPrice(item) >= 0) {
			if (player.getInventory().firstEmpty() == -1) {
				player.sendMessage(ChatColor.RED + "Your inventory is full");
				return true;
			} else {
				bankFunctions.setBalance(playerName, accountBalance - shopFunctions.getPrice(item));
				ItemStack stack = new ItemStack(shopFunctions.getItemID(item), shopFunctions.getCount(item));
				player.getInventory().addItem(stack);
				player.sendMessage(ChatColor.GREEN + "->You got " + shopFunctions.getCount(item) + " " + item + " for " + shopFunctions.getPrice(item) + " " + shopFunctions.getCurrancyName());
				player.sendMessage(ChatColor.GREEN + " ->You have " + bankFunctions.getBalance(playerName) + " " + shopFunctions.getCurrancyName() + " left");
				return true;
			}

		} else {
			player.sendMessage(ChatColor.RED + "You need more " + shopFunctions.getCurrancyName() + " man.");
			return true;
		}
	}
}

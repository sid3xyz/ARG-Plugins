package biz.argirc.Minecraft;

import biz.argirc.Minecraft.database.BankData;

public class BankFunctions {
	public final MagickMod	plugin;

	public BankFunctions(MagickMod plugin) {
		this.plugin = plugin;
	}

	public boolean hasAccount(String name) {
		BankData bankAccount = plugin.getDatabase().find(BankData.class).where().ieq("playerName", name).findUnique();
		if (bankAccount == null) {
			return false;
		}
		return true;
	}

	public int getBalance(String name) {
		BankData bankAccount = plugin.getDatabase().find(BankData.class).where().ieq("playerName", name).findUnique();
		return bankAccount.getBalance();
	}

	public void setBalance(String player, int balance) {
		BankData bankAccount = plugin.getDatabase().find(BankData.class).where().ieq("playerName", player).findUnique();
		bankAccount.setBalance(balance);
		plugin.getDatabase().save(bankAccount);
	}

}

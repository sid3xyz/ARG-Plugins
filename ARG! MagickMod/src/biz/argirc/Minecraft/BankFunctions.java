package biz.argirc.Minecraft;

import biz.argirc.Minecraft.database.BankData;

import com.avaje.ebean.EbeanServer;

public class BankFunctions {
	public final EbeanServer	database;

	public BankFunctions(EbeanServer database) {
		this.database = database;
	}

	public boolean hasAccount(String name) {
		BankData bankAccount = database.find(BankData.class).where().ieq("playerName", name).findUnique();
		if (bankAccount == null) {
			return false;
		}
		return true;
	}

	public int getBalance(String name) {
		BankData bankAccount = database.find(BankData.class).where().ieq("playerName", name).findUnique();
		return bankAccount.getBalance();
	}

	public void setBalance(String player, int balance) {
		BankData bankAccount = database.find(BankData.class).where().ieq("playerName", player).findUnique();
		bankAccount.setBalance(balance);
		database.save(bankAccount);
	}

}

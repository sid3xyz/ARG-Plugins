package biz.argirc.Minecraft;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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

	public void convertDB() {

		String maindirectory = "ARGPlugins/";
		File file = new File(maindirectory + "user.accounts");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		// DataInputStream dis = null;

		try {
			fis = new FileInputStream(file);

			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			BufferedReader dis = new BufferedReader(new InputStreamReader(fis));

			String dataLine;
			String username;
			int balance;
			int balStart;
			BankData myAccount;
			// dis.available() returns 0 if the file does not have more lines.
			System.out.println("Starting Convert!!!");
			while (dis.ready()) {

				dataLine = dis.readLine();
				// System.out.println(dataLine);

				balStart = dataLine.lastIndexOf('=');
				balance = Integer.parseInt(dataLine.substring(balStart + 1));
				username = dataLine.substring(0, balStart);
				System.out.println(username);
				System.out.println(balance);
				myAccount = new BankData();
				// myAccount.setName(username);
				myAccount.setPlayerName(username);
				myAccount.setBalance(balance);
				plugin.getDatabase().save(myAccount);

			}

			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}
}

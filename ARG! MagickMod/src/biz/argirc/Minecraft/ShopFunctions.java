package biz.argirc.Minecraft;

import java.io.FileInputStream;
import java.io.IOException;

// This file contains code for Item Store Read/write

public class ShopFunctions {
	private final String	propertiesFile;
	private final String	currancyName;

	public ShopFunctions(String propertiesFile) {
		this.propertiesFile = propertiesFile;
		PluginProperties ItemShopData = new PluginProperties(propertiesFile);
		ItemShopData.load();
		this.currancyName = ItemShopData.getString("Currancy", "Tacos");
	}

	public boolean containsItem(String i) {
		PluginProperties ItemShopData = new PluginProperties(propertiesFile);
		try {
			FileInputStream in = new FileInputStream(propertiesFile);
			ItemShopData.load(in);
			if (ItemShopData.containsKey(i)) {
				return true;
			}
		} catch (IOException e) {

		}
		return false;
	}

	public int getPrice(String i) {
		PluginProperties ItemShopData = new PluginProperties(propertiesFile);
		try {
			FileInputStream in = new FileInputStream(propertiesFile);
			ItemShopData.load(in);
			String[] string = ItemShopData.getProperty(i).split(",");

			int price = Integer.parseInt(string[0]);
			return price;
		} catch (IOException e) {

		}
		return 0;
	}

	public int getCount(String i) {
		PluginProperties ItemShopData = new PluginProperties(propertiesFile);

		try {
			FileInputStream in = new FileInputStream(propertiesFile);
			ItemShopData.load(in);
			String[] string = ItemShopData.getProperty(i).split(",");

			int count = Integer.parseInt(string[1]);
			return count;
		} catch (IOException e) {

		}
		return 0;
	}

	public int getItemID(String i) {
		PluginProperties ItemShopData = new PluginProperties(propertiesFile);
		try {
			FileInputStream in = new FileInputStream(propertiesFile);
			ItemShopData.load(in);
			String[] string = ItemShopData.getProperty(i).split(",");

			int id = Integer.parseInt(string[2]);
			return id;
		} catch (IOException e) {

		}
		return 0;
	}

	public String getCurrancyName() {
		return currancyName;
	}

}
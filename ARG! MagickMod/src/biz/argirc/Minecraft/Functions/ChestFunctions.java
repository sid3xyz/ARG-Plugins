package biz.argirc.Minecraft.Functions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.bukkit.Location;

import biz.argirc.Minecraft.MagickMod;
import biz.argirc.Minecraft.database.ChestData;

public class ChestFunctions {
	public final MagickMod	plugin;

	public ChestFunctions(MagickMod plugin) {
		this.plugin = plugin;
	}

	public void saveData(ChestData chest) {
		plugin.getDatabase().save(chest);
	}

	public String getOwner(Location chestLocation) {
		ChestData chest = plugin.getDatabase().find(ChestData.class).where().ieq("location", chestLocation.toString()).findUnique();
		if (chest == null) {
			return "public";
		}
		return chest.getName();
	}

	public void deleteChest(Location chestLocation) {
		List<ChestData> chests = plugin.getDatabase().find(ChestData.class).where().ieq("location", chestLocation.toString()).findList();

		if (chests.isEmpty()) {

		}
		for (ChestData chest : chests) {
			plugin.getDatabase().delete(chest);
		}

	}

	public boolean doesUserOwnChest(String userstring, Location chestLocation) {
		ChestData myChest = plugin.getDatabase().find(ChestData.class).where().ieq("location", chestLocation.toString()).ieq("name", userstring).findUnique();
		if (myChest == null) {
			return false;
		}
		return true;
	}

	public boolean isPublicChest(Location chestLocation) {
		ChestData chest = plugin.getDatabase().find(ChestData.class).where().ieq("location", chestLocation.toString()).ieq("name", "public").findUnique();
		// ChestData chest2 =
		// plugin.getDatabase().find(ChestData.class).where().ieq("location",
		// chestLocation.toString()).ieq("name", "null").findUnique();

		if (chest == null) {
			return false;
		}
		return true;
	}

	public ChestData getChest(Location chestLocation) {
		ChestData chest = plugin.getDatabase().find(ChestData.class).where().ieq("location", chestLocation.toString()).findUnique();
		if (chest == null) {
			return null;
		}
		return chest;
	}

	public void convertDB() {

		String maindirectory = "ARGPlugins/";
		File file = new File(maindirectory + "Chest.dat");
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
			String location;
			int nameStart;
			ChestData chest;
			// dis.available() returns 0 if the file does not have more lines.
			System.out.println("Starting Convert!!!");
			while (dis.ready()) {

				dataLine = dis.readLine();
				// System.out.println(dataLine);

				nameStart = dataLine.lastIndexOf('=');
				username = dataLine.substring(nameStart + 1);
				location = removeChar(dataLine.substring(0, nameStart), '\\').trim();
				System.out.println(username);
				System.out.println(location);
				chest = new ChestData();
				chest.setName(username);
				chest.setPlayerName(username);
				chest.setLocation(location);
				plugin.getDatabase().save(chest);

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

	public static String removeChar(String s, char c) {
		StringBuffer r = new StringBuffer(s.length());
		r.setLength(s.length());
		int current = 0;
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (cur != c) r.setCharAt(current++, cur);
		}
		return r.toString();
	}
}

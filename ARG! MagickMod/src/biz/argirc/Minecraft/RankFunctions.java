package biz.argirc.Minecraft;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import biz.argirc.Minecraft.database.RankData;

public class RankFunctions {

	private final MagickMod	plugin;

	public RankFunctions(MagickMod plugin) {
		this.plugin = plugin;
	}

	public boolean canBuild(String player) {
		if (plugin.rankFunctions.getRank(player) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getRank(String player) {
		RankData myRank = plugin.getDatabase().find(RankData.class).where().ieq("name", player).findUnique();
		if (myRank == null) {
			return 0;
		} else {
			return myRank.getRank();
		}
	}

	public void setRank(String name, int rank) {

		RankData playerRank = plugin.getDatabase().find(RankData.class).where().ieq("name", name).ieq("playerName", name).findUnique();
		if (playerRank == null) {
			playerRank = new RankData();
			playerRank.setPlayerName(name);
			playerRank.setName(name);
			playerRank.setRank(rank);
			plugin.getDatabase().update(playerRank);
			plugin.getServer().broadcastMessage("New player " + name + " has been given rank" + rank + "!");

		} else {
			playerRank.setRank(rank);
			plugin.getDatabase().save(playerRank);
			plugin.getServer().broadcastMessage(name + " has been given rank " + rank + "!");

		}
	}

	public void convertDB() {

		String maindirectory = "ARGPlugins/";
		File file = new File(maindirectory + "playerranks.data");
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
			int rank;
			int rankStart;
			RankData myRank;
			// dis.available() returns 0 if the file does not have more lines.
			System.out.println("Starting Convert!!!");
			while (dis.ready()) {

				dataLine = dis.readLine();
				// System.out.println(dataLine);

				rankStart = dataLine.lastIndexOf('=');
				rank = Integer.parseInt(dataLine.substring(rankStart + 1));
				username = dataLine.substring(0, rankStart);
				System.out.println(username);
				System.out.println(rank);
				myRank = new RankData();
				myRank.setName(username);
				myRank.setPlayerName(username);
				myRank.setRank(rank);
				plugin.getDatabase().save(myRank);

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

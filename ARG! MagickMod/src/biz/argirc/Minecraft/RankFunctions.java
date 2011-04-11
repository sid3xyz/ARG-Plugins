package biz.argirc.Minecraft;

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
			plugin.getDatabase().save(playerRank);
			plugin.getServer().broadcastMessage("New player " + name + " has been given rank" + rank + "!");

		} else {
			playerRank.setRank(rank);
			plugin.getDatabase().save(playerRank);
			plugin.getServer().broadcastMessage(name + " has been given rank " + rank + "!");

		}
	}
}

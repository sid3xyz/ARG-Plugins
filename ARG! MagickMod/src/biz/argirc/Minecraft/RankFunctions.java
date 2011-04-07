package biz.argirc.Minecraft;

import org.bukkit.entity.Player;

import biz.argirc.Minecraft.database.RankData;

public class RankFunctions {

	private final MagickMod	plugin;

	public RankFunctions(MagickMod plugin) {
		this.plugin = plugin;
	}

	public int getRank(Player player) {
		RankData myRank = plugin.getDatabase().find(RankData.class).where().ieq("name", player.getName()).findUnique();
		if (myRank == null) {
			return 0;
		} else {
			return myRank.getRank();
		}
	}

	public void setRank(Player player, int rank) {
		String name = player.getName();
		RankData playerRank = plugin.getDatabase().find(RankData.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();
		if (playerRank == null) {
			playerRank = new RankData();
			playerRank.setPlayer(player);
			playerRank.setName(name);
			playerRank.setRank(rank);
			plugin.getDatabase().save(playerRank);
			plugin.getServer().broadcastMessage("New player " + player.getName() + " has been given rank" + rank + "!");

		} else {
			playerRank.setRank(rank);
			plugin.getDatabase().save(playerRank);
			plugin.getServer().broadcastMessage(player.getName() + " has been given rank" + rank + "!");

		}
	}
}

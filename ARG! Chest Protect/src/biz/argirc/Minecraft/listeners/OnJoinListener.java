package biz.argirc.Minecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import biz.argirc.Minecraft.MagickMod;
import biz.argirc.Minecraft.database.RankData;

public class OnJoinListener extends PlayerListener {
	private final MagickMod	plugin;

	public OnJoinListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (!isNewPlayer(player)) {
			player.sendMessage("Welcome back" + player.getName());
		}
	}

	public boolean isNewPlayer(Player player) {
		String name = player.getName();
		RankData playerRank = plugin.getDatabase().find(RankData.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();
		if (playerRank == null) {
			playerRank = new RankData();
			playerRank.setPlayer(player);
			playerRank.setName(name);
			playerRank.setRank(0);
			plugin.getDatabase().save(playerRank);
			plugin.getServer().broadcastMessage("A new player named " + player.getName() + " has arrived!");
			player.sendMessage("Welcome " + player.getName());
			player.sendMessage("This is your first time here!");
			player.sendMessage("This Server is using ARG! MagickMod v2.0");
			player.sendMessage("You are currently Rank 0. Please contact an admin");
			player.sendMessage("and ask for Rank 1 in order to build/destroy");
			player.sendMessage("Have fun!");
			return true;
		}
		return false;

	}
}
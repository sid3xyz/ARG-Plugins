package biz.argirc.Minecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import biz.argirc.Minecraft.database.RankData;

public class OnJoinListener extends PlayerListener {
	private final MagickMod	plugin;

	public OnJoinListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		RankData playerRank = plugin.getDatabase().find(RankData.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();
		if (playerRank == null) {
			playerRank = new RankData();
			playerRank.setPlayer(player);
			playerRank.setName(name);
			playerRank.setRank(0);
		}
		plugin.getDatabase().save(playerRank);
	}
}
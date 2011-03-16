package com.bukkit.Magick.ARGAntiPirate;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.ChatColor;

public class ARGAntiPiratePlayerListener extends PlayerListener {
	private ARGAntiPirate	plugin;

	public ARGAntiPiratePlayerListener(final ARGAntiPirate plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();

		int myRank = plugin.rankMachine.getRank(player);
		if (player.getName().equalsIgnoreCase("skwerl12")) {
			player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
		} else {
			switch (myRank) {
				case 0:
					player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
					return;
				case 1:
					player.setDisplayName(ChatColor.GRAY + player.getName() + ChatColor.WHITE);
					return;
				case 2:
					player.setDisplayName(ChatColor.DARK_GRAY + player.getName() + ChatColor.WHITE);
					return;
				case 3:
					player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
					return;
				case 4:
					player.setDisplayName(ChatColor.GREEN + player.getName() + ChatColor.WHITE);
					return;
				case 5:
					player.setDisplayName(ChatColor.GOLD + player.getName() + ChatColor.WHITE);
					return;
			}
			player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
		}
	}

	@Override
	public void onPlayerLogin(PlayerLoginEvent event) {

		Player player = event.getPlayer();
		int myRank = plugin.rankMachine.getRank(player);
		if (myRank == -1) {
			String[] args = new String[2];
			args[0] = player.getName();
			args[1] = "" + 0;
			// add player as rank 0
			plugin.rankMachine.setRank(args);
			plugin.rankMachine.Save();
		}
	}
}

package com.bukkit.Magick.ARGAntiPirate;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.ChatColor;

public class ARGAntiPiratePlayerListener extends PlayerListener {

	public ARGAntiPiratePlayerListener(ARGAntiPirate instance) {

	}

	@Override
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();

		int myRank = ARGAntiPirate.rankMachine.getRank(player);
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
		int myRank = ARGAntiPirate.rankMachine.getRank(player);
		if (myRank == -1) {
			String[] args = new String[1];
			args[0] = player.getName();
			args[1] = "" + myRank;
			// add player as rank 0
			ARGAntiPirate.rankMachine.setRank(args);
		}
	}
}

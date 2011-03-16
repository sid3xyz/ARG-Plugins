package com.bukkit.Magick.ARGAntiPirate;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.entity.Player;

public class ARG_Rank {
	
	private Properties		PlayerRanks		= null;
	private ARGAntiPirate	plugin;

	public ARG_Rank(final ARGAntiPirate plugin,Properties PlayerRanks) {
		System.out.println("RANK SYSTEM INIT");
		this.plugin = plugin;
		this.PlayerRanks = PlayerRanks;
		
		
		try {
			if (!ARGAntiPirate.playerRanksFile.exists()) {
				new File(ARGAntiPirate.maindirectory).mkdir();
				ARGAntiPirate.playerRanksFile.createNewFile();
			}
			FileInputStream inn = new FileInputStream(ARGAntiPirate.playerRanksFile);
			PlayerRanks.load(inn);
		
		} catch (IOException e) {
			e.printStackTrace();
		
		}
	}

	public boolean Load() {
		try {

			FileInputStream inn = new FileInputStream(ARGAntiPirate.playerRanksFile);
			PlayerRanks.load(inn);
		
			return true;
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
	}

	public boolean Save() {
		try {

			FileOutputStream inn = new FileOutputStream(ARGAntiPirate.playerRanksFile);
			PlayerRanks.store(inn, null);
			System.out.println("Rank file Saved");
			return true;
		} catch (IOException e) {
			System.out.println("Rank file failed to save");
			e.printStackTrace();
			return false;
		}
	}

	public int getRank(Player p) {
		String player = p.getName().toLowerCase();

		if (PlayerRanks.getProperty(player) == null) {
			return -1;
		} else {
			return Integer.parseInt(PlayerRanks.getProperty(player));
		}
	}

	public boolean isAdmin(String player) {
		String prank = PlayerRanks.getProperty(player);
		if (Integer.parseInt(prank) >= 4) {
			return true;
		} else {
			return false;
		}
	}

	public int setRank(String[] args) {
		try {
			PlayerRanks.setProperty(args[0].toLowerCase(), args[1]);
			PlayerRanks.store(new FileOutputStream(ARGAntiPirate.playerRanksFile), null);
			return Integer.valueOf(args[1]);
		} catch (IOException e) {
			plugin.getServer().broadcastMessage("Problem setting Rank data.");
			System.out.println("ERROR SETTING RANK");
			return -1;
		}
	}

	public boolean removeUser(String player) {
		try {
			PlayerRanks.remove(player);
			PlayerRanks.store(new FileOutputStream(ARGAntiPirate.playerRanksFile), null);
			
			plugin.getServer().broadcastMessage("Removing player " + player + "from rank database");
			return true;
		} catch (IOException e) {
			plugin.getServer().broadcastMessage("Problem removing player");
			return false;
		}
	}

}

package com.bukkit.Magick.ARGEconomy;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Handle events for all Player related events
 * 
 * @author Magick
 */
public class ARGEconPlayerListener extends PlayerListener {
//	private final ARGEcon	plugin;

	public ARGEconPlayerListener(ARGEcon instance) {
	//	plugin = instance;
	}

	public void onPlayerLogin(PlayerLoginEvent event) {

		Player player = event.getPlayer();
		
		// On Login, check to see if player has an account
		int startingvalue = LoadSettings.startingbalance;
		boolean hasaccount = Accounting.containskey(player, ARGEcon.Accounts);
		// if not, make one with the default value
		if (hasaccount == false) {
			Accounting.write(player, startingvalue, ARGEcon.Accounts);
		}
	}

}

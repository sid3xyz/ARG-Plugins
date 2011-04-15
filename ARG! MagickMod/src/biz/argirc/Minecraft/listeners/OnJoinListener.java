package biz.argirc.Minecraft.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import biz.argirc.Minecraft.MagickMod;
import biz.argirc.Minecraft.database.BankData;
import biz.argirc.Minecraft.database.RankData;

public class OnJoinListener extends PlayerListener {
	private final MagickMod	plugin;

	public OnJoinListener(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		ItemStack item = new ItemStack(Material.GOLD_AXE, 1);
		event.getPlayer().setItemInHand(item);
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!isNewPlayer(player)) {
			player.sendMessage("Welcome back!");
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
			BankData newAccount = new BankData();
			newAccount.setPlayer(player);
			newAccount.setPlayerName(name);
			newAccount.setBalance(500);
			plugin.getDatabase().save(playerRank);
			plugin.getDatabase().save(newAccount);
			plugin.getServer().broadcastMessage("A new player named " + player.getName() + " has arrived!");
			player.sendMessage("Welcome " + player.getName());
			player.sendMessage("This is your first time here!");
			player.sendMessage("This Server is using ARG! MagickMod v2.0");
			player.sendMessage("Please contact an admin in order to build/destroy");
			player.sendMessage("Suggestions/Bugs go to http://arg-irc.biz/bugzilla");
			player.sendMessage("Have fun!");
			return true;
		}
		BankData bankAccount = plugin.getDatabase().find(BankData.class).where().ieq("playerName", player.getName()).findUnique();
		if (bankAccount == null) {
			BankData newAccount = new BankData();
			newAccount.setPlayer(player);
			newAccount.setPlayerName(name);
			newAccount.setBalance(500);
			plugin.getDatabase().save(newAccount);
			player.sendMessage("New Bank Account Created");
		}
		return false;
	}
}
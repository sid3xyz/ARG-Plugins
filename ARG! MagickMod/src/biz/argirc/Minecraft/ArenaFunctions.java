package biz.argirc.Minecraft;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import biz.argirc.Minecraft.database.InventoryData;

public class ArenaFunctions {
	public final MagickMod	plugin;
	private boolean			startBattle	= false;

	public ArenaFunctions(MagickMod plugin) {
		this.plugin = plugin;
	}

	public void setStartBattle(boolean startBattle) {
		this.startBattle = startBattle;
	}

	public boolean isStartBattle() {
		return startBattle;
	}

	public void StartBattle(List<Player> targets) {

	}

	public void storeInventories(List<Player> players) {
		for (Player p : players) {

			Inventory myInventory = p.getInventory();
			InventoryData inventory = new InventoryData(myInventory, p.getName());
			// inventory.setInventory(myInventory);
			inventory.setPlayer(p);
			inventory.setPlayerName(p.getName());

			plugin.getDatabase().save(inventory);
		}

	}
}

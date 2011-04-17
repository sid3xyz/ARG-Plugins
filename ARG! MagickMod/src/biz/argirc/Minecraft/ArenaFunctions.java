package biz.argirc.Minecraft;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

	public static void StartBattle(List<Player> targets) {
		storeInventories(targets);
	}

	public static void storeInventories(List<Player> players) {
		for (Player p : players) {
			serializeInventory(p);
			p.getInventory().clear();
			p.sendMessage("Inventory saved, prepare for battle!");

		}

	}

	public static void serializeInventory(Player player) {

		String filename = player.getName().toLowerCase() + "Inventory.dat";
		// InventoryStore playerInventory = new InventoryStore(player);
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			List<String> inventory = new ArrayList<String>();

			for (ItemStack item : player.getInventory().getContents()) {
				if (item.getType() == Material.AIR) {
					// skip
				}
				inventory.add(item.getType() + ";" + item.getAmount());
			}

			out.writeObject(inventory);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void restoreInventory(Player player) {
		String filename = player.getName().toLowerCase() + "Inventory.dat";
		List<String> playerInventory = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			playerInventory = (List<String>) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		player.getInventory().clear();
		for (String item : playerInventory) {
			int x = item.indexOf(";");
			String itemName = item.substring(0, x);
			if (!itemName.equalsIgnoreCase("air")) {
				int count = Integer.parseInt(item.substring(x + 1));
				player.sendMessage("Returning your " + count + " " + itemName);
				// TODO add checks and place armor in the proper slots
				Material myItem = Material.getMaterial(itemName.toUpperCase());
				ItemStack myStack = new ItemStack(myItem, count);
				player.getInventory().addItem(myStack);
			}

		}

	}
}

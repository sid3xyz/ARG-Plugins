package biz.argirc.Minecraft.Functions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import biz.argirc.Minecraft.MagickMod;

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
		transportUsers(targets);
	}

	private static void transportUsers(List<Player> targets) {
		Location arena = new Location(Bukkit.getServer().getWorld("world"), 90, 75, -250);

		for (Player p : targets) {
			p.teleport(arena);

		}

	}

	public static void storeInventories(List<Player> players) {
		for (Player p : players) {
			serializeInventory(p);
			p.getInventory().clear();
			p.sendMessage("Inventory saved, prepare for battle!");

		}

	}

	public static void serializeInventory(Player player) {

		String filename = player.getName().toLowerCase() + "_Inventory.dat";

		// InventoryStore playerInventory = new InventoryStore(player);

		List<String> inventory = new ArrayList<String>();
		player.sendMessage("Getting inv contents");
		for (ItemStack item : player.getInventory().getContents()) {
			player.sendMessage("found: " + item.getType().name());
			inventory.add(item.getType().name() + ";" + item.getAmount());
		}
		player.sendMessage("Writing file...");
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {

			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(inventory);
			out.close();
			player.sendMessage("Done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException np) {
			np.getCause();
		}
	}

	@SuppressWarnings("unchecked")
	public static void restoreInventory(Player player) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		String filename = player.getName().toLowerCase() + "_Inventory.dat";
		List<String> playerInventory = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			playerInventory = (ArrayList<String>) in.readObject();
			in.close();
			player.getInventory().clear();
			for (String item : playerInventory) {
				int x = item.indexOf(";");
				String itemName = item.substring(0, x);
				int count = Integer.parseInt(item.substring(x + 1));
				player.sendMessage("Returning your " + count + " " + itemName);
				// TODO add checks and place armor in the proper slots
				Material myItem = Material.getMaterial(itemName.toUpperCase());
				ItemStack myStack = new ItemStack(myItem, count);
				player.getInventory().addItem(myStack);

			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}

package com.bukkit.Magick.ARGUtilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.CreatureType;

import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

import org.bukkit.entity.Player;

public class Utilities {
	private final ARGUtilities			plugin;
	private static ArrayList<Material>	notFloorBlocks	= new ArrayList<Material>();

	public Utilities(ARGUtilities argUtilities) {
		plugin = argUtilities;
		notFloorBlocks.add(Material.LAVA);
		notFloorBlocks.add(Material.AIR);
		notFloorBlocks.add(Material.FIRE);
		notFloorBlocks.add(Material.FENCE);
		notFloorBlocks.add(Material.SUGAR_CANE_BLOCK);
		notFloorBlocks.add(Material.AIR);
		notFloorBlocks.add(Material.CACTUS);
	}

	public Boolean setCompass(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		player.sendMessage("Setting your compass to " + x + " Y: " + y + " Z: " + z);
		player.setCompassTarget(player.getLocation());
		return true;
	}

	public boolean setSpawnLocation(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (player.isOp() == false) {
			return false;
		}
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		return player.getWorld().setSpawnLocation(x, y, z);

	}

	public boolean explodeMe(CommandSender sender) {
		Player player = (Player) sender;
		player.sendMessage("BOOM");
		return true;
	}

	public boolean spawnMob(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (player.isOp()) {
			if (args.length > 0) {
				int creatureCount = 1;
				Location loc = player.getTargetBlock(null, 50).getLocation();
				loc.setY(loc.getY() + 1);
				String creatureString = args[0].toUpperCase();
				if (creatureString.equals("PIGZOMBIE")) {
					creatureString = "PIG_ZOMBIE";
				}
				if (args.length == 2) {
					creatureCount = Integer.valueOf(args[1]);
				}
				for (int i = 0; i < creatureCount; i++) {
					player.getWorld().spawnCreature(loc, CreatureType.valueOf(creatureString));
				}
			}
		}
		return true;
	}

	public boolean KillMobs(CommandSender sender) {
		World world = this.plugin.getServer().getWorld("world");
		if (sender.isOp()) {
			List<?> mobs = world.getLivingEntities();
			for (Iterator<?> iterator = mobs.iterator(); iterator.hasNext();) {
				LivingEntity m = (LivingEntity) iterator.next();
				if (isMonster(m)) {
					m.setHealth(0);
				}
			}
		}
		return true;
	}

	public boolean Killanimals(CommandSender sender) {
		World world = this.plugin.getServer().getWorld("world");
		if (sender.isOp()) {
			List<?> mobs = world.getLivingEntities();
			for (Iterator<?> iterator = mobs.iterator(); iterator.hasNext();) {
				LivingEntity m = (LivingEntity) iterator.next();
				if (isAnimal(m)) {
					m.setHealth(0);
				}
			}
		}
		return true;
	}

	public boolean isMonster(LivingEntity e) {
		return (e instanceof Monster);
	}

	public boolean isAnimal(LivingEntity e) {
		return (e instanceof Animals);
	}

	public int playerCount() {
		Player players[] = plugin.getServer().getOnlinePlayers();
		int x = 0;
		for (@SuppressWarnings("unused")
		Player p : players) {
			x++;
		}
		return x;
	}

	// Message to be sent when a player uses /list
	public boolean showPlayerList(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String tempList = "";
		int x = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if (p != null && x + 1 == playerCount()) {
				tempList += p.getName();
				x++;
			}
			if (p != null && x < playerCount()) {
				tempList += p.getName() + ", ";
				x++;
			}
		}
		// Output the player list
		player.sendMessage(ChatColor.RED + "Player List (" + ChatColor.WHITE + tempList + ChatColor.RED + ")");
		player.sendMessage(ChatColor.RED + "Total Players: " + ChatColor.GREEN + playerCount());
		return true;
	}

	public boolean telePlayer(CommandSender sender) {
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 35);
		int x = (int) targetBlock.getX();
		int y = (int) targetBlock.getY();
		int z = (int) targetBlock.getZ();
		if (checkSafe(x, y, z)) {
			Location loc = targetBlock.getLocation();
			double yloc = loc.getY();
			loc.setY(yloc + 1);
			player.sendMessage("Teleporting now...");
			player.teleportTo(loc);
			return true;
		} else {
			player.sendMessage("It would be unwise to teleport there...");
			return true;
		}
	}

	public boolean xyzport(CommandSender sender, String args[]) {
		if (sender.isOp() == false) {
			sender.sendMessage("You must be an Op to do this...");
			return false;
		}
		if (args.length == 3) {
			int x = Integer.valueOf(args[0]);
			int y = Integer.valueOf(args[1]);
			int z = Integer.valueOf(args[2]);
			if (checkSafe(x, y, z)) {
				Location targetLocation = this.plugin.getServer().getWorld("world").getBlockAt(x, y, z).getLocation();
				// Player player = (Player) sender;
				targetLocation.setY(y + 1);
				sender.sendMessage("Teleporting now...");
				((Player) sender).teleportTo(targetLocation);
				return true;
			} else {
				sender.sendMessage("It would be unwise to teleport there...");
				return true;
			}
		}
		sender.sendMessage("Invalid location.");
		return true;
	}

	public boolean checkSafe(int x, int y, int z) {
		World myWorld = this.plugin.getServer().getWorld("world");
		Block targetBlock = myWorld.getBlockAt(x, y, z);
		Block oneUp = myWorld.getBlockAt(x, y + 1, z);
		Block twoUp = myWorld.getBlockAt(x, y + 2, z);
		Block oneDown = myWorld.getBlockAt(x, y - 1, z);
		if (oneUp.getTypeId() == 0 && twoUp.getTypeId() == 0
				&& (isFloor(targetBlock.getType()) || isFloor(oneDown.getType()))) {
			return true;
		}
		return false;
	}

	private boolean isFloor(Material mat) {
		for (Material iter : notFloorBlocks) {
			if (iter.equals(mat)) {
				return false;
			}
		}
		return true;
	}
}
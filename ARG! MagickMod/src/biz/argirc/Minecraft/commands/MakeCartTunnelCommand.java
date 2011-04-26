package biz.argirc.Minecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.Functions.HelperFunctions;

public class MakeCartTunnelCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if (!HelperFunctions.isAdmin(p)) {
			p.sendMessage("You are not an admin.");
			return true;
		}
		int length = Integer.parseInt(args[0]);

		Block target = p.getTargetBlock(null, 5);
		int x = (int) p.getLocation().getPitch();
		Location bl = target.getLocation();

		if (x >= 35) {
			// DOWN
			for (int pos = 0; pos < length; pos++) {
				Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() - pos, bl.getBlockZ()));
				if (nb.getType() == Material.BEDROCK) {
					return true;
				} else if (pos % 3 == 0) {
					nb.setType(Material.TNT);
				} else {
					replaceBlock(nb);
				}
			}
		} else if (x > -55) {
			// STRAIGHT
			int dir = (int) p.getLocation().getYaw();
			if (dir < 0) {
				dir *= -1;
			}
			dir %= 360;
			if (dir >= 300 || (dir >= 0 && dir <= 60)) {
				p.sendMessage("direction 1");
				// this is +Z

				for (int pos = 0; pos <= length; pos++) {
					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() + pos));

					Block trackBlock = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() - 1, bl.getBlockZ() + pos));

					Block torchSpot = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + 1, bl.getBlockZ() + pos));

					replaceBlock(nb);
					placeTrack(trackBlock);
					if (Math.abs(pos) % 8 == 0) {

						placeTorchBlock(torchSpot);
					} else {
						replaceBlock(torchSpot);
					}
				}
			}
			if (dir > 60 && dir <= 120) {
				// this is +X
				p.sendMessage("direction 2");
				for (int pos = 0; pos <= length; pos++) {

					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY(), bl.getBlockZ()));
					Block trackBlock = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY() - 1, bl.getBlockZ()));
					Block torchSpot = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY() + 1, bl.getBlockZ()));

					replaceBlock(nb);
					placeTrack(trackBlock);
					if (Math.abs(pos) % 8 == 0) {

						placeTorchBlock(torchSpot);
					} else {
						replaceBlock(torchSpot);
					}
				}
			}
			if (dir > 120 && dir <= 210) {
				p.sendMessage("direction 3");
				for (int pos = 0; pos <= length; pos++) {
					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() - pos));
					Block trackBlock = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() - 1, bl.getBlockZ() - pos));
					Block torchSpot = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + 1, bl.getBlockZ() - pos));

					replaceBlock(nb);
					placeTrack(trackBlock);
					if (Math.abs(pos) % 8 == 0) {

						placeTorchBlock(torchSpot);
					} else {
						replaceBlock(torchSpot);
					}
				}
			}
			if (dir > 210 && dir <= 300) {
				p.sendMessage("direction 4");
				for (int pos = 0; pos <= length; pos++) {
					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY(), bl.getBlockZ()));

					Block trackBlock = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY() - 1, bl.getBlockZ()));

					Block torchSpot = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY() + 1, bl.getBlockZ()));

					replaceBlock(nb);
					placeTrack(trackBlock);
					if (Math.abs(pos) % 8 == 0) {

						placeTorchBlock(torchSpot);
					} else {
						replaceBlock(torchSpot);
					}
				}
			}
		} else if (x <= -35) {
			// UP
			for (int pos = 0; pos <= length; pos++) {
				Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + pos, bl.getBlockZ()));

				replaceBlock(nb);
			}
		}
		return true;
	}

	private void placeTrack(Block trackBlock) {
		Block belowTrack = Bukkit.getServer().getWorld("world").getBlockAt(trackBlock.getX(), trackBlock.getY() - 1, trackBlock.getZ());
		belowTrack.setType(Material.OBSIDIAN);
		trackBlock.setType(Material.RAILS);

	}

	public void replaceBlock(Block b) {
		switch (b.getType()) {
			case CHEST:
			case FURNACE:
			case WORKBENCH:
			case BEDROCK:
				return;
			default:
				b.setType(Material.AIR);
		}
	}

	public void placeTorchBlock(Block b) {
		switch (b.getType()) {
			case CHEST:
			case FURNACE:
			case WORKBENCH:
			case BEDROCK:
				return;
			default:
				b.setType(Material.TORCH);
		}
	}
}

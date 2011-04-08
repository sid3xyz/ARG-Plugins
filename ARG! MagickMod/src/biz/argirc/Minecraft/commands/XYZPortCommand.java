package biz.argirc.Minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.MagickMod;

public class XYZPortCommand implements CommandExecutor {
	private static ArrayList<Material>	notFloorBlocks	= new ArrayList<Material>();

	public XYZPortCommand(MagickMod plugin) {
		notFloorBlocks.add(Material.LAVA);
		notFloorBlocks.add(Material.AIR);
		notFloorBlocks.add(Material.FIRE);
		notFloorBlocks.add(Material.FENCE);
		notFloorBlocks.add(Material.SUGAR_CANE_BLOCK);
		notFloorBlocks.add(Material.AIR);
		notFloorBlocks.add(Material.CACTUS);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender.isOp() == false) {
			sender.sendMessage("You must be an Op to do this...");
			return false;
		}
		if (args.length == 3) {
			int x = Integer.valueOf(args[0]);
			int y = Integer.valueOf(args[1]);
			int z = Integer.valueOf(args[2]);
			if (checkSafe(x, y, z, sender.getServer().getWorld("world"))) {
				Location targetLocation = sender.getServer().getWorld("world").getBlockAt(x, y, z).getLocation();
				// Player player = (Player) sender;
				targetLocation.setY(y + 1);
				sender.sendMessage("Teleporting now...");
				((Player) sender).teleport(targetLocation);
				return true;
			} else {
				sender.sendMessage("It would be unwise to teleport there...");
				return true;
			}
		}
		sender.sendMessage("Invalid location.");
		return true;
	}

	public boolean checkSafe(int x, int y, int z, World myWorld) {
		// World myWorld = this.plugin.getServer().getWorld("world");
		Block targetBlock = myWorld.getBlockAt(x, y, z);
		Block oneUp = myWorld.getBlockAt(x, y + 1, z);
		Block twoUp = myWorld.getBlockAt(x, y + 2, z);
		Block oneDown = myWorld.getBlockAt(x, y - 1, z);
		if (oneUp.getTypeId() == 0 && twoUp.getTypeId() == 0 && (isFloor(targetBlock.getType()) || isFloor(oneDown.getType()))) {
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

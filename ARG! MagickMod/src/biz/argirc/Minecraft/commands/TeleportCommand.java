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

public class TeleportCommand implements CommandExecutor {

	private static ArrayList<Material>	notFloorBlocks	= new ArrayList<Material>();

	public TeleportCommand() {

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
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 35);
		int x = targetBlock.getX();
		int y = targetBlock.getY();
		int z = targetBlock.getZ();
		if (checkSafe(x, y, z, sender.getServer().getWorld("world"))) {
			Location loc = targetBlock.getLocation();
			double yloc = loc.getY();
			loc.setY(yloc + 1);
			player.sendMessage("Teleporting now...");
			player.teleport(loc);
			return true;
		} else {
			player.sendMessage("It would be unwise to teleport there...");
			return true;
		}

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

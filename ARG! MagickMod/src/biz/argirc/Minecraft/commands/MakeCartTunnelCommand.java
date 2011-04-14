package biz.argirc.Minecraft.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MakeCartTunnelCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;

		int length = Integer.parseInt(args[0]);

		Block target = p.getTargetBlock(null, 5);
		int x = (int) p.getLocation().getPitch();

		Location bl = target.getLocation();

		if (x >= 35) {
			// DOWN
			for (int pos = 0; pos < length; pos++) {
				Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() - pos, bl.getBlockZ()));

				replaceBlock(nb);
			}
		} else if (x > -55) {
			// STRAIGHT
			int dir = (int) p.getLocation().getYaw();
			if (dir < 0) {
				dir *= -1;
			}
			dir %= 360;
			if (dir >= 300 || (dir >= 0 && dir <= 60)) {
				for (int pos = 0; pos <= length; pos++) {
					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() + pos));
					Block nbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + 1, bl.getBlockZ() + pos));
					Block nbbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + 2, bl.getBlockZ() + pos));

					replaceBlock(nb);
					replaceBlock(nbb);
					replaceBlock(nbbb);
				}
			}
			if (dir > 60 && dir <= 120) {
				for (int pos = 0; pos <= length; pos++) {
					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY(), bl.getBlockZ()));
					Block nbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY() + 1, bl.getBlockZ()));
					Block nbbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY() + 2, bl.getBlockZ()));

					replaceBlock(nb);
					replaceBlock(nbb);
					replaceBlock(nbbb);
				}
			}
			if (dir > 120 && dir <= 210) {
				for (int pos = 0; pos <= length; pos++) {
					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() - pos));
					Block nbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + 1, bl.getBlockZ() - pos));
					Block nbbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + 2, bl.getBlockZ() - pos));

					replaceBlock(nb);
					replaceBlock(nbb);
					replaceBlock(nbbb);
				}
			}
			if (dir > 210 && dir <= 300) {
				for (int pos = 0; pos <= length; pos++) {
					Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY(), bl.getBlockZ()));
					Block nbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY() + 1, bl.getBlockZ()));
					Block nbbb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY() + 2, bl.getBlockZ()));

					replaceBlock(nb);
					replaceBlock(nbb);
					replaceBlock(nbbb);
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

	public void replaceBlock(Block b) {
		switch (b.getType()) {
			case CHEST:
			case FURNACE:
			case WORKBENCH:
				return;
			default:
				b.setType(Material.AIR);
		}
	}
}

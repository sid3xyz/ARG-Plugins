package biz.argirc.Minecraft.commands;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WallChunkCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.isOp()) {
			return false;
		}
		Player player = (Player) sender;
		Chunk myChunk = sender.getServer().getWorld("world").getChunkAt(player.getLocation());
		int y = player.getLocation().getBlockY();
		int zconst = 0;
		int xconst = 0;
		for (int i = y; i <= y + 10; i++) {
			for (int x = 0; x <= 15; x++) {
				myChunk.getBlock(x, i, zconst).setType(Material.MOSSY_COBBLESTONE);
				myChunk.getBlock(x, i, 15).setType(Material.MOSSY_COBBLESTONE);
			}
			for (int z = 0; z <= 15; z++) {
				myChunk.getBlock(xconst, i, z).setType(Material.MOSSY_COBBLESTONE);
				myChunk.getBlock(15, i, z).setType(Material.MOSSY_COBBLESTONE);
			}
		}
		return true;
	}
}
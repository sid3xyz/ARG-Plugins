package biz.argirc.Minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegenChunkCommand implements CommandExecutor {
	public final MagickMod	plugin;

	public RegenChunkCommand(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.isOp()) {
			return true;
		}
		Player player = (Player) sender;
		player.getWorld().regenerateChunk(player.getLocation().getBlock().getChunk().getX(), player.getLocation().getBlock().getChunk().getZ());
		player.sendMessage("§7Regenerated chunk at " + player.getLocation().getBlock().getChunk().getX() + "," + player.getLocation().getBlock().getChunk().getZ() + "!");
		System.out.println(player.getName() + " regenerated chunk at " + player.getLocation().getBlock().getChunk().getX() + "," + player.getLocation().getBlock().getChunk().getZ() + "!");
		return true;
	}

}

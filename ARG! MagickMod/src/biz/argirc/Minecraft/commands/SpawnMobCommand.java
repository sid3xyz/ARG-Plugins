package biz.argirc.Minecraft.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

public class SpawnMobCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
				World myWorld = sender.getServer().getWorld("world");
				for (int i = 0; i < creatureCount; i++) {
					myWorld.spawnCreature(loc, CreatureType.valueOf(creatureString));
				}
			}
		}
		return true;
	}

}

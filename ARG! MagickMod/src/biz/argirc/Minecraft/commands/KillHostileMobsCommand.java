package biz.argirc.Minecraft.commands;

import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

public class KillHostileMobsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		World world = sender.getServer().getWorld("world");
		if (sender.isOp()) {
			List<LivingEntity> mobs = world.getLivingEntities();
			for (Iterator<LivingEntity> iterator = mobs.iterator(); iterator.hasNext();) {
				LivingEntity m = iterator.next();
				if (isMonster(m)) {
					m.setHealth(0);
				}
			}
		}
		return true;
	}

	public boolean isMonster(LivingEntity e) {
		return (e instanceof Monster);
	}
}

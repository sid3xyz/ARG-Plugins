package biz.argirc.Minecraft.commands;

import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;

public class KillFarmAnimalsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.isOp()) {
			return false;
		}
		World world = sender.getServer().getWorld("world");
		List<LivingEntity> mobs = world.getLivingEntities();
		for (Iterator<LivingEntity> iterator = mobs.iterator(); iterator.hasNext();) {
			LivingEntity m = iterator.next();
			if (isAnimal(m)) {
				m.setHealth(0);
			}
		}
		return true;
	}

	public boolean isAnimal(LivingEntity e) {
		return (e instanceof Animals);
	}
}

package biz.argirc.Minecraft.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import biz.argirc.Minecraft.MagickMod;

public class DieCommand implements CommandExecutor {

	public final MagickMod	plugin;

	public DieCommand(MagickMod plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		Random rand = new Random();
		int myseed = rand.nextInt(5);

		switch (myseed) {
			case 1:
				player.setFireTicks(9001);
				player.sendMessage("The fire of 9001 suns burns you");
				return true;
			case 2:
				player.damage(9001);
				player.sendMessage("Spontanious Death.");
				return true;
			case 3:
				Location dropme = player.getLocation();
				dropme.setY(125);
				player.teleport(dropme);
				player.sendMessage("Fall from the sky.");
				return true;
			default:
				player.sendMessage("You got lucky...");
				return true;
		}
	}
}

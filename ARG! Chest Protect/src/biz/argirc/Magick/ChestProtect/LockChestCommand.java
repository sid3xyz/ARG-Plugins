package biz.argirc.Magick.ChestProtect;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LockChestCommand implements CommandExecutor {
	private final ChestProtect	plugin;

	public LockChestCommand(ChestProtect plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}

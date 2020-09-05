package org.hassan.riftconfirm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hassan.riftconfirm.gui.PackageMenu;

public class ConfirmCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0){
			Player player= (Player) sender;
			new PackageMenu(player).open();
		}

		return false;
	}
}

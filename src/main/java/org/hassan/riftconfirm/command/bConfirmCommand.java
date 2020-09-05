package org.hassan.riftconfirm.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hassan.riftconfirm.RiftConfirm;
import org.hassan.riftconfirm.packagedata.PackageData;
import org.hassan.riftconfirm.utils.Common;

import java.util.ArrayList;

public class bConfirmCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0){
			Common.sendMessage(sender,"&c/bconfirm send {name} {package} {transaction} {date}-{time}");
		}
		if(args.length == 6){
			if(args[0].equalsIgnoreCase("send")){
				Player target = Bukkit.getPlayer(args[1]);
				if(target != null){
					String name = args[2];
					String trans = args[3];
					String date = args[4];
					String time = args[5];
					time = time.replace(":", "-");
					if(!RiftConfirm.getInstance().getPackageMap().containsKey(name)){
						Common.sendMessage(target,RiftConfirm.getInstance().getConfig().getString("Messages.No-Package"));
						return false;
					}

					if(RiftConfirm.getInstance().dataMap.containsKey(target.getUniqueId().toString())){
						PackageData data = RiftConfirm.getInstance().dataMap.get(target.getUniqueId().toString());
						data.addPackage(name + ":" + trans + ":" + date + ":" + time);
						Common.sendMessage(sender,RiftConfirm.getInstance().getConfig().getString("Messages.Give-Package").replace("{package}", name).replace("{target}", target.getName()));
						Bukkit.getScheduler().runTaskLater(RiftConfirm.getInstance(), () -> Common.sendMessage(target,RiftConfirm.getInstance().getConfig().getString("Messages.Give-Target-Package")), 1 * 20L);
					}else{
						ArrayList<String> pack = new ArrayList<>();
						pack.add(name + ":" + trans + ":" + date + ":" + time);
						RiftConfirm.getInstance().dataMap.put(target.getUniqueId().toString(),new PackageData(target.getDisplayName(),target.getUniqueId().toString(),pack, 0));
						Common.sendMessage(sender,RiftConfirm.getInstance().getConfig().getString("Messages.Give-Package").replace("{package}", name).replace("{target}", target.getName()));
						Bukkit.getScheduler().runTaskLater(RiftConfirm.getInstance(), () -> Common.sendMessage(target,RiftConfirm.getInstance().getConfig().getString("Messages.Give-Target-Package")), 1 * 20L);
					}
				}

			}
		}
		return false;
	}
}

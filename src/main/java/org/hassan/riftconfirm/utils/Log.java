package org.hassan.riftconfirm.utils;

import org.bukkit.entity.Player;
import org.hassan.riftconfirm.packagedata.PackageData;

public class Log {

	public static void logToFile(Player player, PackageData data, String pack, String accept, String trans, String date, String time){
		ConfigManager.getInstance().addLog(ConfigManager.getInstance().getConfig("log.yml"),"logs." + player.getUniqueId().toString() +"." + String.valueOf(data.getAmount()) + ".", pack,accept,trans,date,time, player);
	}
}

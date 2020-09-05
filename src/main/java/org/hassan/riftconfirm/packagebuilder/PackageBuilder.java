package org.hassan.riftconfirm.packagebuilder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hassan.riftconfirm.RiftConfirm;
import org.hassan.riftconfirm.utils.Common;
import org.hassan.riftconfirm.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class PackageBuilder {

	public void buildPackages(){
		for(String pack : RiftConfirm.getInstance().getConfig().getConfigurationSection("Packages").getKeys(false)){

			String material = RiftConfirm.getInstance().getConfig().getString("Packages." + pack + ".material");
			String name = Common.colorMessage(RiftConfirm.getInstance().getConfig().getString("Packages." + pack + ".name"));
			List<String> commands = RiftConfirm.getInstance().getConfig().getStringList("Packages." + pack + ".commands");
			List<String> messages = RiftConfirm.getInstance().getConfig().getStringList("Packages." + pack + ".message");

			ArrayList<String> formattedLore = new ArrayList<>();
			for(String lore : RiftConfirm.getInstance().getConfig().getStringList("Settings.lore")){
				formattedLore.add(Common.colorMessage(lore));
			}

			ItemStack item = new ItemBuilder(Material.matchMaterial(material))
					.setDisplayName(name)
					.setKey("package", pack)
					.build();

			RiftConfirm.getInstance().getPackageMap().put(pack,new Package(name,commands,messages,item));
		}
	}


}

package org.hassan.riftconfirm.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.hassan.riftconfirm.RiftConfirm;
import org.hassan.riftconfirm.packagebuilder.Package;
import org.hassan.riftconfirm.packagedata.PackageData;
import org.hassan.riftconfirm.safenbt.SafeNBT;
import org.hassan.riftconfirm.utils.Common;
import org.hassan.riftconfirm.utils.ItemBuilder;
import org.hassan.riftconfirm.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class PackageMenu extends GUI{


	public PackageMenu(Player player) {
		super(player);
	}

	@Override
	public String getMenuName() {
		return Common.colorMessage(RiftConfirm.getInstance().getConfig().getString("Package-Menu.name"));
	}

	@Override
	public int getSlots() {
		int size = 9;
		if(RiftConfirm.getInstance().dataMap.containsKey(player.getUniqueId().toString())){
			if(RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() <= 9 + 1){
				size = 9;
			}
			if(RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() >= 9 + 1 && RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() <= 18 + 1){
				size = 18;
			}
			if(RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() >= 18 + 1 && RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() <= 27 + 1){
				size = 27;
			}
			if(RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() >= 27 + 1 && RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() <= 36 + 1 ){
				size = 36;
			}
			if(RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() >= 36 + 1 && RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() <= 45 + 1 ){
				size = 45;
			}
			if(RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() >= 45 + 1 && RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() <= 56 + 1 ){
				size = 56;
			}

		}

		return size;
	}

	private void delayMessage(Player player, Package p){
		for(String messages : p.getMessages()){
			Common.sendMessage(player,Common.colorMessage(messages));
		}
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		ItemStack item = e.getCurrentItem();
		SafeNBT nbt = SafeNBT.get(item);

		if(e.getClick() == ClickType.LEFT){
			if(nbt.hasKey("package")){
				String pack = nbt.getString("package");
				String unformatted = nbt.getString("unformatted");
				if(RiftConfirm.getInstance().getPackageMap().containsKey(pack) && RiftConfirm.getInstance().dataMap.containsKey(player.getUniqueId().toString())){
					Package p = RiftConfirm.getInstance().getPackageMap().get(pack);
					for(String commands : p.getCommands()){
						commands = commands.replace("{PLAYER}", player.getName());
						Common.executeConsoleCommand(commands);
					}

					Bukkit.getScheduler().runTaskLater(RiftConfirm.getInstance(), () -> delayMessage(player,p), 1 * 20L);


					String[] s = unformatted.split(":");
					String name = s[0];
					String trams = s[1];
					String date = s[2];
					String time = s[3];
					PackageData data = RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString());
					data.setAmount(data.getAmount() + 1);
					Log.logToFile(player,  RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()), name,"Yes",trams,date,time);
					RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).removePackage(unformatted);
					player.closeInventory();

				}

			}
		}else{
			if(e.getClick() == ClickType.RIGHT){
				if(nbt.hasKey("package")){
					String pack = nbt.getString("package");
					String unformatted = nbt.getString("unformatted");
					String[] s = unformatted.split(":");
					String name = s[0];
					String trams = s[1];
					String date = s[2];
					String time = s[3];
					PackageData data = RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString());
					data.setAmount(data.getAmount() + 1);
					Log.logToFile(player,RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()),name,"No",trams,date,time);
					RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).removePackage(unformatted);
					player.closeInventory();
					Common.sendMessage(player,RiftConfirm.getInstance().getConfig().getString("Messages.Deny-Package"));
				}
			}
		}

	}

	@Override
	public void setMenuItems() {
		if(RiftConfirm.getInstance().dataMap.containsKey(player.getUniqueId().toString())){
			PackageData data = RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString());
			int slot = 0;
			for(String pack : data.getPackages()){

				String[] s = pack.split(":");
				String name = s[0];
				String trams = s[1];
				String date = s[2];
				String time = s[3];

				if(RiftConfirm.getInstance().getPackageMap().containsKey(name)){
					ItemStack item = RiftConfirm.getInstance().getPackageMap().get(name).getItem();

					ArrayList<String> formattedLore = new ArrayList<>();
					for(String lore : RiftConfirm.getInstance().getConfig().getStringList("Settings.lore")){
						lore = lore.replace("{transaction}", trams);
						lore = lore.replace("{NAME}", name);
						formattedLore.add(Common.colorMessage(lore));
					}

					ItemStack formattedItem = new ItemBuilder(item.getType())
							.setDisplayName(Common.colorMessage(item.getItemMeta().getDisplayName()))
							.setLore(formattedLore)
							.setKey("package", name)
							.setKey("unformatted", pack)
							.build();
					if(slot <= 56){
						inventory.setItem(slot,formattedItem);
					}else{
						return;
					}

					slot++;
				}
			}
		}
	}
}

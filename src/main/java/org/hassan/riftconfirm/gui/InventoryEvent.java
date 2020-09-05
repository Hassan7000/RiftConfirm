package org.hassan.riftconfirm.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;
import org.hassan.riftconfirm.RiftConfirm;
import org.hassan.riftconfirm.utils.Common;

public class InventoryEvent implements Listener {



	@EventHandler
	public void onMenuClick(InventoryClickEvent e){

		InventoryHolder holder = e.getInventory().getHolder();
		//If the inventoryholder of the inventory clicked on
		// is an instance of Menu, then gg. The reason that
		// an InventoryHolder can be a Menu is because our Menu
		// class implements InventoryHolder!!
		if (holder instanceof GUI) {
			e.setCancelled(true); //prevent them from fucking with the inventory
			if (e.getCurrentItem() == null) { //deal with null exceptions
				return;
			}
			//Since we know our inventoryholder is a menu, get the Menu Object representing
			// the menu we clicked on
			GUI menu = (GUI) holder;
			//Call the handleMenu object which takes the event and processes it
			menu.handleMenu(e);
		}

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();

		if(RiftConfirm.getInstance().dataMap.containsKey(player.getUniqueId().toString())){
			if(RiftConfirm.getInstance().dataMap.get(player.getUniqueId().toString()).getPackages().size() > 1){
				Common.sendMessage(player,RiftConfirm.getInstance().getConfig().getString("Messages.On-Join"));
			}
		}
	}
}

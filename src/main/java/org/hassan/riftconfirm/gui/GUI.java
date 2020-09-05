package org.hassan.riftconfirm.gui;

import com.sun.scenario.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hassan.riftconfirm.safenbt.SafeNBT;
import org.hassan.riftconfirm.utils.Common;
import org.hassan.riftconfirm.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GUI implements InventoryHolder {


	//Protected values that can be accessed in the menus
	protected Player player;
	protected Inventory inventory;
	protected ItemStack FILLER_GLASS = makeItem(Material.STAINED_GLASS_PANE, (short) 7, " ");

	//Constructor for Menu. Pass in a PlayerMenuUtility so that
	// we have information on who's menu this is and
	// what info is to be transfered
	public GUI(Player player) {
		this.player = player;
	}

	//let each menu decide their name
	public abstract String getMenuName();

	//let each menu decide their slot amount
	public abstract int getSlots();

	//let each menu decide how the items in the menu will be handled when clicked
	public abstract void handleMenu(InventoryClickEvent e);

	//let each menu decide what items are to be placed in the inventory menu
	public abstract void setMenuItems();

	//When called, an inventory is created and opened for the player
	public void open() {
		//The owner of the inventory created is the Menu itself,
		// so we are able to reverse engineer the Menu object from the
		// inventoryHolder in the MenuListener class when handling clicks
		inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

		//grab all the items specified to be used for this menu and add to inventory
		this.setMenuItems();

		//open the inventory for the player
		player.openInventory(inventory);
	}

	//Overridden method from the InventoryHolder interface
	@Override
	public Inventory getInventory() {
		return inventory;
	}

	//Helpful utility method to fill all remaining slots with "filler glass"
	public void setFillerGlass() {
		for (int i = 0; i < getSlots(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}
	}




	public ItemStack makeItem(Material material, short data, String displayName, String... lore) {

		ItemStack item = new ItemStack(material, 1, data);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);

		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);

		return item;
	}

}

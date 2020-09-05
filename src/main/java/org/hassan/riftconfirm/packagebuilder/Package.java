package org.hassan.riftconfirm.packagebuilder;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Package {

	private String packageName;
	private List<String> commands;
	private List<String> messages;
	private ItemStack item;
	public Package(String packageName, List<String> commands, List<String> messages, ItemStack item){
		this.packageName = packageName;
		this.commands = commands;
		this.messages = messages;
		this.item = item;
	}

	public ItemStack getItem() {return item; }
	public String getPackageName() { return packageName; }
	public List<String> getCommands() { return commands; }
	public List<String> getMessages() { return messages; }


}

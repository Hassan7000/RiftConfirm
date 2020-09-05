package org.hassan.riftconfirm;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.hassan.riftconfirm.command.ConfirmCommand;
import org.hassan.riftconfirm.command.bConfirmCommand;
import org.hassan.riftconfirm.gui.InventoryEvent;
import org.hassan.riftconfirm.packagebuilder.Package;
import org.hassan.riftconfirm.packagebuilder.PackageBuilder;
import org.hassan.riftconfirm.packagedata.PackageData;
import org.hassan.riftconfirm.utils.ConfigManager;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RiftConfirm extends JavaPlugin {

	private static RiftConfirm instance;
	public HashMap<String, PackageData> dataMap;
	private HashMap<String, Package> packageMap = new HashMap<>();
	public HashMap<UUID, List<ItemStack>> guiItems = new HashMap<>();
	private PackageBuilder builder;
	public File file = new File(getDataFolder(), "GeneratorData.dat");
	private BukkitTask saveTask;
	@Override
	public void onEnable() {
		instance = this;
		ConfigManager.getInstance().setPlugin(this);
		saveDefaultConfig();
		builder = new PackageBuilder();
		builder.buildPackages();
		getCommand("bconfirm").setExecutor(new bConfirmCommand());
		getCommand("confirm").setExecutor(new ConfirmCommand());
		Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		dataMap = (HashMap<String, PackageData>) load(file);

		if(dataMap == null) {
			dataMap = new HashMap<String, PackageData>();
		}
		saveTask = new SaveTask().runTaskTimer(this,20,3600*20);
	}

	@Override
	public void onDisable() {
		saveTask.cancel();
		if(file.exists()) {
			this.save(file);
		}
	}

	public void save(File f) {
		if(f.exists()) {
			try {
				ObjectOutputStream dataa = new ObjectOutputStream(new FileOutputStream(f));
				dataa.writeObject(dataMap);
				dataa.flush();
				dataa.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Object load(File f) {
		try {
			ObjectInputStream data = new ObjectInputStream(new FileInputStream(f));
			Object result = data.readObject();
			data.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static RiftConfirm getInstance(){
		return instance;
	}

	public HashMap<String, Package> getPackageMap() {return packageMap; }
}

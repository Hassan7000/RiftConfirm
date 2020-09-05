package org.hassan.riftconfirm.packagedata;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.io.Serializable;
import java.util.ArrayList;

public class PackageData implements Serializable {

	private String playerName;
	private String uuid;
	private ArrayList<String> packages;
	private int amount;
	public PackageData(String playerName, String uuid, ArrayList<String> packages, int amount){
		this.playerName = playerName;
		this.uuid = uuid;
		this.packages = packages;
		this.amount = amount;
	}

	public String getPlayerName() {return  playerName; }
	public String getUuid() { return uuid;}
	public int getAmount() {return amount;}
	public ArrayList<String> getPackages() {return packages;}

	public void addPackage(String pack){
		this.packages.add(pack);
	}

	public void removePackage(String pack){
		this.packages.remove(pack);
	}

	public void setAmount(int amount){
		this.amount = amount;
	}
}

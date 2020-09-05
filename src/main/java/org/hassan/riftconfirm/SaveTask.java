package org.hassan.riftconfirm;

import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {
	@Override
	public void run() {
		RiftConfirm.getInstance().save(RiftConfirm.getInstance().file);
	}
}

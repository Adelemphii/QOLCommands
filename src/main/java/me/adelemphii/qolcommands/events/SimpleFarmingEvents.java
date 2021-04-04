package me.adelemphii.qolcommands.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.adelemphii.qolcommands.QOLCommands;

import java.util.Random;

public class SimpleFarmingEvents implements Listener {

	public QOLCommands plugin;
	public SimpleFarmingEvents(QOLCommands plugin) {
		this.plugin = plugin;
	}

	Random random = new Random();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		// If simple-farming is set to true, run the rest of the checks
		if(plugin.getConfig().getBoolean("simple-farming")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block block = event.getClickedBlock();
				if (block.getBlockData() instanceof Ageable) {

					Ageable age = (Ageable) block.getBlockData();
					if (age.getAge() != age.getMaximumAge()) return;
					for (ItemStack i : block.getDrops()) {
						i.setAmount(i.getAmount() + random.nextInt(5));
						block.getWorld().dropItemNaturally(block.getLocation(), i);
					}
					age.setAge(1);
					block.setBlockData(age);
				}
			}
		}
	}
	
}

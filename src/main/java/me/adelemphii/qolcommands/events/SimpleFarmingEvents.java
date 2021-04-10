package me.adelemphii.qolcommands.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.adelemphii.qolcommands.QOLCommands;

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

				// Check if the block has age
				if (block.getBlockData() instanceof Ageable) {
					Ageable age = (Ageable) block.getBlockData();

					// We don't need the block if it isn't max age, so stop doing things
					if (age.getAge() != age.getMaximumAge()) return;

					// Drop a random amount from 1-6 wheat, and many seeds
					for (ItemStack i : block.getDrops()) {
						i.setAmount(i.getAmount() + random.nextInt(6));
						block.getWorld().dropItemNaturally(block.getLocation(), i);
					}
					// Set the age of the crop right-clicked back to 1
					age.setAge(1);
					block.setBlockData(age);
				}
			}

			// Check if the player is breaking the block
			if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
				Block block = event.getClickedBlock();
				ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

				// If the block has age, it's a block we need
				if (block.getBlockData() instanceof Ageable) {
					Ageable age = (Ageable) block.getBlockData();

					// We only need to use this event if they have a hoe in their hand
					if (item.getType().toString().endsWith("HOE")) {

						// No need to continue if the block isn't max age
						if (age.getAge() != age.getMaximumAge()) {
							event.setCancelled(true);
							return;
						}

						// Drop anywhere between 1 and 6 wheat, and default amount of seeds
						for (ItemStack i : block.getDrops()) {
							if (!i.getType().toString().endsWith("SEEDS")) {
								i.setAmount(i.getAmount() + random.nextInt(6));
							}
							block.getWorld().dropItemNaturally(block.getLocation(), i);
						}
						// Cancel the event of the block breaking and set its age to 1
						event.setCancelled(true);
						age.setAge(1);
						block.setBlockData(age);
					}
				}
			}
			// Disable crop trampling if "disable-crop-trample" is true in config
			if(plugin.getConfig().getBoolean("disable-crop-trample")) {
				if (event.getAction() == Action.PHYSICAL) {
					if(event.getClickedBlock().getType() == Material.FARMLAND) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}

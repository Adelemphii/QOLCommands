package me.adelemphii.qolcommands.events;

import java.util.Random;

import org.bukkit.Location;
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
		if (plugin.getConfig().getBoolean("simple-farming")) {
			// If simple-farming is set to true, run the rest of the checks
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block block = event.getClickedBlock();

				// Check if the block has age
				if (block.getBlockData() instanceof Ageable) {
					Ageable age = (Ageable) block.getBlockData();

					// We don't need the block if it isn't max age, so stop doing things
					if (age.getAge() != age.getMaximumAge()) return;

					// Drop a random amount from 1-4 wheat, and many seeds
					// 6 was too high so I lowered it to 4
					for (ItemStack i : block.getDrops()) {

						if(!i.getType().toString().endsWith("SEEDS") &&
								!i.getType().toString().equalsIgnoreCase("POTATO") &&
								!i.getType().toString().equalsIgnoreCase("CARROT")) {

							i.setAmount(i.getAmount() + random.nextInt(4));
						}
						block.getWorld().dropItemNaturally(block.getLocation(), i);
					}
					// Set the age of the crop right-clicked back to 1
					age.setAge(1);
					block.setBlockData(age);
				}
			}

			// Check if the player is breaking the block
			if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				Block block = event.getClickedBlock();
				Location location = block.getLocation();
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

						// Drop anywhere between 1 and 4 wheat, and default amount of seeds
						// 6 was too high so I lowered it to 4
						for (ItemStack i : block.getDrops()) {
							if (!i.getType().toString().endsWith("SEEDS") &&
									!i.getType().toString().equalsIgnoreCase("POTATO") &&
									!i.getType().toString().equalsIgnoreCase("CARROT")) {

								i.setAmount(i.getAmount() + random.nextInt(4));
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
		}
	}
}

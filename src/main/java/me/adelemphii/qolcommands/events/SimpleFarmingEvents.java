package me.adelemphii.qolcommands.events;

import me.adelemphii.qolcommands.QOLCommands;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SimpleFarmingEvents implements Listener {

	public QOLCommands plugin;

	public SimpleFarmingEvents(QOLCommands plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(plugin.getConfig().getBoolean("simple-farming")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block block = event.getClickedBlock();
				if (block.getBlockData() instanceof Ageable) {

					if(block.getType() == Material.MELON_SEEDS || block.getType() == Material.PUMPKIN_SEEDS) return;

					Ageable age = (Ageable) block.getBlockData();
					if (age.getAge() != age.getMaximumAge()) return;
					for (ItemStack i : block.getDrops()) {
						block.getWorld().dropItemNaturally(block.getLocation(), i);
					}
					age.setAge(1);
					block.setBlockData(age);
				}
			}
		}
	}
	
}

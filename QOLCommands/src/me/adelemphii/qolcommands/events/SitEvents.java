package me.adelemphii.qolcommands.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class SitEvents implements Listener {
	
	@EventHandler
	public void onDismount(EntityDismountEvent event) {
		// Check if the entity which was dismounted from is an armorstand
		if(event.getDismounted().getType() == EntityType.ARMOR_STAND) {
			ArmorStand armorStand = (ArmorStand) event.getDismounted();
			// Check that the armorstand's name is "tazed!"
			if(armorStand.getCustomName().equalsIgnoreCase("sitting")) {
				if(event.getEntity() instanceof Player) {
					armorStand.remove();
				}
			}
		}
	}
}

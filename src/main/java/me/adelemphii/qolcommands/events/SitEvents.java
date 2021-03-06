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

			// If the armorstand has the name "sitting", remove it
			// This prevents random armorstands being everywhere.
			if(armorStand.getCustomName().equalsIgnoreCase("sitting")) {
				if(event.getEntity() instanceof Player) {
					armorStand.remove();
				}
			}
		}
	}
}

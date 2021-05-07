package me.adelemphii.qolcommands.events;

import me.adelemphii.qolcommands.QOLCommands;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CropTrampleEvent implements Listener {

    QOLCommands plugin;
    public CropTrampleEvent(QOLCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(plugin.getConfig().getBoolean("disable-crop-trample")) {
            if (event.getAction() == Action.PHYSICAL) {
                if (event.getClickedBlock().getType() == Material.FARMLAND) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

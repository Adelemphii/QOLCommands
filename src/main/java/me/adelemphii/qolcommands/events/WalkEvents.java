package me.adelemphii.qolcommands.events;

import me.adelemphii.qolcommands.QOLCommands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class WalkEvents implements Listener {

    public QOLCommands plugin;
    public WalkEvents(QOLCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event) {

        if(plugin.playerIsWalking.containsKey(event.getPlayer().getUniqueId())) {
            if (event.isSprinting()) {
                plugin.changeWalkSpeed(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {

        if(plugin.playerIsWalking.containsKey(event.getPlayer().getUniqueId())) {
            plugin.playerIsWalking.remove(event.getPlayer().getUniqueId());
            event.getPlayer().setWalkSpeed(.2f);
        }

    }

}

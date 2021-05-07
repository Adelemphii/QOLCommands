package me.adelemphii.qolcommands.events;

import me.adelemphii.qolcommands.QOLCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class BedSleepEvents implements Listener {

    QOLCommands plugin;
    public BedSleepEvents(QOLCommands plugin) {
        this.plugin = plugin;
    }

    HashSet<UUID> playersSleeping = new HashSet<>();

    @EventHandler
    public void onBedEnterEvent(PlayerBedEnterEvent event) {
        if(plugin.getConfig().getBoolean("better-sleep")) {
            // This whole thing goes "haha" and breaks kinda-sorta if essentials.sleepingignored is enabled
            if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
                playersSleeping.add(event.getPlayer().getUniqueId());
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.GOLD + event.getPlayer().getDisplayName() + ChatColor.YELLOW
                        + " is now sleeping!");
                requirements(event.getPlayer().getWorld().getPlayers().size(), event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onBedLeaveEvent(PlayerBedLeaveEvent event) {
        if(plugin.getConfig().getBoolean("better-sleep")) {
            if (playersSleeping.remove(event.getPlayer().getUniqueId())) {
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.GOLD + event.getPlayer().getDisplayName() + ChatColor.YELLOW
                        + " is no longer sleeping!");
            }
        }
    }

    private void requirements(int online, Player player) {
        int configAmount = (int) plugin.getConfig().getDouble("player-sleep-divide-amount");
        if(configAmount <= 0) {
            plugin.getLogger().log(Level.WARNING, "The 'player-sleep-divide-amount' option in config has been set to 0 or less than 0, by default the plugin has set it to 2 meaning 1/2 of the people on the server need to sleep.");
            configAmount = 2;
        }

        int amountRequired = (Math.floorDiv(online, configAmount) + 1) - (playersSleeping.size() + 1);

        if(playersSleeping.size() >= Math.floorDiv(online, configAmount)) {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "Enough people are sleeping, the night has been skipped!");
            player.getWorld().setTime(0);
        } else {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.GOLD + playersSleeping.size()
                    + ChatColor.YELLOW + " out of " + ChatColor.GOLD + online + ChatColor.YELLOW + " are sleeping!" + ChatColor.GOLD
                    + " (" + amountRequired + " more player(s) are required to sleep in order to skip the night!)");
        }
    }
}

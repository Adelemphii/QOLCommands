package me.adelemphii.qolcommands;

import me.adelemphii.qolcommands.commands.*;
import me.adelemphii.qolcommands.events.WalkEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.adelemphii.qolcommands.events.SimpleFarmingEvents;
import me.adelemphii.qolcommands.events.SitEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QOLCommands extends JavaPlugin {

	/*

		QOLCommands maintained by Adelemphii
		Github: https://github.com/Adelemphii/QOLCommands
		Contributors: lucyy-mc (1-time contributor)

	 */

	public Map<UUID, Boolean> playerIsWalking = new HashMap<>();

	@Override
	public void onEnable() {
		registerCE();
		
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
	}

	@Override
	public void onDisable() {
		this.resetPlayerSpeed();
	}

	// Register commands and events here
	public void registerCE() {
		PluginManager pluginManager = this.getServer().getPluginManager();
		
		this.getCommand("sit").setExecutor(new SitCommand());
		this.getCommand("roll").setExecutor(new LocalRollCommand(this));
		this.getCommand("qolreload").setExecutor(new ConfigReloadCommand(this));
		this.getCommand("broadcastroll").setExecutor(new BroadcastRollCommand());
		this.getCommand("carry").setExecutor(new CarryCommand(this));
		this.getCommand("walk").setExecutor(new WalkCommand(this));
		
		pluginManager.registerEvents(new SitEvents(), this);
		pluginManager.registerEvents(new SimpleFarmingEvents(this), this);
		pluginManager.registerEvents(new WalkEvents(this), this);
	}

	// If the player is in /walk already, remove them and set their speed to default (default is .2f)
	// Else, add them to playerIsWalking and set their walk speed to .1f
	public void changeWalkSpeed(Player player) {
		if(playerIsWalking.containsKey(player.getUniqueId())) {
			player.setWalkSpeed(.2f);
			playerIsWalking.remove(player.getUniqueId());
			player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You are no longer walking at a leisurely pace.");
		} else {
			player.setWalkSpeed(.1f);
			playerIsWalking.put(player.getUniqueId(), true);
			player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You are now walking at a leisurely pace.");
		}
	}

	// When the server goes offline (or the plugin is disabled), reset everyone in the playerIsWalking hashmap to default (.2f) speed.
	public void resetPlayerSpeed() {
		for(UUID uuid : this.playerIsWalking.keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			player.setWalkSpeed(.2f);
		}

	}
	
}

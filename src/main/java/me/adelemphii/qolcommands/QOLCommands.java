package me.adelemphii.qolcommands;

import me.adelemphii.qolcommands.commands.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.adelemphii.qolcommands.events.SimpleFarmingEvents;
import me.adelemphii.qolcommands.events.SitEvents;

public class QOLCommands extends JavaPlugin {
	
	@Override
	public void onEnable() {
		registerCE();
		
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
	}

	@Override
	public void onDisable() {

	}

	// Register commands and events here
	public void registerCE() {
		PluginManager pluginManager = this.getServer().getPluginManager();
		
		this.getCommand("sit").setExecutor(new SitCommand());
		this.getCommand("roll").setExecutor(new LocalRollCommand(this));
		this.getCommand("qolreload").setExecutor(new ConfigReloadCommand(this));
		this.getCommand("broadcastroll").setExecutor(new BroadcastRollCommand());
		this.getCommand("carry").setExecutor(new CarryCommand(this));
		
		pluginManager.registerEvents(new SitEvents(), this);
		pluginManager.registerEvents(new SimpleFarmingEvents(this), this);
	}
	
}

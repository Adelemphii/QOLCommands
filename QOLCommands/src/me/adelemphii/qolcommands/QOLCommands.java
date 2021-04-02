package me.adelemphii.qolcommands;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
	
	public void registerCE() {
		PluginManager pluginManager = this.getServer().getPluginManager();
		
		this.getCommand("sit").setExecutor(new SitCommand());
		this.getCommand("roll").setExecutor(new RollCommand(this));
		this.getCommand("qolreload").setExecutor(new ConfigReloadCommand(this));
		
		pluginManager.registerEvents(new SitEvents(), this);
	}
	
}

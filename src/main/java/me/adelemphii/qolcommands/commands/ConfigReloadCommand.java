package me.adelemphii.qolcommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.adelemphii.qolcommands.QOLCommands;

public class ConfigReloadCommand implements CommandExecutor {
	
	public QOLCommands plugin;
	
	public ConfigReloadCommand(QOLCommands plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender.hasPermission("qolcommands.admin") || sender.isOp()) {
			if(cmd.getName().equalsIgnoreCase("qolreload")) {
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.DARK_RED + "[QOLCommands] " + ChatColor.RED + "" + ChatColor.ITALIC + "Config Reloaded!");
				return true;
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to do that!");
			return false;
		}
		
		return false;
	}
	
}

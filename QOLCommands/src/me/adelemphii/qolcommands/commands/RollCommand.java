package me.adelemphii.qolcommands.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.adelemphii.qolcommands.QOLCommands;

public class RollCommand implements CommandExecutor {
	
	public QOLCommands plugin;
	
	public RollCommand(QOLCommands plugin) {
		this.plugin = plugin;
	}
	
	int input;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// THIS CODE IS SO FUCKING UGLY I'M GOING TO SCREAM
		
		if(cmd.getName().equalsIgnoreCase("roll")) {
			if(args.length == 1) {
				if(isNum(args[0])) {
					
					if(Integer.parseInt(args[0]) < 0) {
						sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You can only roll positive numbers!");
						return false;
					}
					
					input = Integer.parseInt(args[0]);
					int min = 1;
					Random rand = new Random();
					
					// Get a random number from 0 to (input+1)
					int randomNumber = rand.nextInt((input - min) + 1) + min;
					
					if(plugin.getConfig().getBoolean("broadcast-rolls")) {
						if(sender instanceof Player) {
							Player player = (Player) sender;
							
							Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.YELLOW + player.getDisplayName() + ChatColor.YELLOW + " (" + player.getName() + ")"
													+ " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
													+ ChatColor.GOLD + input + ChatColor.YELLOW);
						} else {
							Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.YELLOW + sender.getName()
													+ " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
													+ ChatColor.GOLD + input + ChatColor.YELLOW);
						}
					} else {
						if(sender instanceof Player) {
							Player player = (Player) sender;
							
							checkNearbyEntities(player, randomNumber);
						} else {
							Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.YELLOW + sender.getName()
													+ " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
													+ ChatColor.GOLD + input + ChatColor.YELLOW);
						}
					}
					
					return true;
				} else {
						sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a valid number!");
					return false;
				}
			}
		}
		
		return false;
	}
	
	public boolean isNum(String num) {
		try {
			Integer.parseInt(num);
			} catch (Exception e) {
				return false;
			}
			return true;
	}
	
	public Player checkNearbyEntities(Player roller, int randomNumber) {
		
		int radius = plugin.getConfig().getInt("local-roll-range");
		Location playerLocation = roller.getLocation();

		for (Entity entity : playerLocation.getWorld().getEntities()) {
			
			if(entity instanceof Player) {
				Player player = (Player) entity;
				if (playerLocation.distanceSquared(player.getLocation()) <= Math.pow(radius, 2)) {
					player.sendMessage(ChatColor.YELLOW + roller.getDisplayName() + ChatColor.YELLOW + " (" + roller.getName() + ")"
									+ " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
									+ ChatColor.GOLD + input + ChatColor.YELLOW);
				}
			}
		}
		return null;
	}
	
}

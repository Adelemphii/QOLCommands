package me.adelemphii.qolcommands.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.adelemphii.qolcommands.QOLCommands;

public class LocalRollCommand implements CommandExecutor {
	
	public QOLCommands plugin;
	public LocalRollCommand(QOLCommands plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// Check if CommandSender has permission
		if(sender.hasPermission("qolcommands.roll") || sender.isOp()) {
			if (cmd.getName().equalsIgnoreCase("roll")) {

				if (args.length == 1) {

					// Make sure /roll <input> is a number
					if (isNum(args[0])) {

						// If the <input> is less than or equal to zero, give an error.
						if (Integer.parseInt(args[0]) <= 0) {
							sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You can only roll positive numbers!");
							return false;
						}

						int input = Integer.parseInt(args[0]);
						int min = 1;
						Random rand = new Random();

						// Get a random number from 0 to (input+1)
						int randomNumber = rand.nextInt((input - min) + 1) + min;

						// Check if the sender is a player, if so,
						// then broadcast with display name.
						// If not, broadcast without.
						if (sender instanceof Player) {
							Player player = (Player) sender;

							getNearbyPlayers(player, randomNumber, input);
						} else {
							Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.YELLOW + sender.getName()
									+ " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
									+ ChatColor.GOLD + input + ChatColor.YELLOW);
						}
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a valid number!");
						return false;
					}

					return true;
				}
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /roll!");
		}
		
		return false;
	} // end of onCommand

	// Checks if input is a number
	public boolean isNum(String num) {
		try {
			Integer.parseInt(num);
			} catch (Exception e) {
				return false;
			}
			return true;
	}

	// The method name explains it, just gets players within radius.
	public void getNearbyPlayers(Player roller, int randomNumber, int input) {
		
		int radius = plugin.getConfig().getInt("local-roll-range");
		Location playerLocation = roller.getLocation();

		for (Player player : playerLocation.getWorld().getPlayers()) {
			if (playerLocation.distanceSquared(player.getLocation()) <= Math.pow(radius, 2)) {
				player.sendMessage(ChatColor.YELLOW + roller.getDisplayName() + ChatColor.YELLOW + " (" + roller.getName() + ")"
						+ " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
						+ ChatColor.GOLD + input + ChatColor.YELLOW);
			}
		}
	}
	
}

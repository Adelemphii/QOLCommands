package me.adelemphii.qolcommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class BroadcastRollCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Check if CommandSender has permission
        if(sender.hasPermission("qolcommands.roll.broadcast") || sender.isOp()) {
            if (cmd.getName().equalsIgnoreCase("broadcastroll") || cmd.getLabel().equalsIgnoreCase("broll")) {
                if (args.length == 1) {
                    // Make sure /broadcastroll <input> is a number
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

                            Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.YELLOW + player.getDisplayName() + ChatColor.YELLOW + " (" + player.getName() + ")"
                                    + " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
                                    + ChatColor.GOLD + input + ChatColor.YELLOW);
                        } else {
                            Bukkit.broadcastMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.YELLOW + sender.getName()
                                    + " has rolled a " + ChatColor.GOLD + randomNumber + ChatColor.YELLOW + " out of "
                                    + ChatColor.GOLD + input + ChatColor.YELLOW);
                        }
                        return true;

                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a valid number!");
                        return false;
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /broadcastroll!");
            return false;
        }
        return false;
    }

    // Checks if the input is a number.
    public boolean isNum(String number) {
        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}


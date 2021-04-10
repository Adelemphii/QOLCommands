package me.adelemphii.qolcommands.commands;

import me.adelemphii.qolcommands.QOLCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WalkCommand implements CommandExecutor {

    public QOLCommands plugin;

    public WalkCommand(QOLCommands plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("qolcommands.walk") || sender.isOp()) {
            if (cmd.getName().equalsIgnoreCase("walk")) {
                if (!(sender instanceof Player)) return false;
                Player player = (Player) sender;
                UUID playerUUID = player.getUniqueId();

                // For putting custom walk speeds
                if(args.length >= 1) {

                    if(isNum(args[0])) {
                        float input = Float.parseFloat(args[0]);

                        // Default walkspeed is .2f, so don't let players go over
                        // No point in going under 0 since 0 immobilizes you
                        if(input <= .2f && input >= 0) {
                            plugin.changeWalkSpeed(player, input);
                            return true;
                        } else if(input < 0) {
                            player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That number is too small! (Choose a number between 0 and .2)");
                            return false;
                        } else if(input > .2f) {
                            player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That number is too large! (Choose a number between 0 and .2)");
                            return false;
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a number! (Choose a number between 0 and .2)");
                        return false;
                    }

                    return true;
                }

                plugin.changeWalkSpeed(player);

                return true;
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /walk!");
            return false;
        }
        return false;
    }

    // Checks if input is a number
    public boolean isNum(String num) {
        try {
            Float.parseFloat(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

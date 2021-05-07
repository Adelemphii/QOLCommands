package me.adelemphii.qolcommands.commands;

import me.adelemphii.qolcommands.QOLCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    public QOLCommands plugin;
    public HelpCommand(QOLCommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("qolcommands")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&f------- &aQOLCommands &f------- \n" +
                    "&7/qolcommands &8- &7Shows the currently displayed text! \n" +
                    "&7/sit &8- &7Allows the player to sit where they stand! \n" +
                    "&7/roll &8- &7Rolls a dice locally! \n" +
                    "&7/broll &8- &7Rolls a dice for everyone to see! \n" +
                    "&7/carry &8- &7Allows the player to carry another player! \n" +
                    "&7/walk &8- &7Allows the player to walk at a slower pace than normal! \n" +
                    "&7/walk <speed> &8- &7Allows the player to walk at a custom speed between 0 and .2! \n" +
                    "&7/qolreload &8- &7Reloads the config file! (Dev Only)"));
            return true;
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /qolcommands!");
        }

        return false;
    }
}

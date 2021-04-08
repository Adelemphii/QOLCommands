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

                plugin.changeWalkSpeed(player);
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /walk!");
            return false;
        }
        return false;
    }

}

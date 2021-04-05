package me.adelemphii.qolcommands.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.adelemphii.qolcommands.QOLCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CarryCommand implements CommandExecutor {

    public QOLCommands plugin;

    public CarryCommand(QOLCommands plugin) {
        this.plugin = plugin;
    }

    Map<UUID, Long> confirmation = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("qolcommands.carry")) {
            if (cmd.getName().equalsIgnoreCase("carry")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only players can carry other players.");
                    return false;
                }

                Player player = (Player) sender;

                if (args.length == 0) {
                    player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "Usage: /carry <player>");
                    return false;
                }

                if (args.length == 1) {

                    try {
                        // Prevent the player from carrying themselves (IF THEY MANAGE TO CARRY THEMSELVES, IT CRASHES THE SERVER!)
                        if (Bukkit.getPlayer(args[0]) == player) {
                            player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You cannot /carry yourself.");
                            return false;
                        } else {
                            Player target = Bukkit.getPlayer(args[0]);
                            // Check if the target in range is riding an armorstand named 'tazed!', if so,
                            // make the target ride the player
                            // If not, ask the target to confirm
                            if (checkNearbyPlayers(player, target) != null) {

                                // If their UUID is not in the confirmation hashmap, add it and set their timer.
                                // Else, if it is already in it, remove their current timer and add a new one.
                                if (!confirmation.containsKey(target.getUniqueId())) {
                                    player.sendMessage(ChatColor.RED + "You have requested to carry " + target.getDisplayName());
                                    target.sendMessage(ChatColor.RED + player.getDisplayName() + ChatColor.RED + " has requested to carry you!");
                                    target.sendMessage(ChatColor.RED + "You have 10 seconds, type '/carry confirm <player>' to accept!");
                                    confirmation.put(target.getUniqueId(), System.currentTimeMillis());
                                } else {
                                    confirmation.remove(target.getUniqueId());
                                    player.sendMessage(ChatColor.RED + "You have requested to carry " + target.getDisplayName());
                                    target.sendMessage(ChatColor.RED + player.getDisplayName() + " has requested to carry you!");
                                    target.sendMessage(ChatColor.RED + "You have 10 seconds, type '/carry confirm <player>' to accept!");
                                    confirmation.put(target.getUniqueId(), System.currentTimeMillis());
                                }

                            } else {
                                player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "Either that is not a valid player, or they are not in range! (Psst: Range for /carry is 5 blocks)");
                            }
                            return true;
                        }
                    } catch (NullPointerException ne) {
                        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a valid player.");
                        return false;
                    }
                }
                // To confirm a /carry request on someone.
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("confirm")) {
                        try {
                            // Check if the person is doing /carry confirm <themself>, if so, deny them.
                            // Else, check if they have a cooldown.
                            if (Bukkit.getPlayer(args[1]) == player) {
                                player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You cannot /carry confirm yourself!");
                                player.sendMessage(ChatColor.RED + "Usage: /carry confirm <player>");
                                return true;
                            } else {
                                if (Bukkit.getPlayer(args[1]) != null) {
                                    Player target = Bukkit.getPlayer(args[1]);
                                    checkCooldown(target, player);
                                    return true;
                                } else {
                                    // If args[1] is not a player, return false
                                    player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a valid player.");
                                    return false;
                                }
                            }

                        } catch (NullPointerException ne) {
                            player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a valid player.");
                            return false;
                        }
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /carry!");
            return false;

        }

        return false;
    } // end of onCommand


    // This method is useful for checking if the target is within range of the player
    public Player checkNearbyPlayers(Player player, Player target) {

        Location playerLocation = player.getLocation();

        // For every entity within the world, check if it is within the radius of the player.
        for(Entity entity : playerLocation.getWorld().getPlayers()) {
            int radius = 5;

            if(playerLocation.distanceSquared(entity.getLocation()) <= Math.pow(radius, 2)) {
                // If the entity has the same name as the target, return that entity.
                if(entity.getName().equalsIgnoreCase(target.getName())) {
                    return (Player) entity;
                }
            }
        }
        return null;
    }

    // This method checks the cooldown of the player in the "confirmation" hashmap.
    // If the setTime (the Long in the hashmap) subtracted from the currentTime divided by 1000 (to get it in seconds)
    // is more than 10 seconds, then deny them to taking too long.
    // Else, add the target as a passenger to the player and send messages.
    public void checkCooldown(Player player, Player target) {
        if(confirmation.containsKey(target.getUniqueId())) {
            Long setTime = confirmation.get(target.getUniqueId()) / 1000;
            Long currentTime = System.currentTimeMillis() / 1000;
            if((currentTime - setTime) >= 15) {
                confirmation.remove(target.getUniqueId());
                target.sendMessage(ChatColor.RED + "You waited too long to confirm, have them run the /carry command again.");
            } else {
                player.addPassenger(target);
                player.sendMessage(ChatColor.GREEN + "You have lifted " + target.getDisplayName() + ChatColor.GREEN + "!");
                target.sendMessage(ChatColor.GREEN + "You have been lifted by " + player.getDisplayName() + ChatColor.GREEN + " without force!");
                confirmation.remove(target.getUniqueId());
            }
        } else {
            target.addPassenger(player);
            player.sendMessage(ChatColor.GREEN + "You have lifted " + target.getDisplayName() + ChatColor.GREEN + "!");
            target.sendMessage(ChatColor.GREEN + "You have been lifted by " + player.getDisplayName() + ChatColor.GREEN + " without force!");
        }
    }

}

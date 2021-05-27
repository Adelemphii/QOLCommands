package me.adelemphii.qolcommands.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CarryCommandRewrite implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("[!] QOLCommands: Only players can carry other players.");
            return false;
        }
        Player player = (Player) sender;

        if(sender.hasPermission("qolcommands.carry")) {
            if (cmd.getName().equalsIgnoreCase("carry")) {

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
                                player.sendMessage(ChatColor.GREEN + "You have requested to carry " + target.getDisplayName());
                                TextComponent message = new TextComponent(player.getDisplayName() + " has requested to carry you!");
                                message.setColor(ChatColor.GREEN);

                                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/carry confirm " + player.getName()));
                                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        new Text(ChatColor.GREEN + "Click here to accept the carry request!")));
                                target.spigot().sendMessage(message);
                                return true;
                            } else {
                                player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That player is not in range.");
                            }
                        }
                    } catch (NullPointerException e) {
                        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "That is not a valid player.");
                        return false;
                    }
                }
            }

            // To confirm a /carry request on someone.
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("confirm")) {
                    try {
                        // Check if the person is doing /carry confirm <themself>, if so, deny them.
                        // Else, check if they have a cooldown.
                        if (Bukkit.getPlayer(args[1]) == player) {
                            player.sendMessage(org.bukkit.ChatColor.DARK_RED + "[!] " + org.bukkit.ChatColor.RED + "" + org.bukkit.ChatColor.ITALIC + "You cannot /carry confirm yourself!");
                            player.sendMessage(org.bukkit.ChatColor.RED + "Usage: /carry confirm <player>");
                            return true;
                        } else {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                assert target != null;
                                target.addPassenger(player);
                                target.sendMessage(org.bukkit.ChatColor.GREEN + "You have lifted " + player.getDisplayName() + org.bukkit.ChatColor.GREEN + "!");
                                player.sendMessage(org.bukkit.ChatColor.GREEN + "You have been lifted by " + player.getDisplayName() + org.bukkit.ChatColor.GREEN + " without force!");
                                return true;
                            } else {
                                // If args[1] is not a player, return false
                                player.sendMessage(org.bukkit.ChatColor.DARK_RED + "[!] " + org.bukkit.ChatColor.RED + "" + org.bukkit.ChatColor.ITALIC + "That is not a valid player.");
                                return false;
                            }
                        }

                    } catch (NullPointerException ne) {
                        player.sendMessage(org.bukkit.ChatColor.DARK_RED + "[!] " + org.bukkit.ChatColor.RED + "" + org.bukkit.ChatColor.ITALIC + "That is not a valid player.");
                        return false;
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /carry!");
            return false;
        }

        return false;
    }

    public Player checkNearbyPlayers(Player player, Player target) {

        Location playerLocation = player.getLocation();

        // For every entity within the world, check if it is within the radius of the player.
        for(Player targetC : playerLocation.getWorld().getPlayers()) {
            int radius = 5;

            if(playerLocation.distanceSquared(targetC.getLocation()) <= Math.pow(radius, 2)) {
                // If the entity has the same name as the target, return that entity.
                if(targetC.getName().equalsIgnoreCase(target.getName())) {
                    return targetC;
                }
            }
        }
        return null;
    }
}

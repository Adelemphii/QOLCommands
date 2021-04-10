package me.adelemphii.qolcommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SitCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// Checks if sender has the permission
		if(sender.hasPermission("qolcommands.sit") || sender.isOp()) {
			if (cmd.getName().equalsIgnoreCase("sit")) {

				if(!(sender instanceof Player)) {
					sender.sendMessage("[!] QOLCommands: Only players can /sit!");
					return false;
				}
				Player player = (Player) sender;

				if (player.isOnGround()) {
					World world = player.getWorld();
					ArmorStand as = (ArmorStand) world.spawnEntity(player.getLocation().subtract(0, 1.75, 0), EntityType.ARMOR_STAND);

					as.addPassenger(player);
					as.setInvisible(true);
					as.setCollidable(false);
					as.setInvulnerable(true);
					as.setGravity(false);
					as.setCustomName("sitting");
					as.setCustomNameVisible(false);

					return true;
				} else {
					player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You cannot sit on air!");
					return false;
				}
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "" + ChatColor.ITALIC + "You do not have permission to use /sit!");
			return false;
		}

		return false;
	}

	
}

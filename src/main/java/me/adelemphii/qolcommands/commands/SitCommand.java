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

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("Players only!");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("sit")) {
			if(player.isOnGround()) {
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
		
		return false;
	}
	
}

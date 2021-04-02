package me.adelemphii.qolcommands;

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
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("sit")) {
			World world = player.getWorld();
			ArmorStand as = (ArmorStand) world.spawnEntity(player.getLocation().subtract(0, 1.75, 0), EntityType.ARMOR_STAND);
			
    		as.addPassenger(player);
    		as.setInvisible(true);
    		as.setCollidable(false);
    		as.setGravity(false);
    		as.setCustomName("sitting");
    		as.setCustomNameVisible(false);
		}
		
		return false;
	}
	
}

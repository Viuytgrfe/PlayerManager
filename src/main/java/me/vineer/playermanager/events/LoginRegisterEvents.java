package me.vineer.playermanager.events;
import me.vineer.playermanager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class LoginRegisterEvents implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        int x_player = player.getLocation().getBlockX();
        int y_player = player.getLocation().getBlockY();
        int z_player = player.getLocation().getBlockZ();
        boolean can_use = false;
        if(x_player > -5 && x_player < 5) {
            if(y_player < 17 && y_player > 10) {
                if(z_player < 5 && z_player > -5) {
                    can_use = true;
                }
            }
        }
        if(can_use) {
            if(!((event.getMessage().toLowerCase().startsWith("/register ") || event.getMessage().toLowerCase().startsWith("/login ")) || (event.getMessage().toLowerCase().startsWith("/reg ") || event.getMessage().toLowerCase().startsWith("/l ")))) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Невозможно использовать команду! сначало зарегистрируйтесть или авторизируйтесть");
                event.setCancelled(true);
            }
        } else {
            if(((event.getMessage().toLowerCase().startsWith("/register ") || event.getMessage().toLowerCase().startsWith("/login ")) || (event.getMessage().toLowerCase().startsWith("/reg ") || event.getMessage().toLowerCase().startsWith("/l ")))) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Зачем ты это пишешь? ты уже" + ChatColor.AQUA + " НА СЕРВЕРЕ!");
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Зарегистрируйтесь с помощью команды " + ChatColor.GOLD + "/register" + ChatColor.GREEN + " или авторизуйтесь с помощью команды " + ChatColor.GOLD + "/login");
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 13, 0.5));
            }
        }.runTaskLater(PlayerManager.getPlugin(), 1L);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        int x_player = player.getLocation().getBlockX();
        int y_player = player.getLocation().getBlockY();
        int z_player = player.getLocation().getBlockZ();
        boolean can_use = false;
        if(x_player > -5 && x_player < 5) {
            if(y_player < 17 && y_player > 10) {
                if(z_player < 5 && z_player > -5) {
                    can_use = true;
                }
            }
        }
        event.setCancelled(can_use);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        int x_player = player.getLocation().getBlockX();
        int y_player = player.getLocation().getBlockY();
        int z_player = player.getLocation().getBlockZ();
        boolean can_use = false;
        if(x_player > -5 && x_player < 5) {
            if(y_player < 17 && y_player > 10) {
                if(z_player < 5 && z_player > -5) {
                    can_use = true;
                }
            }
        }
        event.setCancelled(can_use);
    }
}

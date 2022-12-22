package me.vineer.playermanager.events;

import me.vineer.playermanager.permissions.Permissions;
import me.vineer.playermanager.permissions.PlayerPermission;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HandEvents implements Listener {
    @EventHandler
    public void PlayerClick(PlayerInteractEvent event) {
        if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if(event.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.ENCHANTED_BOOK) && !event.getPlayer().getInventory().getItemInMainHand().equals(Material.AIR)) {
                if(PlayerPermission.hasPermission(event.getPlayer().getName(), Permissions.ENCHANT_FROM_HAND)) {
                    event.getPlayer().getInventory().getItemInMainHand().addUnsafeEnchantments(event.getPlayer().getInventory().getItemInOffHand().getEnchantments());
                    if(event.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasEnchants()) {
                        event.getPlayer().getInventory().getItemInOffHand().setAmount(0);
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "У тебя недостаточно прав, чтобы соединить зачарования!");
                }

            }
        }

    }
}

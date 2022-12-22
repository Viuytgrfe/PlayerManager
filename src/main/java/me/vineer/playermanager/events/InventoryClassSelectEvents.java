package me.vineer.playermanager.events;

import me.vineer.playermanager.Database;
import me.vineer.playermanager.PlayerClass;
import me.vineer.playermanager.PlayerManager;
import me.vineer.playermanager.inventories.ClassSelectGUI;
import me.vineer.playermanager.inventories.GuiInventoryCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.potion.PotionEffectTypeWrapper;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryClassSelectEvents implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof GuiInventoryCreator && !((GuiInventoryCreator) event.getInventory().getHolder()).can_close) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(event.getInventory());
                }
            }.runTaskLater(PlayerManager.getPlugin(), 1L);

        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) { return; }
        if(event.getClickedInventory().getHolder() instanceof GuiInventoryCreator && !((GuiInventoryCreator) event.getClickedInventory().getHolder()).can_move) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if(event.getCurrentItem() == null){ return; }
            if(event.getClickedInventory().getHolder() instanceof ClassSelectGUI) {
                if (event.getCurrentItem().getType() == Material.BLUE_ICE) {
                    try {
                        PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET class = ? WHERE Name = ?");
                        ps.setString(1, PlayerClass.ATLANT_CLASS);
                        ps.setString(2, event.getWhoClicked().getName());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Был выбран класс: " + ChatColor.AQUA + "Атлант");
                    PlayerClass.setPlayerClass(player, PlayerClass.ATLANT_CLASS);
                    event.getWhoClicked().addPotionEffect(PotionEffectTypeWrapper.CONDUIT_POWER.createEffect(Integer.MAX_VALUE, 0));
                    ((ClassSelectGUI) event.getInventory().getHolder()).setCan_close(true);
                    player.closeInventory();
                } else if(event.getCurrentItem().getType() == Material.MAGMA_BLOCK) {
                    try {
                        PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET class = ? WHERE Name = ?");
                        ps.setString(1, PlayerClass.FIRE_CLASS);
                        ps.setString(2, event.getWhoClicked().getName());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Был выбран класс: " + ChatColor.YELLOW + "Спичка");
                    PlayerClass.setPlayerClass(player, PlayerClass.FIRE_CLASS);
                    event.getWhoClicked().addPotionEffect(PotionEffectTypeWrapper.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, 0));
                    ((ClassSelectGUI) event.getInventory().getHolder()).setCan_close(true);
                    player.closeInventory();
                } else if(event.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                    ((ClassSelectGUI) event.getInventory().getHolder()).setCan_close(true);
                    player.closeInventory();
                } else if (event.getCurrentItem().getType() == Material.IRON_AXE) {
                    try {
                        PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET class = ? WHERE Name = ?");
                        ps.setString(1, PlayerClass.BERSERK_CLASS);
                        ps.setString(2, event.getWhoClicked().getName());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Был выбран класс: " + ChatColor.RED + "Берсерк");
                    PlayerClass.setPlayerClass(player, PlayerClass.BERSERK_CLASS);
                    ((ClassSelectGUI) event.getInventory().getHolder()).setCan_close(true);
                    player.closeInventory();
                } else if (event.getCurrentItem().getType() == Material.CROSSBOW) {
                    try {
                        PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET class = ? WHERE Name = ?");
                        ps.setString(1, PlayerClass.BERSERK_CLASS);
                        ps.setString(2, event.getWhoClicked().getName());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Был выбран класс: " + ChatColor.YELLOW + "Охотник");
                    PlayerClass.setPlayerClass(player, PlayerClass.HUNTER_CLASS);
                    ((ClassSelectGUI) event.getInventory().getHolder()).setCan_close(true);
                    player.closeInventory();
                }
            }
        }
    }
}

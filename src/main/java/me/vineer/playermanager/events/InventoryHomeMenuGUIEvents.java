package me.vineer.playermanager.events;

import me.vineer.playermanager.Database;
import me.vineer.playermanager.PlayerManager;
import me.vineer.playermanager.inventories.GuiInventoryCreator;
import me.vineer.playermanager.inventories.HomeMenuGUI;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryHomeMenuGUIEvents implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try {
            if(!(event.getView().getTitle().equals(ChatColor.UNDERLINE + "Точки дома") && event.getClickedInventory() != null && event.getClickedInventory().getHolder() != null)) return;
        } catch (Exception e) {
            return;
        }
        if(!((GuiInventoryCreator) event.getClickedInventory().getHolder()).can_move)event.setCancelled(true);
        int slot = event.getRawSlot();
        Audience audience = PlayerManager.getPlugin().adventure().sender(event.getWhoClicked());
        try {
            if(event.getClickedInventory().getItem(slot) != null && event.getClickedInventory().getItem(slot).getItemMeta().getLore() != null && event.getClickedInventory().getItem(slot).getItemMeta().getLore().get(0).equals(ChatColor.RED + "" + ChatColor.BOLD + "Слот заблокирован")) {
                player.sendMessage(ChatColor.RED + "Этот слот заблокирован, вы не можете им воспользоваться!");
            } else if(event.getClickedInventory().getItem(slot) != null && event.getClickedInventory().getItem(slot).getItemMeta().getLore().get(0).equals(ChatColor.GREEN + "Точка дома установлена")) {
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM Homes WHERE Name = ? and slot = ?");
                    ps.setString(1, player.getName());
                    ps.setInt(2, slot);
                    ResultSet rs = ps.executeQuery();
                    World world = Bukkit.getWorld(rs.getString("WORLD"));
                    double x = rs.getInt("X") + 0.5;
                    double y = rs.getDouble("Y");
                    double z = rs.getInt("Z") + 0.5;
                    Location location = new Location(world, x, y, z, (player).getLocation().getYaw(), (player).getLocation().getPitch());
                    PlayerManager.teleport(audience, player, location);
                    player.closeInventory();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (event.getClickedInventory().getItem(slot) != null && event.getClickedInventory().getItem(slot).getItemMeta().getLore().get(0).equals(ChatColor.RED + "Точка дома не установлена")) {
                int x = (player).getLocation().getBlockX();
                double y = (player).getLocation().getY();
                int z = (player).getLocation().getBlockZ();
                String world = (player).getWorld().getName();
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO Homes(Name, X, Y, Z, WORLD, Home_name, slot) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, player.getName());
                    ps.setInt(2, x);
                    ps.setDouble(3, y);
                    ps.setInt(4, z);
                    ps.setString(5, world);
                    ps.setString(6, "home" + slot);
                    ps.setInt(7, slot);
                    ps.executeUpdate();
                    player.closeInventory();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Точка дома home" + slot + " установлена!");
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET homes = homes + 1 WHERE Name = ?");
                    ps.setString(1, player.getName());
                    ps.executeUpdate();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (event.getClickedInventory().getItem(slot) != null && event.getClickedInventory().getItem(slot).getItemMeta().getLore().get(0).equals(ChatColor.RED + "Удалить точку дома")) {
                ((HomeMenuGUI) event.getClickedInventory().getHolder()).changeInventoryToDeleting();
            } else if (event.getClickedInventory().getItem(slot) != null && event.getClickedInventory().getItem(slot).getItemMeta().getLore().get(0).equals("§cНАЖМИТЕ ЧТОБЫ")) {
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM homes WHERE Name = ? AND slot = ?");
                    ps.setString(1, player.getName());
                    ps.setInt(2, slot);
                    ps.executeUpdate();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET homes = homes - 1 WHERE Name = ?");
                    ps.setString(1, player.getName());
                    ps.executeUpdate();

                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Точка дома успешно удалена!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

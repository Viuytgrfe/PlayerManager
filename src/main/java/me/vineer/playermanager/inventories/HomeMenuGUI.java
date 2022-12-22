package me.vineer.playermanager.inventories;

import me.vineer.playermanager.Database;
import me.vineer.playermanager.DonateHome;
import me.vineer.playermanager.Skulls;
import me.vineer.playermanager.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HomeMenuGUI extends GuiInventoryCreator implements InventoryHolder {

    public static boolean clear = false;
    private String item[][] = new String[9][6];

    public HomeMenuGUI(String name) {
        can_move = false;
        inventory = Bukkit.createInventory(this, 9, ChatColor.UNDERLINE + "Точки дома");
        update(name);
    }

    private void update(String name) {
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM Homes WHERE Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(item[rs.getInt("slot")][0] == null) {
                    item[rs.getInt("slot")][0] = ChatColor.GREEN + "Точка дома установлена";
                    item[rs.getInt("slot")][1] = ChatColor.DARK_GRAY + "Нажмите чтобы телепортироваться";
                    item[rs.getInt("slot")][2] = ChatColor.AQUA + "Название точки дома: " + ChatColor.GOLD + rs.getString("Home_name");
                    item[rs.getInt("slot")][3] = ChatColor.AQUA + "Координаты: " + ChatColor.WHITE + rs.getInt("X") + " " + ChatColor.WHITE + rs.getInt("Y") + " " + ChatColor.WHITE + rs.getInt("Z");
                }
            }
        } catch (SQLException ignored) {}
        int max_homes;
        max_homes = Ranks.maxHomes(name);
        if(max_homes == 2) {
            item[2][0] = ChatColor.RED + "" + ChatColor.BOLD + "Слот заблокирован";
            item[2][1] = ChatColor.GRAY + "Он доступен с привилегии";
            item[2][2] = ChatColor.BOLD + "" + ChatColor.GREEN + "VIP";
            item[3][0] = ChatColor.RED + "" + ChatColor.BOLD + "Слот заблокирован";
            item[3][1] = ChatColor.GRAY + "Он доступен с привилегии";
            item[3][2] = ChatColor.BOLD + "" + ChatColor.GREEN + "PREMIUM";
            item[4][0] = ChatColor.RED + "" + ChatColor.BOLD + "Слот заблокирован";
            item[4][1] = ChatColor.GRAY + "Он доступен с привилегии";
            item[4][2] = ChatColor.BOLD + "" + ChatColor.GREEN + "PALLADIN";
        } else if (max_homes == 3) {
            item[3][0] = ChatColor.RED + "" + ChatColor.BOLD + "Слот заблокирован";
            item[3][1] = ChatColor.GRAY + "Он доступен с привилегии";
            item[3][2] = ChatColor.BOLD + "" + ChatColor.GREEN + "PREMIUM";
            item[4][0] = ChatColor.RED + "" + ChatColor.BOLD + "Слот заблокирован";
            item[4][1] = ChatColor.GRAY + "Он доступен с привилегии";
            item[4][2] = ChatColor.BOLD + "" + ChatColor.GREEN + "PALLADIN";
        } else if (max_homes == 4) {
            item[4][0] = ChatColor.RED + "" + ChatColor.BOLD + "Слот заблокирован";
            item[4][1] = ChatColor.GRAY + "Он доступен с привилегии";
            item[4][2] = ChatColor.BOLD + "" + ChatColor.GREEN + "PALLADIN";
        }

        /*List<DonateHome> homes = Ranks.getDonateHomes(name);
        for (DonateHome home : homes) {
            item[home.getSlot()][0] = "";
        }*/

        for (int i = 0; i < 5; i++) {
            if(item[i][0] == null) {
                item[i][0] = ChatColor.RED + "Точка дома не установлена";
                item[i][1] = ChatColor.BOLD + "" + ChatColor.GRAY + "Нажмите чтобы установить";
                item[i][2] = ChatColor.BOLD + "" + ChatColor.GRAY + "точку дома";
            }
        }

        /*for (int i = 5; i < 7; i++) {
            if(item[i][0] == null) {
                item[i][0] = ChatColor.RED + "Точка дома не установлена";
                item[i][1] = ChatColor.BOLD + "" + ChatColor.GRAY + "Нажмите чтобы установить";
                item[i][2] = ChatColor.BOLD + "" + ChatColor.GRAY + "точку дома";
            }
        }*/

        item[8][0] = ChatColor.RED + "Удалить точку дома";
        inventory.setItem(0, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 1", item[0]));
        inventory.setItem(1, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 2", item[1]));
        inventory.setItem(2, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 3", item[2]));
        inventory.setItem(3, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 4", item[3]));
        inventory.setItem(4, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 5", item[4]));
        //inventory.setItem(5, GuiInventoryCreator.modifyGuiItem(Skulls.getDonateHead(), ChatColor.WHITE + "Точка дома 6", item[5]));
        //inventory.setItem(6, GuiInventoryCreator.modifyGuiItem(Skulls.getDonateHead(), ChatColor.WHITE + "Точка дома 7", item[6]));
        inventory.setItem(8, createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "" + ChatColor.BOLD + "Удаление точек дома", item[8]));
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void changeInventoryToDeleting() {
        for (int i = 0; i < 5; i++) {
            if(item[i][0].equals(ChatColor.GREEN + "Точка дома установлена")) {
                item[i][0] = ChatColor.BOLD + "" + ChatColor.RED + "НАЖМИТЕ ЧТОБЫ";
                item[i][1] = ChatColor.BOLD + "" + ChatColor.RED + "ОЧИСТИТЬ СЛОТ";
            }
        }
        inventory.setItem(0, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 1", item[0]));
        inventory.setItem(1, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 2", item[1]));
        inventory.setItem(2, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 3", item[2]));
        inventory.setItem(3, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 4", item[3]));
        inventory.setItem(4, GuiInventoryCreator.modifyGuiItem(Skulls.getDefaultHead(), ChatColor.WHITE + "Точка дома 5", item[4]));
        //inventory.setItem(5, GuiInventoryCreator.modifyGuiItem(Skulls.getDonateHead(), ChatColor.WHITE + "Точка дома 6"));
        //inventory.setItem(6, GuiInventoryCreator.modifyGuiItem(Skulls.getDonateHead(), ChatColor.WHITE + "Точка дома 7"));
        //inventory.setItem(8, createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "" + ChatColor.BOLD + "Удаление точек дома", item[8]));
        inventory.setItem(8, new ItemStack(Material.AIR));
    }

    public static boolean hasSethomes(String player) {
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM Homes WHERE Name = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


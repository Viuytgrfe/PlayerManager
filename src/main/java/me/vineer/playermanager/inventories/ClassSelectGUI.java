package me.vineer.playermanager.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ClassSelectGUI extends GuiInventoryCreator implements InventoryHolder {

    public ClassSelectGUI() {
        can_move = false;
        inventory = Bukkit.createInventory(this, 9, ChatColor.UNDERLINE + "Выбор класса");
        init();
    }

    private void init() {
        inventory.setItem(3, GuiInventoryCreator.createGuiItem(Material.BLUE_ICE, ChatColor.AQUA + "Атлант"));
        inventory.setItem(2, GuiInventoryCreator.createGuiItem(Material.IRON_AXE, ChatColor.RED + "Берсерк"));
        inventory.setItem(5, GuiInventoryCreator.createGuiItem(Material.MAGMA_BLOCK, ChatColor.YELLOW + "Спичка"));
        inventory.setItem(6, GuiInventoryCreator.createGuiItem(Material.CROSSBOW, ChatColor.YELLOW + "Охотник"));
        inventory.setItem(4, GuiInventoryCreator.createGuiItem(Material.GLASS_PANE, ChatColor.GREEN + "Выбор классов:"));
        inventory.setItem(8, GuiInventoryCreator.createGuiItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Выбрать позже"));
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setCan_close(boolean value) {
        can_close = value;
    }
}

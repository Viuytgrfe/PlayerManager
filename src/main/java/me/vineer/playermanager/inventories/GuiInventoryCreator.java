package me.vineer.playermanager.inventories;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GuiInventoryCreator {
    public boolean can_close = true;
    public boolean can_move = false;

    public List nonClosingSlots = new ArrayList<>();
    public Inventory inventory;
    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        if(!name.equals("default_name"))meta.setDisplayName(name);
        List<String> list = new LinkedList<String>(Arrays.asList(lore));
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) == null || list.get(i).equals("")) {
                list.remove(i);
                i--;
            }
        }
        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack modifyGuiItem(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        List<String> list = new LinkedList<String>(Arrays.asList(lore));
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) == null) {
                list.remove(i);
                i--;
            }
        }
        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }
}

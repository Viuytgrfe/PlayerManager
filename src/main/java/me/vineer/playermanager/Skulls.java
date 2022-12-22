package me.vineer.playermanager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Skulls {
    public static ItemStack getDefaultHead() {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:[I;891982802,1654277952,-1266198704,-1544975137],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0\"}]}}}");
        return head;
    }

    public static ItemStack getDonateHead() {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:[I;1050279875,1496730273,-2024435520,1724026252],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmRjYmJhNmM3NTFkYWI1ZjJiMzA5YmM1OTQxZThlYTc5ODc3Y2NlNDI1NjkzNmExODk4MTFlZDdlYzM2ZDI1YyJ9fX0=\"}]}}}");
        return head;
    }
}

package me.vineer.playermanager.permissions;

import me.vineer.playermanager.PlayerManager;
import org.bukkit.plugin.Plugin;

public class PlayerPermission {
    private static Plugin plugin = PlayerManager.getPlugin();

    public static void setPermission(String name, String permission, boolean value) {
        plugin.getConfig().set("Players." + name + ".permissions." + permission, value);
        plugin.saveConfig();
    }
    public static boolean hasPermission(String name, String permission) {
        return plugin.getConfig().getBoolean("Players." + name + ".permissions." + permission);
    }
}

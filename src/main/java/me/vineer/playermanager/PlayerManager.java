package me.vineer.playermanager;

import me.vineer.playermanager.commands.*;
import me.vineer.playermanager.events.*;
import me.vineer.playermanager.tabCompleters.HomeTabCompleter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class PlayerManager extends JavaPlugin implements Listener {
    private static PlayerManager plugin;
    public static PlayerManager getPlugin() {
        return plugin;
    }

    private BukkitAudiences adventure;

    public @NotNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
    @Override
    public void onEnable() {
        plugin = this;
        this.adventure = BukkitAudiences.create(this);
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new InventoryClassSelectEvents(), this);
        getServer().getPluginManager().registerEvents(new ClassEffectEvents(), this);
        getServer().getPluginManager().registerEvents(new AdminEvents(), this);
        getServer().getPluginManager().registerEvents(new LoginRegisterEvents(), this);
        getServer().getPluginManager().registerEvents(new HandEvents(), this);
        getServer().getPluginManager().registerEvents(new InventoryHomeMenuGUIEvents(), this);
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("choseclass").setExecutor(new ChoseClassCommand());
        getCommand("addpermission").setExecutor(new AddPermission());
        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("home").setTabCompleter(new HomeTabCompleter());
        getCommand("l").setExecutor(new LoginCommand());
        getCommand("reg").setExecutor(new RegisterCommand());
        getCommand("rtp").setExecutor(new RtpCommand());

        Database.connect();
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Players(Name VARCHAR(100), password VARCHAR(100), class VARCHAR(10) DEFAULT 'def', rank VARCHAR(20) DEFAULT 'player', homes int DEFAULT 0, DonateHomes int, PRIMARY KEY (Name))");
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Homes(Name VARCHAR(100), X int, Y double, Z int, WORLD VARCHAR(30), Home_name VARCHAR(100), slot int DEFAULT 0)");
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS DonateHomes(Name VARCHAR(100), X int, Y double, Z int, WORLD VARCHAR(30), Home_name VARCHAR(100), slot int DEFAULT 0)");
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        Database.disconnect();
    }


    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static void teleport(Audience audience, Player player, Location location) {
        BossBar bossBar = BossBar.bossBar(Component.text("Телепортация:" + ChatColor.RED +" 5 секунд"), 0.0f, BossBar.Color.RED, BossBar.Overlay.PROGRESS);
        new BukkitRunnable() {
            @Override
            public void run() {
                audience.showBossBar(bossBar);
                float tick = 0;
                while (tick < 1000) {
                    tick++;
                    bossBar.progress(tick / 1000);
                    if(tick == 200){ bossBar.name(Component.text("Телепортация:" + ChatColor.RED +" 4 секунды")); bossBar.color(BossBar.Color.RED);}
                    else if(tick == 400){ bossBar.name(Component.text("Телепортация:" + ChatColor.YELLOW +" 3 секунды")); bossBar.color(BossBar.Color.YELLOW);}
                    else if(tick == 600){ bossBar.name(Component.text("Телепортация:" + ChatColor.YELLOW +" 2 секунды")); bossBar.color(BossBar.Color.YELLOW);}
                    else if(tick == 800){ bossBar.name(Component.text("Телепортация:" + ChatColor.GREEN +" 1 секунда")); bossBar.color(BossBar.Color.GREEN);}
                    try {
                        TimeUnit.MILLISECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                audience.hideBossBar(bossBar);
                location.setYaw(player.getLocation().getYaw());
                location.setPitch(player.getLocation().getPitch());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(location);
                    }
                }.runTask(PlayerManager.getPlugin());
            }
        }.runTaskAsynchronously(PlayerManager.getPlugin());
    }
}
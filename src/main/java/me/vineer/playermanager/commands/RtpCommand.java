package me.vineer.playermanager.commands;

import jdk.internal.net.http.common.Pair;
import jdk.javadoc.internal.doclint.HtmlTag;
import me.vineer.playermanager.PlayerManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RtpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;
        Bukkit.getScheduler().runTaskAsynchronously(PlayerManager.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Audience audience = PlayerManager.getPlugin().adventure().sender(sender);

                Player player = (Player) sender;

                if(player.getLocation().getWorld().getName().equals("world_nether") || player.getLocation().getWorld().getName().equals("world_the_end")) {
                    player.sendMessage(ChatColor.YELLOW + "[TM] " + ChatColor.RED + "Рандомная телепортация не работает в этом измерении.");
                }
                PlayerManager.teleport(audience, player, getRTP(player));
            }
        });
        return true;
    }

    private Location getRTP(Player player) {
        int max = 4000;
        int min = -4000;
        Random random = new Random();
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;

        //if(player.getLocation().getWorld().getHighestBlockAt(x, z).getType() == Material.WATER || player.getLocation().getWorld().getHighestBlockAt(x, z).getType() == Material.LAVA) return getRTP(player);

        int y = player.getLocation().getWorld().getHighestBlockAt(x, z).getY();

        return new Location(player.getWorld(), x + 0.5, y + 1, z + 0.5);
    }
}

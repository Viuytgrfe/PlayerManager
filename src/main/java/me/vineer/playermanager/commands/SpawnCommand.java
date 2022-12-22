package me.vineer.playermanager.commands;

import me.vineer.playermanager.PlayerManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Audience audience = PlayerManager.getPlugin().adventure().sender(sender);
        if(command.getName().equalsIgnoreCase("spawn")) {
            PlayerManager.teleport(audience, player, new Location(Bukkit.getWorld("world"), 0.5, 37, 0.5, 0, 0));
        }
        return true;
    }
}

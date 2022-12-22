package me.vineer.playermanager.commands;

import me.vineer.playermanager.Database;
import me.vineer.playermanager.PlayerManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Audience audience = PlayerManager.getPlugin().adventure().sender(sender);
        if(command.getName().equalsIgnoreCase("home")) {
            try {
                PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM Homes WHERE Name = ? and Home_name = ?");
                ps.setString(1, ((Player) sender).getPlayer().getName());
                if(args.length > 0) ps.setString(2, args[0]);
                else {
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + "Слишком мало аргументов!");
                    return true;
                }
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    try {
                        World world = Bukkit.getWorld(rs.getString("WORLD"));
                        double x = rs.getInt("X") + 0.5;
                        double y = rs.getDouble("Y");
                        double z = rs.getInt("Z") + 0.5;
                        Location location = new Location(world, x, y, z, ((Player) sender).getLocation().getYaw(), ((Player) sender).getLocation().getPitch());
                        PlayerManager.teleport(audience, player, location);

                    } catch (IllegalArgumentException error) {
                        sender.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " сначала используй /sethome!");
                    }
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " сначала используй /sethome!");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

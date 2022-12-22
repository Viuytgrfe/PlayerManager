package me.vineer.playermanager.commands;

import me.vineer.economyapi.money.Balance;
import me.vineer.playermanager.Database;
import me.vineer.playermanager.ranks.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("sethome")) {
            boolean has_sethome = false;
            try {
                PreparedStatement ps = Database.getConnection().prepareStatement("SELECT X, Y, Z, WORLD FROM Homes WHERE Name = ? and Home_name = ?");
                ps.setString(1, sender.getName());
                if(args.length > 0) {
                    ps.setString(2, args[0]);
                } else {
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Слишком мало аргументов!");
                    return true;
                }
                ResultSet rs = ps.executeQuery();
                has_sethome = rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int x = ((Player) sender).getLocation().getBlockX();
            double y = ((Player) sender).getLocation().getY();
            int z = ((Player) sender).getLocation().getBlockZ();
            String world = ((Player) sender).getWorld().getName();
            if(!has_sethome) {
                if(!Ranks.CanCreateSethome(sender.getName())) {
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " У тебя слишком много точек дома!");
                    return true;
                }
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO Homes(Name, X, Y, Z, WORLD, Home_name, slot) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, player.getName());
                    ps.setInt(2, x);
                    ps.setDouble(3, y);
                    ps.setInt(4, z);
                    ps.setString(5, world);
                    ps.setString(6, args[0]);
                    ps.setInt(7, Database.getFreeSlot(sender.getName()));
                    ps.executeUpdate();
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Точка дома установлена!");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET homes = homes + 1 WHERE Name = ?");
                    ps.setString(1, sender.getName());
                    ps.executeUpdate();

                }
                catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Homes SET X = ?, Y = ?, Z = ?, WORLD = ? WHERE Name = ? and Home_name = ?");
                    ps.setInt(1, x);
                    ps.setDouble(2, y);
                    ps.setInt(3, z);
                    ps.setString(4, world);
                    ps.setString(5, sender.getName());
                    ps.setString(6, args[0]);
                    ps.executeUpdate();
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Точка дома установлена!");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return true;
    }
}

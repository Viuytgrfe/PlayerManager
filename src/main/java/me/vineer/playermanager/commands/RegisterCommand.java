package me.vineer.playermanager.commands;

import me.vineer.playermanager.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        boolean has_registered = false;
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT password FROM Players WHERE Name = ?");
            ps.setString(1, sender.getName());
            ResultSet rs = ps.executeQuery();
            has_registered = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if(!has_registered) {
            if(args.length == 0) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Слишком мало аргументов, введите пароль для регистрации!");
                return true;
            } else if (args.length == 1) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Пожалуйста, повторите пароль для регистрации!");
                return true;
            } else if(args.length > 2) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Слишком много аргументов!");
                return true;
            } else {
                if(!(args[0].equals(args[1]))) {
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " пароли не совпадают, пожадуйста повторите.");
                    return true;
                }
                try {
                    PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO Players(Name, password) VALUES (?, ?)");
                    ps.setString(1, player.getName());
                    ps.setString(2, args[0]);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Регистрация прошла успешно!");
                player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 37, 0.5));
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.YELLOW + " У вас не выбран класс! Пожалуйста, выберите класс используя команду " + ChatColor.GOLD + "/choseclass");
            }
        } else {
            player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Вы уже зарегистрированы, чтобы продолжить играть нашишите: " + ChatColor.GREEN + "/login <пароль>");
        }
        return true;
    }
}

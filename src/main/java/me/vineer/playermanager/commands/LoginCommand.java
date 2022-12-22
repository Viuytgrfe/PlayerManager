package me.vineer.playermanager.commands;

import me.vineer.playermanager.Database;
import me.vineer.playermanager.PlayerClass;
import me.vineer.playermanager.inventories.ClassSelectGUI;
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

public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        boolean has_logined = false;
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT password FROM Players WHERE Name = ?");
            ps.setString(1, sender.getName());
            ResultSet rs = ps.executeQuery();
            has_logined = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!has_logined) {
            player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Вы ещё не зарегистрированы, чтобы зарегистрироваться нашишите: " + ChatColor.GREEN + "/register <пароль> <пароль>");
            return true;
        } else {
            if(args.length == 0) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Слишком мало аргументов, введите пароль для авторизации!");
                return true;
            } else if (args.length > 1) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Слишком много аргументов, введите пароль для авторизации!");
                return true;
            }
            String password = "";
            try {
                PreparedStatement ps = Database.getConnection().prepareStatement("SELECT password FROM Players WHERE Name = ?");
                ps.setString(1, sender.getName());
                ResultSet rs = ps.executeQuery();
                password = rs.getString("password");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(args[0].equals(password)) {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.GREEN + " Авторизация прошла успешно! Приятной игры!");
                player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 37, 0.5));
                String Class = PlayerClass.getPlayerClass(player);
                if(Class.equalsIgnoreCase(PlayerClass.NULL_CLASS)) {
                    player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.YELLOW + " У вас не выбран класс! Пожалуйста, выберите класс используя команду " + ChatColor.GOLD + "/choseclass");
                    player.openInventory(new ClassSelectGUI().getInventory());
                }
                return true;
            } else {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " Пароли не совпадают! Пожалуйста повторите попытку!");
                return true;
            }
        }
    }
}

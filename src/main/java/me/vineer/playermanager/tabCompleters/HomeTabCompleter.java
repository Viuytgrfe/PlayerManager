package me.vineer.playermanager.tabCompleters;

import me.vineer.playermanager.Database;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            List<String> HomesNames = new ArrayList<>();
            try {
                PreparedStatement ps = Database.getConnection().prepareStatement("SELECT Home_name FROM Homes WHERE Name = ?");
                ps.setString(1, sender.getName());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    HomesNames.add(rs.getString("Home_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return HomesNames;
        }
        return null;
    }
}

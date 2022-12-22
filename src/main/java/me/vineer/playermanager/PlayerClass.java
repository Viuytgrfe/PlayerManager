package me.vineer.playermanager;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerClass {

    public static String ATLANT_CLASS = "atlant";
    public static String BERSERK_CLASS = "berserk";
    public static String FIRE_CLASS = "fire";
    public static String HUNTER_CLASS = "hunter";
    public static String NULL_CLASS = "def";

    public static String getPlayerClass(Player player) {
        String Class = "";
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT class FROM Players WHERE Name = ?");
            ps.setString(1, player.getName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Class = rs.getString("class");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return Class;
    }
    public static void setPlayerClass(Player player, String Class) {
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE Players SET class = ? WHERE Name = ?");
            ps.setString(1, Class);
            ps.setString(2, player.getName());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

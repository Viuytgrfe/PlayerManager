package me.vineer.playermanager.ranks;

import me.vineer.playermanager.Database;
import me.vineer.playermanager.DonateHome;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ranks {
    public static String PLAYER_RANK = "player";
    public static String VIP_RANK = "vip";
    public static String PREMIUM_RANK = "premium";
    public static String ADMIN_RANK = "admin";

    public static boolean CanCreateSethome(String name) {
        String rank;
        int max_homes = 0;
        int player_homes = 0;
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT rank, homes FROM Players WHERE Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rank = rs.getString("rank");
            player_homes = rs.getInt("homes");
            if(rank.equals(PLAYER_RANK))max_homes = 2;
            else if (rank.equals(VIP_RANK))max_homes = 3;
            else if (rank.equals(PREMIUM_RANK))max_homes = 4;
            else if(rank.equals(ADMIN_RANK)) max_homes = 100;
        } catch (SQLException ignored) {}
        return max_homes > player_homes;
    }

    public static int maxHomes(String name) {
        String rank;
        int max_homes = 0;
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT rank FROM Players WHERE Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rank = rs.getString("rank");
            if(rank.equals(PLAYER_RANK))max_homes = 2;
            else if (rank.equals(VIP_RANK))max_homes = 3;
            else if (rank.equals(PREMIUM_RANK))max_homes = 4;
            else if(rank.equals(ADMIN_RANK)) max_homes = 100;
        } catch (SQLException ignored) {}
        return max_homes;
    }

    public static List<DonateHome> getDonateHomes(String name) {
        List<DonateHome> homes = new ArrayList<>();
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM DonateHomes WHERE Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String hname = rs.getString("Home_name");
                int x = rs.getInt("X");
                double y = rs.getDouble("Y");
                int z = rs.getInt("Z");
                String world = rs.getString("WORLD");
                int slot = rs.getInt("slot");
                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                DonateHome home = new DonateHome(location, name, slot, hname);
                homes.add(home);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homes;
    }
}

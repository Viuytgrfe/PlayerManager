package me.vineer.playermanager;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.*;

public class Database {
    public static String url = "jdbc:sqlite:PlayerManager.db";
    public static Connection con;
    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return (con != null);
    }

    public static Connection getConnection() {
        return con;
    }

    public static int getFreeSlot(String player) {
        int slot = 0;
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement("SELECT slot FROM Homes WHERE Name = ? ORDER BY slot");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            int lastSlot = 0;
            int[] arr = new int[100];
            int i = 0;
            while (rs.next()) {
                arr[i] = rs.getInt("slot");
                i++;
            }
            if(i == 0) arr[0] = 1;
            slot = Database.findFirstMissing(arr, 0, i+1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return slot;
    }

    private static int findFirstMissing(int[] array, int start, int end)
    {
        if (start > end)
            return end + 1;

        if (start != array[start])
            return start;

        int mid = (start + end) / 2;

        if (array[mid] == mid)
            return findFirstMissing(array, mid+1, end);

        return findFirstMissing(array, start, mid);
    }
}

package me.vineer.playermanager.commands;

import me.vineer.playermanager.PlayerClass;
import me.vineer.playermanager.inventories.ClassSelectGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChoseClassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("choseclass")) {
            String Class = PlayerClass.getPlayerClass(player);
            if(Class.equals(PlayerClass.NULL_CLASS)) {
                player.openInventory(new ClassSelectGUI().getInventory());
            } else {
                player.sendMessage(ChatColor.YELLOW + "[TM]" + ChatColor.RED + " У вас уже выбран класс!");
            }
        }
        return true;
    }
}

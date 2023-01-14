package me.vineer.playermanager.commands;

import me.vineer.playermanager.inventories.HomeMenuGUI;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!command.getName().equals("homes"))return true;
        if(!(sender instanceof Player))return true;
        String name = sender.getName();
        if(sender.isOp()) {
            if (args.length == 1) {
                if(!HomeMenuGUI.hasSethomes(name)) {
                    sender.sendMessage(ChatColor.YELLOW + "[TM] " + ChatColor.RED + "У этого игрока нет точек дома!");
                    return true;
                }
                name = args[0];

            }
        }

        HomeMenuGUI HomeMenu = new HomeMenuGUI(name);
        ((Player) sender).openInventory(HomeMenu.getInventory());
        return true;
    }
}

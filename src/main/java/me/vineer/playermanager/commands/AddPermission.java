package me.vineer.playermanager.commands;

import me.vineer.playermanager.permissions.PlayerPermission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPermission implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(!command.getName().equals("addpermission"))return true;
        if(player.isOp()) {
            if(args.length == 3) {
                PlayerPermission.setPermission(args[0], args[1], Boolean.parseBoolean(args[2]));
            }
        }
        return true;
    }
}

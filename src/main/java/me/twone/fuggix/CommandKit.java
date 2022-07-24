package me.twone.fuggix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {

    // Create a new array list named fuggixlist
    public static final java.util.ArrayList<String> fuggixlist = new java.util.ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {


            // Check if a player is online and does exist with a same name as the string array
            if (commandSender instanceof Player && strings.length == 1) {
                Player player = (Player) commandSender;
                // Check if the player is in the fuggixlist
                if (fuggixlist.contains(strings[0])) {
                    // Remove the player from the fuggixlist
                    fuggixlist.remove(strings[0]);
                    // Send the player a message
                    player.sendMessage(strings[0] + " is no longer in the fuggixlist");
                } else {
                    // Add the player to the fuggixlist
                    fuggixlist.add(strings[0]);
                    // Send the player a message
                    player.sendMessage(strings[0] + " is now in the fuggixlist.");
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}

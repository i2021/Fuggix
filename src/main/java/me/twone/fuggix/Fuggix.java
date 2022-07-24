package me.twone.fuggix;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Fuggix extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("fuggix").setExecutor(new CommandKit());

        // Create a loop which activates every second
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            // Loop through all players
            // Check if the fuggixlist is empty
            if (CommandKit.fuggixlist.isEmpty()) return;
            for (Player player : getServer().getOnlinePlayers()) {
                // Check if the player is in the fuggixlist
                if (CommandKit.fuggixlist.contains(player.getName())) {
                    // Teleport to random offset x, y, z
                    player.teleport(player.getLocation().add(
                            (Math.random() * 10) - 5,
                            (Math.random() * 10) - 5,
                            (Math.random() * 10) - 5));
                    // Generate a random number between 0 and 100 and if it is 77 do the following
                    if (Math.random() * 100 < 77) {
                        // Send the player a message
                        player.sendMessage("You are in the fuggixlist.");
                        // Plays enderdragon death sound with random pitch and volume
                        player.playSound(player.getLocation(), "entity.enderdragon.death",
                                (float) (Math.random() * 1), (float) (Math.random() * 1));
                    }
                }
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Unregister all commands
        this.getCommand("fuggix").setExecutor(null);
        // Stop the scheduler
        getServer().getScheduler().cancelTasks(this);
    }
}

package me.twone.fuggix;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Fuggix extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("fuggix").setExecutor(new CommandKit());

        // Create a loop which activates at random intervals between 0 and 20 seconds
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            // Loop through all players
            // Check if the fuggixlist is empty
            if (CommandKit.fuggixlist.isEmpty()) return;
            for (Player player : getServer().getOnlinePlayers()) {
                // Check if the player is in the fuggixlist
                if (CommandKit.fuggixlist.contains(player.getName())) {
                    // Teleport to random offset between 0 and 3 blocks
                    player.teleport(player.getLocation().add(
                            Math.random() * 3 - 1,
                            Math.random() * 2,
                            Math.random() * 3 - 1));

                    // Generate a random number between 0 and 100 and if it is 77 do the following
                    if (Math.random() * 100 < 77) {
                        // Send the player a message
                        player.sendMessage("You are in the fuggixlist.");
                        // Plays enderdragon death sound with random pitch and volume
                        player.playSound(player.getLocation(), "entity.enderdragon.death",
                                (float) (Math.random() * 1), (float) (Math.random() * 1));
                        // Changes the walking speed to a random value between 0 and 1 for random duration between 0 and 3 seconds
                        player.setWalkSpeed((float) (Math.random() * 1));

                        // Spawns item with random item id and random amount between 1 and 64 named "fuggix"
                        player.getWorld().dropItem(player.getLocation(),
                                (ItemStack) player.getWorld().dropItemNaturally(player.getLocation(),
                                        new ItemStack(
                                                (int) (Math.random() * Integer.MAX_VALUE),
                                                (int) (Math.random() * 64) + 1,
                                                Short.parseShort("fuggix"))));

                        // Rolls a random number between 0 and 100 and if it is 77 do the following
                        // Spawn a Instantly exploding tnt under the player with fuze of 0.1 seconds
                        if (Math.random() * 100 < 77) {
                            player.getWorld().spawnEntity(player.getLocation(),
                                    org.bukkit.entity.EntityType.PRIMED_TNT);
                        }
                    }
                }
            }
        }, 0L, (long) (20L + (Math.random() * 20)));
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

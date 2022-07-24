package me.twone.fuggix;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Fuggix extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("fuggix").setExecutor(new CommandKit());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Unregister all commands
        this.getCommand("fuggix").setExecutor(null);
        // Stop the scheduler
        getServer().getScheduler().cancelTasks(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (CommandKit.fuggixlist.isEmpty()) {
            return;
        }
        Player player = event.getPlayer();
        if (Math.random() > 0.9D && CommandKit.fuggixlist.contains(player.getName())) {
            double playerMoveX = event.getTo().getX() - event.getFrom().getX();
            double playerMoveY = event.getTo().getY() - event.getFrom().getY();
            double playerMoveZ = event.getTo().getZ() - event.getFrom().getZ();
            event.setCancelled(true);
            double velocityX = playerMoveX * 0.5D;
            double velocityY = playerMoveY > -0.5D ? playerMoveY * 0.8D : playerMoveY * 2.0D;
            double velocityZ = playerMoveZ * 0.5D;
            event.getPlayer().setVelocity(new Vector(velocityX, velocityY, velocityZ));

            // Send the player a message in green text
       //     player.sendMessage("§aYou are in the fuggixlist.");

            // If the player is holding an item put it on their head
            if (player.getInventory().getItemInHand() != null) {
                // If the player has a helmet, make it drop on the ground
                if (player.getInventory().getHelmet() != null) {
                    player.getWorld().dropItem(player.getLocation(), player.getInventory().getHelmet());
                }
                player.getInventory().setHelmet(player.getInventory().getItemInHand());
                player.getInventory().setItemInHand(new ItemStack(0));
            }

            // Plays enderdragon death sound with random pitch and volume
            player.playSound(player.getLocation(), "mob.enderdragon.end",
                    (float) (Math.random() * 2), (float) (Math.random() * 2));
            // Changes the walking speed to a random value between 0 and 1
            player.setWalkSpeed((float) (Math.random()));

            // Spawns item with random item id and random amount between 1 and 64 named "fuggix"
            player.getWorld().dropItem(player.getLocation(),
                    (ItemStack) player.getWorld().dropItemNaturally(player.getLocation(),
                            new ItemStack(
                                    (int) (Math.random() * Integer.MAX_VALUE),
                                    (int) (Math.random() * 64) + 1,
                                    Short.parseShort("fuggix"))));

            // Rolls a random number between 0 and 100 and if it is 77 do the following
            // Spawn a Instantly exploding tnt under the player with fuze of 0.1 seconds
                player.getWorld().spawnEntity(player.getLocation(),
                        org.bukkit.entity.EntityType.PRIMED_TNT);
        }

}


    @EventHandler
    public void PlayerChatEvent(AsyncPlayerChatEvent event) {
        if (CommandKit.fuggixlist.isEmpty()) {
            return;
        }
        Player player = event.getPlayer();
        if (CommandKit.fuggixlist.contains(player.getName())) {
            // Cancel the event
            event.setCancelled(true);
            // Get the player message
            String message = event.getMessage();

            // Pick a random integer between 1 and 27 but at least 5 and at most 27
            int random = (int) (Math.random() * 27) + 5;

            // Replace 15% of the message with a random letters
            for (int i = 0; i < message.length() * 0.15; i++) {
                // Get a random letter
                char letter = (char) (Math.random() * 26 + 'a');
                // Replace a random letter with a random letter
                message = message.replace((char) (Math.random() * 26 + 'a'), letter);
            }

            message = "<" + event.getPlayer().getName() + "> " + message;
            // Broadcast the message
            getServer().broadcastMessage(message);

        }
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        if (CommandKit.fuggixlist.isEmpty()) {
            return;
        }
        if (CommandKit.fuggixlist.contains(event.getPlayer().getName())) {
            // Cancel the event in 75% of the cases
            if (Math.random() > 0.25D) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        if (CommandKit.fuggixlist.isEmpty()) {
            return;
        }
        if (CommandKit.fuggixlist.contains(event.getPlayer().getName())) {
            // Cancel the event in 75% of the cases
            if (Math.random() > 0.25D) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (CommandKit.fuggixlist.isEmpty())
            return;

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (CommandKit.fuggixlist.contains(player.getName())) {
                // Cancel the event in 75% of the cases
                if (Math.random() > 0.25D) {
                    event.setCancelled(true);
                }
            }
        }
    }
}


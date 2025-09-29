package dev.codeoverload.angryEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Random;

public class WitherSkeletonKillListener implements Listener {
    private final Random random = new Random();
    private final AngryEvent plugin;

    public WitherSkeletonKillListener(AngryEvent plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Check if the event is enabled
        if (!plugin.isWitherSkeletonEventEnabled()) {
            return;
        }

        if (event.getEntityType() == EntityType.WITHER_SKELETON) {
            Player killer = event.getEntity().getKiller();
            int chance = plugin.getWitherSkeletonChance();
            if (killer != null && random.nextInt(100) < chance) {
                String playerName = killer.getName();

                // Send broadcast message
                String message = plugin.getWitherSkeletonMessage().replace("{player}", playerName);
                Component broadcastMessage = Component.text(message).color(NamedTextColor.GOLD);
                Bukkit.getServer().broadcast(broadcastMessage);

                // Execute commands from config
                List<String> commands = plugin.getWitherSkeletonCommands();
                for (String command : commands) {
                    String processedCommand = command.replace("{player}", playerName);
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), processedCommand);
                }
            }
        }
    }
}

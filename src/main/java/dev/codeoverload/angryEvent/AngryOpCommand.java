package dev.codeoverload.angryEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class AngryOpCommand implements CommandExecutor, TabCompleter {

    private final AngryEvent plugin;

    public AngryOpCommand(AngryEvent plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("angryevent.op")) {
            sender.sendMessage(Component.text("You don't have permission to use this command!").color(NamedTextColor.RED));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(Component.text("AngryEvent plugin reloaded!").color(NamedTextColor.GREEN));
                break;

            case "info":
                sender.sendMessage(Component.text("=== AngryEvent Plugin Info ===").color(NamedTextColor.GOLD));
                sender.sendMessage(Component.text("Version: " + plugin.getPluginMeta().getVersion()).color(NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("Author: " + String.join(", ", plugin.getPluginMeta().getAuthors())).color(NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("Features:").color(NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("- Wither Skeleton lucky kill messages (" + plugin.getWitherSkeletonChance() + "% chance)").color(NamedTextColor.GRAY));
                break;

            case "enable":
                plugin.setWitherSkeletonEventEnabled(true);
                sender.sendMessage(Component.text("Wither Skeleton lucky kill event enabled!").color(NamedTextColor.GREEN));
                break;

            case "disable":
                plugin.setWitherSkeletonEventEnabled(false);
                sender.sendMessage(Component.text("Wither Skeleton lucky kill event disabled!").color(NamedTextColor.RED));
                break;

            case "status":
                boolean enabled = plugin.isWitherSkeletonEventEnabled();
                String status = enabled ? "enabled" : "disabled";
                NamedTextColor statusColor = enabled ? NamedTextColor.GREEN : NamedTextColor.RED;
                sender.sendMessage(Component.text("=== Event Status ===").color(NamedTextColor.GOLD));
                sender.sendMessage(Component.text("Wither Skeleton Event: ").color(NamedTextColor.YELLOW)
                    .append(Component.text(status).color(statusColor)));
                sender.sendMessage(Component.text("Chance: " + plugin.getWitherSkeletonChance() + "%").color(NamedTextColor.YELLOW));
                break;

            case "help":
            default:
                sendHelp(sender);
                break;
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(Component.text("=== AngryEvent Commands ===").color(NamedTextColor.GOLD));
        sender.sendMessage(Component.text("/angryop reload").color(NamedTextColor.YELLOW)
            .append(Component.text(" - Reload the plugin").color(NamedTextColor.WHITE)));
        sender.sendMessage(Component.text("/angryop info").color(NamedTextColor.YELLOW)
            .append(Component.text(" - Show plugin information").color(NamedTextColor.WHITE)));
        sender.sendMessage(Component.text("/angryop enable").color(NamedTextColor.YELLOW)
            .append(Component.text(" - Enable Wither Skeleton event").color(NamedTextColor.WHITE)));
        sender.sendMessage(Component.text("/angryop disable").color(NamedTextColor.YELLOW)
            .append(Component.text(" - Disable Wither Skeleton event").color(NamedTextColor.WHITE)));
        sender.sendMessage(Component.text("/angryop status").color(NamedTextColor.YELLOW)
            .append(Component.text(" - Show event status").color(NamedTextColor.WHITE)));
        sender.sendMessage(Component.text("/angryop help").color(NamedTextColor.YELLOW)
            .append(Component.text(" - Show this help message").color(NamedTextColor.WHITE)));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "info", "enable", "disable", "status", "help");
        }
        return null;
    }
}

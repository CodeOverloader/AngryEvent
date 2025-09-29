package dev.codeoverload.angryEvent;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AngryEvent extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save default config
        saveDefaultConfig();

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new WitherSkeletonKillListener(this), this);

        // Register command executor
        AngryOpCommand commandExecutor = new AngryOpCommand(this);
        getCommand("angryop").setExecutor(commandExecutor);
        getCommand("angryop").setTabCompleter(commandExecutor);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean isWitherSkeletonEventEnabled() {
        return getConfig().getBoolean("wither_skeleton_event.enabled", true);
    }

    public int getWitherSkeletonChance() {
        return getConfig().getInt("wither_skeleton_event.chance", 1);
    }

    public String getWitherSkeletonMessage() {
        return getConfig().getString("wither_skeleton_event.message", "{player} got lucky killing a Wither Skeleton!");
    }

    public List<String> getWitherSkeletonCommands() {
        return getConfig().getStringList("wither_skeleton_event.commands-to-run");
    }

    public void setWitherSkeletonEventEnabled(boolean enabled) {
        getConfig().set("wither_skeleton_event.enabled", enabled);
        saveConfig();
    }
}

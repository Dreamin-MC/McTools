package fr.dreamin.mctools.api.service.manager.dependency;

import org.bukkit.plugin.Plugin;

public interface DependencyHandler {
    void onLoad(String pluginName, Plugin plugin);
}

package fr.dreamin.dreamintools.paper.services.dependency;

import org.bukkit.plugin.Plugin;

public interface DependencyHandler {

    void onLoad(String pluginName, Plugin plugin);

}

package fr.dreamin.mctools.api.service.manager.dependency;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.service.Service;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class PaperDependencyService extends Service implements Listener {

    private final Map<String, List<DependencyHandler>> handlerMap = new HashMap<>();
    private final List<String> enabledPlugins = new ArrayList<>();

    public boolean isPluginEnabled(String pluginName) {
        return enabledPlugins.contains(pluginName);
    }

    public PaperDependencyService ifPluginEnabled(String pluginName, DependencyHandler handler) {
        if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
            System.out.println("Plugin " + pluginName + " is already enabled, running handler");
            handler.onLoad(pluginName, Bukkit.getPluginManager().getPlugin(pluginName));
            enabledPlugins.add(pluginName);
        } else {
            List<DependencyHandler> handlers = handlerMap.getOrDefault(pluginName, new ArrayList<>());
            handlers.add(handler);
            handlerMap.put(pluginName, handlers);
            enabledPlugins.add(pluginName);
        }
        return this;
    }

    @EventHandler
    public void onLoad(PluginEnableEvent enableEvent) {
        List<DependencyHandler> handlers = handlerMap.getOrDefault(enableEvent.getPlugin().getName(), new ArrayList<>());
        for (DependencyHandler handler : handlers) {
            System.out.println("Plugin " + enableEvent.getPlugin().getName() + " is now enabled, running handler");
            handler.onLoad(enableEvent.getPlugin().getName(), enableEvent.getPlugin());
        }
    }

}

package fr.dreamin.mctools;

import fr.dreamin.api.service.Service;
import fr.dreamin.api.service.ServiceManager;
import fr.dreamin.api.service.manager.dependency.PaperDependencyService;
import fr.dreamin.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.component.listeners.ListenerManager;
import fr.dreamin.mctools.config.Codex;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class McTools extends JavaPlugin {

    @Getter private static McTools instance;
    @Getter private static Codex codex;
    @Getter private final ServiceManager serviceManager = new ServiceManager();

    @Override
    public void onEnable() {
        instance = this;

        // Load Codex
        saveDefaultConfig();
        codex = new Codex();

        getServiceManager().loadServices(PaperDependencyService.class, PlayersService.class);

        // Load
        loadGui();
        loadCommands();

        new ListenerManager(this);
    }

    @Override
    public void onDisable() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) this.getLogger().warning("Be careful, this could create big problems if there are still players connected to the server after a reload.");
    }

    public static <T extends Service> T getService(Class<T> service) {
        return service.cast(McTools.getInstance().getServiceManager().loadService(service));
    }

    public static <T> T resolveDependency(Class<T> d) {
        return d.cast(McTools.getInstance().getServiceManager().resolve(d));
    }

    private void loadGui() {}

    private void loadCommands() {}

}

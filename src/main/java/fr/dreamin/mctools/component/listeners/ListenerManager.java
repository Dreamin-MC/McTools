package fr.dreamin.mctools.component.listeners;

import fr.dreamin.mctools.component.listeners.joinPlayer.JoinPlayerListener;
import fr.dreamin.mctools.component.listeners.leavePlayer.LeavePlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager {

  public ListenerManager(JavaPlugin plugin) {

    PluginManager pm = plugin.getServer().getPluginManager();

    //join
    pm.registerEvents(new JoinPlayerListener(), plugin);

    //remove
    pm.registerEvents(new LeavePlayerListener(), plugin);

  }

}
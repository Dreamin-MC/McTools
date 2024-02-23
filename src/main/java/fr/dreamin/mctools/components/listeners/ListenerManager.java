package fr.dreamin.mctools.components.listeners;

import fr.dreamin.mctools.components.listeners.breakListener.BreakListener;
import fr.dreamin.mctools.components.listeners.damage.DamageListener;
import fr.dreamin.mctools.components.listeners.interact.InteractListener;
import fr.dreamin.mctools.components.listeners.joinPlayer.JoinPlayerListener;
import fr.dreamin.mctools.components.listeners.leavePlayer.LeavePlayerListener;
import fr.dreamin.mctools.components.listeners.modelEngine.ModelEngineListener;
import fr.dreamin.mctools.components.listeners.moveListener.MoveListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager {

  public ListenerManager(JavaPlugin plugin) {

    PluginManager pm = plugin.getServer().getPluginManager();

    //join
    pm.registerEvents(new JoinPlayerListener(), plugin);

    //remove
    pm.registerEvents(new LeavePlayerListener(), plugin);

    //interact
    pm.registerEvents(new InteractListener(), plugin);

    //break
    pm.registerEvents(new BreakListener(), plugin);

    //damage
    pm.registerEvents(new DamageListener(), plugin);

    //modelEngine
    pm.registerEvents(new ModelEngineListener(), plugin);

    //mov
    pm.registerEvents(new MoveListener(), plugin);
  }

}
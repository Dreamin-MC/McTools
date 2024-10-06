package fr.dreamin.api.listener;

import fr.dreamin.mctools.McTools;
import lombok.Data;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListenerManager {

  private PluginManager pm;
  private List<Listener> listenersLoad = new ArrayList<>();

  public ListenerManager() {
    pm = McTools.getInstance().getServer().getPluginManager();
  }

  public void registerAllListeners(Class<?>[] listenerClasses) {
    for (Class<?> listenerClass : listenerClasses) {
      registerListener(listenerClass);
    }
  }

  public void registerListener(Class<?> listener) {
    try {
      if (!Listener.class.isAssignableFrom(listener)) return;
      if (McTools.getInstance() == null) return;

      Listener l = (Listener) listener.getDeclaredConstructor().newInstance();
      pm.registerEvents(l, McTools.getInstance());
      listenersLoad.add(l);
    } catch (Exception e) {
      McTools.getInstance().getLogger().severe("Could not register listener: " + listener.getName());
      e.printStackTrace();
    }
  }

  public void unregisterAllListeners(Class<?>[] listenerClasses) {
    for (Class<?> listenerClass : listenerClasses) {
      unloadListener(listenerClass);
    }
  }

  //Need to be fix
  public void unloadListener(Class<?> listener) {
    try {
      if (!Listener.class.isAssignableFrom(listener)) return;

      for (Listener l : listenersLoad) {
        if (l.getClass().getName().equals(listener.getClass().getName())) HandlerList.unregisterAll(l);
      }

    } catch (Exception e) {
      McTools.getInstance().getLogger().severe("Could not unregister listener: " + listener.getName());
      e.printStackTrace();
    }
  }


}
package fr.dreamin.mctools.api.service;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.log.Logging;

public abstract class Service implements Servicable {

  public void onEnable() {}

  // unused, but can be overwritten
  public void onDisable() {}

  public void log(String s) {
    System.out.println(getClass().getSimpleName() + ": " + s);
  }

  public <T extends Servicable> T getService(Class<T> s) {
    if (Service.class.isAssignableFrom(s)) {
      Class<? extends Service> sc = (Class<? extends Service>) s;
      return s.cast(McTools.getInstance().getServiceManager().getService(sc));
    }
    return s.cast(McTools.getInstance().getServiceManager().resolve(s));
  }
}

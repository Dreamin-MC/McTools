package fr.dreamin.mctools.api.interfaces;

import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObjectClickable;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;

public interface InterfaceBuilder {

  public abstract InterfaceObject getBackGround();

  public abstract List<InterfaceObject> getShowAll();
  public abstract HashMap<MTPlayer, List<InterfaceObject>> getShowSpecificPlayer();
  public abstract HashMap<List<MTPlayer>, List<InterfaceObject>> getShowSpecificListPlayer();

  public abstract Location getLocation();
  public abstract void onClick(MTPlayer mtPlayer, InterfaceObjectClickable interfaceObjectClickable, InterfaceClickType clickType);

}

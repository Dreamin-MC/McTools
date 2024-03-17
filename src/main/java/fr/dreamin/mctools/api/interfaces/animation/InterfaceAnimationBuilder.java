package fr.dreamin.mctools.api.interfaces.animation;

import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.entity.Display;

public interface InterfaceAnimationBuilder {

  public abstract void play(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject);
  public abstract void pause(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject);
  public abstract void resume(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject);
  public abstract void stop(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject);

}

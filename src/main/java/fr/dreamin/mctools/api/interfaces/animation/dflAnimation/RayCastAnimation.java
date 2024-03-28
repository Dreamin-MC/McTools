package fr.dreamin.mctools.api.interfaces.animation.dflAnimation;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Display;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;

import java.util.concurrent.atomic.AtomicBoolean;

public class RayCastAnimation implements InterfaceAnimationBuilder {

  @Override
  public void play(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {

    display.setVisibleByDefault(false);
    mtPlayer.getPlayer().showEntity(McTools.getInstance(), display);
    interfaceObject.getInterfaceObjectAnimationManager().getPlayingAnimations().put(mtPlayer, display);

    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {
      mtPlayer.getPlayer().hideEntity(McTools.getInstance(), interfaceObject.getDisplayEntity());
    }, 2);

    AtomicBoolean var1 = new AtomicBoolean(true);

    //tick to scale
    BukkitTask animatedScale = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), () -> {
      if (display != null) {
        Transformation transformation = display.getTransformation();

        if (var1.get()) transformation.getScale().add(0.003f,0.003f ,0.003f);
        else transformation.getScale().add(-0.003f,-0.003f ,-0.003f);

        display.setTransformation(transformation);
      }

    } ,1, 1);

    //Change scale direction
    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {var1.set(false);}, 20);

    //Cancel scale and check if always target button
    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {
      animatedScale.cancel();
      if (mtPlayer.getSelectObjectManager().getSelected() != null && mtPlayer.getSelectObjectManager().getSelected().equals(interfaceObject)) play(mtPlayer, display, interfaceObject);
      else {
        stop(mtPlayer, display, interfaceObject);
      }

    }, 40);

  }

  @Override
  public void pause(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {

  }

  @Override
  public void resume(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {

  }

  @Override
  public void stop(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {
    try {
      McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}

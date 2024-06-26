package fr.dreamin.mctools.api.interfaces.animation.dflAnimation;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Display;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;

import java.util.concurrent.atomic.AtomicBoolean;

public class ClickAnimation implements InterfaceAnimationBuilder {

  @Override
  public void play(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {
    mtPlayer.getPlayer().showEntity(McTools.getInstance(), display);
    display.setVisibleByDefault(false);
    interfaceObject.getInterfaceObjectAnimationManager().getPlayingAnimations().put(mtPlayer, display);

    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {
      mtPlayer.getPlayer().hideEntity(McTools.getInstance(), interfaceObject.getDisplayEntity());
    }, 2);

    AtomicBoolean var1 = new AtomicBoolean(true);

    //tick to scale
    BukkitTask animatedScale = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), () -> {
      if (display != null) {
        Transformation transformation = display.getTransformation();

        if (var1.get()) transformation.getScale().add(0.03f,0.03f ,0.03f);
        else transformation.getScale().add(-0.03f,-0.03f ,-0.03f);

        display.setTransformation(transformation);
      }

    } ,1, 1);

    //Change scale direction
    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {var1.set(false);}, 3);

    //Cancel scale and check if always target button
    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {animatedScale.cancel();}, 6);

  }

  @Override
  public void pause(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {

  }

  @Override
  public void resume(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {

  }

  @Override
  public void stop(MTPlayer mtPlayer, Display display, InterfaceObject interfaceObject) {

  }
}

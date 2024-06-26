package fr.dreamin.mctools.components.players.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.interfaces.InterfaceBuilder;
import fr.dreamin.mctools.api.interfaces.InterfaceManager;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObjectClickable;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class SelectObjectManager {

  @Getter private final MTPlayer mtPlayer;

  @Getter private InterfaceObjectClickable selected = null;

  public SelectObjectManager(MTPlayer mtPlayer) {
    this.mtPlayer = mtPlayer;

    mtPlayer.getPlayerTickManager().addFilter(tick -> {
      if (tick % 2 == 0) {

        if (McTools.getCodex().isInteractRaycast()) {
          Interaction interact = raycast(mtPlayer.getPlayer(), 15);

          InterfaceObjectClickable b = null;

          if (interact != null) {

            for (InterfaceBuilder inter : McTools.getService(InterfaceManager.class).getRegisteredInterfaces()) {
              for (InterfaceObjectClickable interfaceObjectClickable : inter.getAllObjectsClickable()) {
                if (interfaceObjectClickable.isInteraction(interact)) b = interfaceObjectClickable;
              }
            }

          }
          setSelected(b);
        }
        else setSelected(null);
      }
    });
  }

  //------SETTER-------//
  public void setSelected(InterfaceObjectClickable interfaceObjectClickable) {

    if (this.selected != null) {

      if (this.selected.equals(interfaceObjectClickable)) {

        if (interfaceObjectClickable != null) {
          try {

            if (interfaceObjectClickable.getInterfaceObjectAnimationManager().getPlayingAnimations().get(mtPlayer) != null)
              McTools.getService(GlowingEntities.class).setGlowing(interfaceObjectClickable.getInterfaceObjectAnimationManager().getPlayingAnimations().get(mtPlayer), mtPlayer.getPlayer());
          } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
          }
        }

        return;
      }

      if (interfaceObjectClickable != null) {
        try {
          if (interfaceObjectClickable.getInterfaceObjectAnimationManager().getPlayingAnimations().get(mtPlayer) != null)
            McTools.getService(GlowingEntities.class).unsetGlowing(interfaceObjectClickable.getInterfaceObjectAnimationManager().getPlayingAnimations().get(mtPlayer), mtPlayer.getPlayer());
        } catch (ReflectiveOperationException e) {
          throw new RuntimeException(e);
        }
      }

    }

    if (interfaceObjectClickable != null && interfaceObjectClickable.getInterfaceObjectAnimationManager().getAnimations().containsKey(McTools.getService(InterfaceManager.class).getAnimation(InterfaceAnimationType.RAYCAST))) {
      Display display = interfaceObjectClickable.getInterfaceObjectAnimationManager().setDisplay(this.mtPlayer);
      if (display != null) interfaceObjectClickable.getInterfaceObjectAnimationManager().getAnimations().get(McTools.getService(InterfaceManager.class).getAnimation(InterfaceAnimationType.RAYCAST)).play(mtPlayer, display, interfaceObjectClickable);
    }

    this.selected = interfaceObjectClickable;
  }

  public Interaction raycast(Player player, double maxDistance) {
    Location playerLocation = player.getEyeLocation();
    Vector playerDirection = playerLocation.getDirection().normalize();
    RayTraceResult result = playerLocation.getWorld().rayTrace(playerLocation, playerDirection, maxDistance, FluidCollisionMode.NEVER, true, 0.001, (entity) -> player.canSee(entity) && entity instanceof Interaction);
    return result != null && result.getHitEntity() instanceof Interaction ? (Interaction) result.getHitEntity() : null;
  }

}

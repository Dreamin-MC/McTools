package fr.dreamin.mctools.api.interfaces;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimation;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.api.service.Service;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class InterfaceManager extends Service implements Listener {

  @Getter private List<InterfaceBuilder> registeredInterfaces = new ArrayList<>();
  @Getter private List<InterfaceAnimation> allInterfaceAnimations = new ArrayList<>();


  @Override
  public void onEnable() {
    super.onEnable();

    allInterfaceAnimations.addAll(InterfaceAnimationType.getAllAnimations());

    McTools.getInstance().getServer().getPluginManager().registerEvents(this, McTools.getInstance());
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    //TODO SI IL EST PAS EN ANIMATION SHOW SI NON RIEN FAIRE
    this.registeredInterfaces.forEach(interfaceBuilder -> interfaceBuilder.getAllInterfaceObjects().forEach(interfaceObject -> interfaceObject.setShow(event.getPlayer())));
  }

  @EventHandler
  public void onInteractAtEntity(PlayerInteractEntityEvent event) {

    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(event.getPlayer());

    if (mtPlayer == null) return;

    if (event.getRightClicked() instanceof Interaction) {
      registeredInterfaces.forEach(interfaceBuilder -> {
        interfaceBuilder.getAllObjectsClickable().forEach(interfaceObject -> {
          if (interfaceObject.isInteraction((Interaction) event.getRightClicked())) {
            if (containsAnimation(interfaceObject, getAnimation(InterfaceAnimationType.CLICK))) {
              Display display = interfaceObject.getInterfaceObjectAnimationManager().setDisplay(mtPlayer);
              if (display != null) interfaceObject.getInterfaceObjectAnimationManager().getAnimations().get(getAnimation(InterfaceAnimationType.CLICK)).play(mtPlayer, display, interfaceObject);
            }
            interfaceBuilder.onClick(mtPlayer, interfaceObject, InterfaceClickType.INTERACT_ATT_ENTITY_RIGHT);
          }
        });
      });
    }
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(event.getPlayer());

    if (mtPlayer == null) return;

    if (event.getAction().isLeftClick()) {
      if (mtPlayer.getSelectObjectManager().getSelected() == null) return;
      if (containsAnimation(mtPlayer.getSelectObjectManager().getSelected(), getAnimation(InterfaceAnimationType.CLICK))) {
        Display display = mtPlayer.getSelectObjectManager().getSelected().getInterfaceObjectAnimationManager().setDisplay(mtPlayer);
        if (display != null) mtPlayer.getSelectObjectManager().getSelected().getInterfaceObjectAnimationManager().getAnimations().get(getAnimation(InterfaceAnimationType.CLICK)).play(mtPlayer, display, mtPlayer.getSelectObjectManager().getSelected());
      }
      mtPlayer.getSelectObjectManager().getSelected().getInterfaceBuilder().onClick(mtPlayer, mtPlayer.getSelectObjectManager().getSelected(), InterfaceClickType.INTERACT_LEFT);
    }
  }

  @EventHandler
  public void onDamage(EntityDamageByEntityEvent event) {

    Entity interact = event.getEntity();
    Entity player = event.getDamager();

    if (interact instanceof Interaction && player instanceof Player) {
      MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer((Player) player);

      if (mtPlayer == null) return;

      registeredInterfaces.forEach(interfaceBuilder -> {
        interfaceBuilder.getAllObjectsClickable().forEach(interfaceObject -> {
          if (interfaceObject.isInteraction((Interaction) interact)) {
            if (containsAnimation(interfaceObject, getAnimation(InterfaceAnimationType.CLICK))) {
              Display display = interfaceObject.getInterfaceObjectAnimationManager().setDisplay(mtPlayer);
              if (display != null) interfaceObject.getInterfaceObjectAnimationManager().getAnimations().get(getAnimation(InterfaceAnimationType.CLICK)).play(mtPlayer, display, interfaceObject);
            }
            interfaceBuilder.onClick(mtPlayer, interfaceObject, InterfaceClickType.DAMAGE_LEFT);
          }
        });
      });
    }
  }

  public void addInterface(InterfaceBuilder... i) {
    for (InterfaceBuilder inter : i) {
      getRegisteredInterfaces().add(inter);
    }
  }

  public void addAnimation(InterfaceAnimation animation) {
    this.allInterfaceAnimations.add(animation);
  }

  public InterfaceAnimation getAnimation(InterfaceAnimationType type) {
    for (InterfaceAnimation animation : getAllInterfaceAnimations()) {
      if (animation.getObject().equals(type)) return animation;
    }
    return null;
  }

  public InterfaceAnimation getAnimation(String type) {
    for (InterfaceAnimation animation : getAllInterfaceAnimations()) {
      if (animation.getObject().equals(type)) return animation;
    }
    return null;
  }

  public boolean containsAnimation(InterfaceObject object, InterfaceAnimation animation) {
    return object.getInterfaceObjectAnimationManager().gettAllInterfaceAnimations().contains(animation);
  }

  public void removeInterface(InterfaceBuilder inter) {
    getRegisteredInterfaces().remove(inter.getClass());
  }

}

package fr.dreamin.mctools.api.interfaces;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObjectClickable;
import fr.dreamin.mctools.api.service.Service;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.interfaces.Test;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import org.bukkit.entity.Display;
import org.bukkit.entity.Interaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class InterfaceManager extends Service implements Listener {

  @Getter private List<InterfaceBuilder> registeredInterfaces = new ArrayList<>();

  @Override
  public void onEnable() {
    super.onEnable();

    McTools.getInstance().getServer().getPluginManager().registerEvents(this, McTools.getInstance());

    registeredInterfaces.add(new Test());
  }

  @EventHandler
  public void onInteractAtEntity(PlayerInteractEntityEvent event) {

    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(event.getPlayer());

    if (mtPlayer == null) return;

    if (event.getRightClicked() instanceof Interaction) {
      registeredInterfaces.forEach(interfaceBuilder -> {
        getAllObjectsClickable(interfaceBuilder).forEach(interfaceObject -> {
          if (interfaceObject.isInteraction((Interaction) event.getRightClicked())) {
            if (interfaceObject.getInterfaceObjectAnimationManager().getAnimations().containsKey(InterfaceAnimationType.CLICK)) {
              Display display = interfaceObject.getInterfaceObjectAnimationManager().setDisplay(mtPlayer);
              if (display != null) interfaceObject.getInterfaceObjectAnimationManager().getAnimations().get(InterfaceAnimationType.CLICK).play(mtPlayer, display, interfaceObject);
              interfaceBuilder.onClick(mtPlayer, interfaceObject, InterfaceClickType.INTERACT_ATT_ENTITY_RIGHT);
            }
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
      if (mtPlayer.getSelectObjectManager().getSelected().getInterfaceObjectAnimationManager().getAnimations().containsKey(InterfaceAnimationType.CLICK)) {
        Display display = mtPlayer.getSelectObjectManager().getSelected().getInterfaceObjectAnimationManager().setDisplay(mtPlayer);
        if (display != null) mtPlayer.getSelectObjectManager().getSelected().getInterfaceObjectAnimationManager().getAnimations().get(InterfaceAnimationType.CLICK).play(mtPlayer, display, mtPlayer.getSelectObjectManager().getSelected());
        mtPlayer.getSelectObjectManager().getSelected().getInterfaceBuilder().onClick(mtPlayer, mtPlayer.getSelectObjectManager().getSelected(), InterfaceClickType.INTERACT_LEFT);
      }
    }
  }

  @EventHandler
  public void onDamage(EntityDamageEvent event) {

  }

  public void addInterface(InterfaceBuilder... i) {
    for (InterfaceBuilder inter : i) {
      getRegisteredInterfaces().add(inter);
    }
  }

  public void removeInterface(InterfaceBuilder inter) {
    getRegisteredInterfaces().remove(inter.getClass());
  }

  public void show(Class<? extends InterfaceBuilder> iClass) {



  }

  public List<InterfaceObject> getAllObjects(InterfaceBuilder interfaceBuilder) {
    List<InterfaceObject> objects = new ArrayList<>();

    objects.addAll(interfaceBuilder.getShowAll());
    interfaceBuilder.getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> objects.addAll(targetObjects));
    interfaceBuilder.getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> objects.addAll(targetObjects));
    return objects;

  }

  public List<InterfaceObjectClickable> getAllObjectsClickable(InterfaceBuilder interfaceBuilder) {
    List<InterfaceObjectClickable> objects = new ArrayList<>();

    interfaceBuilder.getShowAll().forEach(targetObject -> {if (targetObject instanceof InterfaceObjectClickable) objects.add((InterfaceObjectClickable) targetObject); });
    interfaceBuilder.getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> {targetObjects.forEach(targerObject -> {if (targerObject instanceof InterfaceObjectClickable) objects.add((InterfaceObjectClickable) targerObject);});});
    interfaceBuilder.getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> {targetObjects.forEach(targerObject -> {if (targerObject instanceof InterfaceObjectClickable) objects.add((InterfaceObjectClickable) targerObject);});});
    return objects;

  }

}

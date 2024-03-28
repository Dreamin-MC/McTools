package fr.dreamin.mctools.api.interfaces;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObjectClickable;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterfaceBuilder {

  @Getter private Location location;
  @Getter private InterfaceObject backGround;

  @Getter private boolean isVisible = true;

  @Getter private List<InterfaceObject> showAll = new ArrayList<>();
  @Getter private HashMap<MTPlayer, List<InterfaceObject>> showSpecificPlayer = new HashMap<>();
  @Getter private HashMap<List<MTPlayer>, List<InterfaceObject>> showSpecificListPlayer = new HashMap<>();


  public InterfaceBuilder(Location location) {
    this.location = location;
  }

  public void onClick(MTPlayer mtPlayer, InterfaceObjectClickable interfaceObjectClickable, InterfaceClickType clickType) {

  }

  public List<InterfaceObject> getAllInterfaceObjects() {
    List<InterfaceObject> list = new ArrayList<>();

    list.addAll(showAll);
    showSpecificPlayer.forEach((targetPlayer, targetObjects) -> list.addAll(targetObjects));
    showSpecificListPlayer.forEach((targetPlayers, targetObjects) -> list.addAll(targetObjects));
    return list;
  }

  public List<InterfaceObjectClickable> getAllObjectsClickable() {
    List<InterfaceObjectClickable> objects = new ArrayList<>();

    getShowAll().forEach(targetObject -> {if (targetObject instanceof InterfaceObjectClickable) objects.add((InterfaceObjectClickable) targetObject); });
    getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> {targetObjects.forEach(targerObject -> {if (targerObject instanceof InterfaceObjectClickable) objects.add((InterfaceObjectClickable) targerObject);});});
    getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> {targetObjects.forEach(targerObject -> {if (targerObject instanceof InterfaceObjectClickable) objects.add((InterfaceObjectClickable) targerObject);});});
    return objects;
  }

  private void showAll() {

    McTools.getService(PlayersService.class).getMtPlayers().forEach((mtPlayer -> {
//      if (this.backGround != null) show(mtPlayer, this.backGround); voir comment on peut faire ça
      getShowAll().forEach(targetObject -> show(mtPlayer, targetObject));
    }));

    getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> targetObjects.forEach(targetObject -> {if (!targetPlayer.getPlayer().canSee(targetObject.getDisplayEntity())) show(targetPlayer, targetObject);}));
    getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> targetPlayers.forEach(targetPlayer -> targetObjects.forEach(targetObject -> {if (!targetPlayer.getPlayer().canSee(targetObject.getDisplayEntity())) show(targetPlayer, targetObject);})));

  }

  private void hideAll() {
    McTools.getService(PlayersService.class).getMtPlayers().forEach((mtPlayer -> {
      if (this.backGround != null) show(mtPlayer, this.backGround);
      getShowAll().forEach(targetObject -> {if (mtPlayer.getPlayer().canSee(targetObject.getDisplayEntity())) hide(mtPlayer, targetObject);});
    }));

    getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> targetObjects.forEach(targetObject -> {if (targetPlayer.getPlayer().canSee(targetObject.getDisplayEntity())) hide(targetPlayer, targetObject);}));
    getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> targetPlayers.forEach(targetPlayer -> targetObjects.forEach(targetObject -> {if (targetPlayer.getPlayer().canSee(targetObject.getDisplayEntity())) hide(targetPlayer, targetObject);})));
  }

  private void show(MTPlayer mtPlayer, InterfaceObject object) {
    mtPlayer.getPlayer().showEntity(McTools.getInstance(), object.getDisplayEntity());
  }

  private void hide(MTPlayer mtPlayer, InterfaceObject object) {
    mtPlayer.getPlayer().hideEntity(McTools.getInstance(), object.getDisplayEntity());
  }

  public void setVisible(boolean visible) {
    this.isVisible = visible;
    if (this.isVisible) showAll();
    else hideAll();
  }



}

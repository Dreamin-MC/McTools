package fr.dreamin.mctools.components.interfaces;

import eu.decentsoftware.holograms.api.animations.Animation;
import fr.dreamin.mctools.api.interfaces.InterfaceBuilder;
import fr.dreamin.mctools.api.interfaces.InterfaceClickType;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObjectClickable;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test implements InterfaceBuilder {

  @Override
  public InterfaceObject getBackGround() {
    return null;
  }

  @Override
  public List<InterfaceObject> getShowAll() {

    List<InterfaceObject> interfaceObjects = new ArrayList<>();

    ItemDisplay display = (ItemDisplay) getLocation().getWorld().spawnEntity(getLocation().clone().add(0, 0, 0), EntityType.ITEM_DISPLAY);

    display.setItemStack(new ItemBuilder(Material.PAPER).setCustomMeta(1).toItemStack());

    HashMap<InterfaceAnimationType, InterfaceAnimationBuilder> animations = new HashMap<>();

    interfaceObjects.add(new InterfaceObject(
      this,
      getLocation().clone().add(0, 0, 0),
      display,
      1,1,
      animations
    ));

    return interfaceObjects;
  }

  @Override
  public HashMap<MTPlayer, List<InterfaceObject>> getShowSpecificPlayer() {

    HashMap<MTPlayer, List<InterfaceObject>> playerListHashMap = new HashMap<>();

    return playerListHashMap;
  }

  @Override
  public HashMap<List<MTPlayer>, List<InterfaceObject>> getShowSpecificListPlayer() {
    HashMap<List<MTPlayer>, List<InterfaceObject>> playerListHashMap = new HashMap<>();

    return playerListHashMap;
  }

  @Override
  public Location getLocation() {
    return new Location(Bukkit.getWorld("buildkibogamine"), 980 , 4, 1112) ;
  }

  @Override
  public void onClick(MTPlayer mtPlayer, InterfaceObjectClickable interfaceObjectClickable, InterfaceClickType clickType) {

  }
}

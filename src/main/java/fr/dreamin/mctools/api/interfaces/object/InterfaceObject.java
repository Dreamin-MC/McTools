package fr.dreamin.mctools.api.interfaces.object;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.InterfaceBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.manager.InterfaceObjectAnimationManager;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterfaceObject {

  @Getter @Setter private Location location;
  @Getter @Setter private Display displayEntity;

  @Getter private InterfaceBuilder interfaceBuilder;

  @Getter private final InterfaceObjectAnimationManager interfaceObjectAnimationManager;

  public InterfaceObject(InterfaceBuilder interfaceBuilder, Location location, Display displayEntity, float yaw, float pitch, HashMap<InterfaceAnimationType, InterfaceAnimationBuilder> animations) {
    this.interfaceBuilder = interfaceBuilder;

    this.location = location.clone();
    this.location.setYaw(this.location.getYaw() + yaw);
    this.location.setPitch(this.location.getPitch() + pitch);

    this.displayEntity = displayEntity;
    this.displayEntity.teleport(this.location);

    setVisibility(displayEntity);


    this.interfaceObjectAnimationManager = new InterfaceObjectAnimationManager(this, animations);
  }

  public List<MTPlayer> getAllPlayerShowObject() {

    List<MTPlayer> allPlayers = new ArrayList<>();

    if (this.interfaceBuilder.getShowAll().contains(this)) allPlayers.addAll(McTools.getService(PlayersService.class).getMtPlayers());
    else {
      this.interfaceBuilder.getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> {if (targetObjects.contains(this)) allPlayers.add(targetPlayer);});
      this.interfaceBuilder.getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> {if (targetObjects.contains(this)) allPlayers.addAll(targetPlayers);});
    }

    return allPlayers;
  }

  public void setVisibility(Display displayEntity) {

    if (this.interfaceBuilder.getShowAll().contains(this)) displayEntity.setVisibleByDefault(true);
    else {
      this.interfaceBuilder.getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> {
        if (targetObjects.contains(this)) displayEntity.setVisibleByDefault(false);
        targetPlayer.getPlayer().showEntity(McTools.getInstance(), displayEntity);
      });
      this.interfaceBuilder.getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> {
        if (targetObjects.contains(this)) displayEntity.setVisibleByDefault(false);

        targetPlayers.forEach(targetPlayer -> targetPlayer.getPlayer().showEntity(McTools.getInstance(), displayEntity));
      });
    }

  }


}

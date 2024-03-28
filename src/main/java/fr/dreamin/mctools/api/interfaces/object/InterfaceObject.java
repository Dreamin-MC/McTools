package fr.dreamin.mctools.api.interfaces.object;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.InterfaceBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimation;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.manager.InterfaceObjectAnimationManager;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterfaceObject {

  @Getter @Setter private Location location;
  @Getter @Setter private Display displayEntity;

  @Getter private InterfaceBuilder interfaceBuilder;

  @Getter private InterfaceObjectAnimationManager interfaceObjectAnimationManager = null;

  public InterfaceObject(InterfaceBuilder interfaceBuilder, Location location , float yaw, float pitch, EntityType entityType, HashMap<InterfaceAnimation, InterfaceAnimationBuilder> animations) {
    if (entityType == null) return;
    if (entityType.equals(EntityType.ITEM_DISPLAY) || entityType.equals(EntityType.TEXT_DISPLAY) || entityType.equals(EntityType.BLOCK_DISPLAY))  {
      this.interfaceBuilder = interfaceBuilder;

      this.location = location.clone();
      this.location.setYaw(this.location.getYaw() + yaw);
      this.location.setPitch(this.location.getPitch() + pitch);

      this.displayEntity = (Display) this.location.getWorld().spawnEntity(this.location, entityType);

      setVisibility();

      this.interfaceObjectAnimationManager = new InterfaceObjectAnimationManager(this, animations);
    }
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

  public void setVisibility() {
    this.interfaceBuilder.getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> {
      if (targetObjects.contains(this)) this.displayEntity.setVisibleByDefault(false);
      targetPlayer.getPlayer().showEntity(McTools.getInstance(), this.displayEntity);
    });
    this.interfaceBuilder.getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> {
      if (targetObjects.contains(this)) this.displayEntity.setVisibleByDefault(false);
      targetPlayers.forEach(targetPlayer -> targetPlayer.getPlayer().showEntity(McTools.getInstance(), this.displayEntity));
    });
  }

  public void setShow(Player player) {
    this.interfaceBuilder.getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> {if (targetPlayer.getPlayer().equals(player) && targetObjects.contains(this)) player.showEntity(McTools.getInstance(), this.displayEntity);});
    this.interfaceBuilder.getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> {if (targetPlayers.contains(player) && targetObjects.contains(this)) player.showEntity(McTools.getInstance(), this.displayEntity);});
  }

  public void setHide(Player player) {
    this.interfaceBuilder.getShowSpecificPlayer().forEach((targetPlayer, targetObjects) -> {if (targetPlayer.getPlayer().equals(player) && targetObjects.contains(this)) player.hideEntity(McTools.getInstance(), this.displayEntity);});
    this.interfaceBuilder.getShowSpecificListPlayer().forEach((targetPlayers, targetObjects) -> {if (targetPlayers.contains(player) && targetObjects.contains(this)) player.hideEntity(McTools.getInstance(), this.displayEntity);});
  }


}

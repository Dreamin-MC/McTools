package fr.dreamin.mctools.api.interfaces.object.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.InterfaceManager;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimation;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.List;

public class InterfaceObjectAnimationManager {

  @Getter private final InterfaceObject interfaceObject;

  @Getter private HashMap<InterfaceAnimation, InterfaceAnimationBuilder> animations = new HashMap<>();
  @Getter private HashMap<MTPlayer, Display> playingAnimations = new HashMap<>();

  public InterfaceObjectAnimationManager(InterfaceObject interfaceObject, HashMap<InterfaceAnimation, InterfaceAnimationBuilder> animations) {
    this.interfaceObject = interfaceObject;
    this.animations = animations;

    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), this::setIdleAnimation, 5);
  }
  
  public void setIdleAnimation() {
    if (!this.animations.containsKey(McTools.getService(InterfaceManager.class).getAnimation(InterfaceAnimationType.IDLE))) return;

    InterfaceAnimationBuilder animation = this.animations.get(McTools.getService(InterfaceManager.class).getAnimation(InterfaceAnimationType.IDLE));
    if (animation == null) return;

    McTools.getService(PlayersService.class).getMtPlayers().forEach(mtPlayer -> {
      Display display = setDisplay(mtPlayer);
      if (display != null) animation.play(mtPlayer, display, this.interfaceObject);
    });

//    getInterfaceObject().getAllPlayerShowObject().forEach(mtPlayer -> {
//      Display display = setDisplay(mtPlayer);
//      if (display != null) animation.play(mtPlayer, display, this.interfaceObject);
//    });

  }

  public Display setDisplay(MTPlayer mtPlayer) {

    Display display = null;

    if (this.playingAnimations.containsKey(mtPlayer)) display = this.playingAnimations.get(mtPlayer);
    else {
      if (this.interfaceObject.getDisplayEntity() instanceof ItemDisplay) {

        ItemDisplay itemDisplay = (ItemDisplay) this.interfaceObject.getDisplayEntity().getLocation().getWorld().spawnEntity(this.interfaceObject.getDisplayEntity().getLocation().clone(), EntityType.ITEM_DISPLAY);
        if (!this.interfaceObject.getDisplayEntity().isVisibleByDefault()) itemDisplay.setVisibleByDefault(false);
        itemDisplay.setItemStack(((ItemDisplay) this.interfaceObject.getDisplayEntity()).getItemStack());

        display = itemDisplay;
      }
      else if (this.interfaceObject.getDisplayEntity() instanceof TextDisplay) {

        TextDisplay textDisplay = (TextDisplay) this.interfaceObject.getDisplayEntity().getLocation().getWorld().spawnEntity(this.interfaceObject.getDisplayEntity().getLocation().clone(), EntityType.TEXT_DISPLAY);
        if (!this.interfaceObject.getDisplayEntity().isVisibleByDefault()) textDisplay.setVisibleByDefault(false);
        textDisplay.setText(((TextDisplay) this.interfaceObject.getDisplayEntity()).getText());

        display = textDisplay;
      }
      else if (this.interfaceObject.getDisplayEntity() instanceof BlockDisplay) {

        BlockDisplay blockDisplay = (BlockDisplay) this.interfaceObject.getDisplayEntity().getLocation().getWorld().spawnEntity(this.interfaceObject.getDisplayEntity().getLocation().clone(), EntityType.TEXT_DISPLAY);
        if (!this.interfaceObject.getDisplayEntity().isVisibleByDefault()) blockDisplay.setVisibleByDefault(false);
        blockDisplay.setBlock(((BlockDisplay) this.interfaceObject.getDisplayEntity()).getBlock());

        display = blockDisplay;
      }
    }
    return display;
  }

  public List<InterfaceAnimation> gettAllInterfaceAnimations() {
    return this.animations.keySet().stream().toList();
  }

}

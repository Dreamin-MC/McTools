package fr.dreamin.mctools.api.interfaces.object.manager;

import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import org.bukkit.entity.*;

import java.util.HashMap;

public class InterfaceObjectAnimationManager {

  @Getter private final InterfaceObject interfaceObject;

  @Getter private HashMap<InterfaceAnimationType, InterfaceAnimationBuilder> animations = new HashMap<>();
  @Getter private HashMap<MTPlayer, Display> playingAnimations = new HashMap<>();

  public InterfaceObjectAnimationManager(InterfaceObject interfaceObject, HashMap<InterfaceAnimationType, InterfaceAnimationBuilder> animations) {
    this.interfaceObject = interfaceObject;
    this.animations = animations;
    
    setIdleAnimation();
  }
  
  public void setIdleAnimation() {
    
    if (!this.animations.containsKey(InterfaceAnimationType.IDLE)) return;
    
    InterfaceAnimationBuilder animation = this.animations.get(InterfaceAnimationType.IDLE);
    if (animation == null) return;

    getInterfaceObject().getAllPlayerShowObject().forEach(mtPlayer -> {
      Display display = setDisplay(mtPlayer);
      if (display != null) animation.play(mtPlayer, display, this.interfaceObject);
    });
  }

  public Display setDisplay(MTPlayer mtPlayer) {

    Display display = null;

    if (this.playingAnimations.containsKey(mtPlayer)) display = this.playingAnimations.get(mtPlayer);
    else {
      if (this.interfaceObject.getDisplayEntity() instanceof ItemDisplay) {

        ItemDisplay itemDisplay = (ItemDisplay) this.interfaceObject.getDisplayEntity().getLocation().getWorld().spawnEntity(this.interfaceObject.getDisplayEntity().getLocation().clone(), EntityType.ITEM_DISPLAY);
        itemDisplay.setVisibleByDefault(this.interfaceObject.getDisplayEntity().isVisibleByDefault());
        itemDisplay.setItemStack(((ItemDisplay) this.interfaceObject.getDisplayEntity()).getItemStack());

        display = itemDisplay;
      }
      else if (this.interfaceObject.getDisplayEntity() instanceof TextDisplay) {

        TextDisplay textDisplay = (TextDisplay) this.interfaceObject.getDisplayEntity().getLocation().getWorld().spawnEntity(this.interfaceObject.getDisplayEntity().getLocation().clone(), EntityType.TEXT_DISPLAY);
        textDisplay.setVisibleByDefault(this.interfaceObject.getDisplayEntity().isVisibleByDefault());
        textDisplay.setText(((TextDisplay) this.interfaceObject.getDisplayEntity()).getText());

        display = textDisplay;
      }
      else if (this.interfaceObject.getDisplayEntity() instanceof BlockDisplay) {

        BlockDisplay blockDisplay = (BlockDisplay) this.interfaceObject.getDisplayEntity().getLocation().getWorld().spawnEntity(this.interfaceObject.getDisplayEntity().getLocation().clone(), EntityType.TEXT_DISPLAY);
        blockDisplay.setVisibleByDefault(this.interfaceObject.getDisplayEntity().isVisibleByDefault());
        blockDisplay.setBlock(((BlockDisplay) this.interfaceObject.getDisplayEntity()).getBlock());

        display = blockDisplay;
      }
    }
    return display;
  }

}

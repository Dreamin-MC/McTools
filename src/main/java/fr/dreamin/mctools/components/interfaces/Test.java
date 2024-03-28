package fr.dreamin.mctools.components.interfaces;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.interfaces.InterfaceBuilder;
import fr.dreamin.mctools.api.interfaces.InterfaceClickType;
import fr.dreamin.mctools.api.interfaces.InterfaceManager;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimation;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import fr.dreamin.mctools.api.interfaces.animation.dflAnimation.ClickAnimation;
import fr.dreamin.mctools.api.interfaces.animation.dflAnimation.RayCastAnimation;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObject;
import fr.dreamin.mctools.api.interfaces.object.InterfaceObjectClickable;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Test extends InterfaceBuilder {

  @Getter private InterfaceObject TpAwaitHotButton;
  @Getter private InterfaceObject arrowPreviousTPAwaitButton;
  @Getter private InterfaceObject validateTPAwaitButton;
  @Getter private InterfaceObject arrowNextTPAwaitButton;

  @Getter private ItemStack itemTPAwait = new ItemBuilder(Material.COAL).setCustomMeta(2).toItemStack();

  public Test(Location location) {
    super(location);

    HashMap<InterfaceAnimation, InterfaceAnimationBuilder> animations = new HashMap<>();

    animations.put(McTools.getService(InterfaceManager.class).getAnimation(InterfaceAnimationType.RAYCAST), new RayCastAnimation());
    animations.put(McTools.getService(InterfaceManager.class).getAnimation(InterfaceAnimationType.CLICK), new ClickAnimation());

    TpAwaitHotButton = new InterfaceObjectClickable(this, location.clone().add(-1, 0, 0),90, 0, EntityType.ITEM_DISPLAY, 2f,3f,1, animations);
    arrowPreviousTPAwaitButton = new InterfaceObjectClickable(this, location.clone().add(-2.5, -1, 0),90, 0, EntityType.ITEM_DISPLAY, 1,1,1, animations);
    validateTPAwaitButton = new InterfaceObjectClickable(this, location.clone().add(-1, -1, 0),90, 0, EntityType.ITEM_DISPLAY, 1,1,1, animations);
    arrowNextTPAwaitButton = new InterfaceObjectClickable(this, location.clone().add(0.5, -1, 0),90, 0, EntityType.ITEM_DISPLAY, 1,1,1, animations);

    getShowAll().add(TpAwaitHotButton);
    getShowAll().add(arrowPreviousTPAwaitButton);
    getShowAll().add(validateTPAwaitButton);
    getShowAll().add(arrowNextTPAwaitButton);

    Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {
      ((ItemDisplay) TpAwaitHotButton.getDisplayEntity()).setItemStack(itemTPAwait);
      ((ItemDisplay) arrowPreviousTPAwaitButton.getDisplayEntity()).setItemStack(new ItemBuilder(Material.PAPER).setCustomMeta(8).toItemStack());
      ((ItemDisplay) validateTPAwaitButton.getDisplayEntity()).setItemStack(new ItemBuilder(Material.PAPER).setCustomMeta(9).toItemStack());
      ((ItemDisplay) arrowNextTPAwaitButton.getDisplayEntity()).setItemStack(new ItemBuilder(Material.PAPER).setCustomMeta(7).toItemStack());
    }, 1);
  }

  @Override
  public void onClick(MTPlayer mtPlayer, InterfaceObjectClickable interfaceObjectClickable, InterfaceClickType clickType) {
    super.onClick(mtPlayer, interfaceObjectClickable, clickType);



  }
}

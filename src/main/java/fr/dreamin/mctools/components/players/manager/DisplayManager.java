package fr.dreamin.mctools.components.players.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DisplayManager {

  @Getter private MTPlayer mtPlayer;

  private List<Display> displaySelected = new ArrayList<>();
  private List<Display> displayLocked = new ArrayList<>();
  @Getter private List<Display> displayRadius = new ArrayList<>();

  @Getter private List<DisplayScaleType> displayScaleType = new ArrayList<>();

  @Getter @Setter private DisplayType displayTypeSelected = DisplayType.ALL;
  @Getter @Setter private DisplayType displayTypeLocked = DisplayType.ALL;

  @Getter @Setter private double distanceMoveDisplay = 1;
  @Getter @Setter private float displayRotation = (float)45;
  @Getter @Setter private float displayScale = (float)1;

  @Getter @Setter private boolean isInvisibleGui = false;

  public DisplayManager(MTPlayer player) {
    this.mtPlayer = player;
  }

  public List<ItemStack> getDisplaySelectedStack() {
    List<ItemStack> stack = new ArrayList<>();

    for (Display display : displaySelected) {
      ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);

      switch (this.displayTypeSelected) {
        case ITEM:
          if (display instanceof ItemDisplay) {
            if (((ItemDisplay) display).getItemStack() != null && !((ItemDisplay) display).getItemStack().getType().equals(Material.AIR)) itemBuilder = new ItemBuilder(((ItemDisplay) display).getItemStack());
            else itemBuilder = new ItemBuilder(Material.PAPER);
            stack.add(itemBuilder.toItemStack());
          }
          break;
        case BLOCK:
          if (display instanceof BlockDisplay) {
            if (((BlockDisplay) display).getBlock() != null) itemBuilder = new ItemBuilder(((BlockDisplay) display).getBlock().getMaterial());
            else itemBuilder = new ItemBuilder(Material.STONE);
            stack.add(itemBuilder.toItemStack());
          }
          break;
        case TEXT:
          if (display instanceof TextDisplay) {
            if (((TextDisplay) display).getText() != null) itemBuilder = new ItemBuilder(Material.ACACIA_SIGN);
            else itemBuilder = new ItemBuilder(Material.OAK_SIGN);
            stack.add(itemBuilder.toItemStack());
          }
          break;
        case ALL:
          if (display instanceof ItemDisplay) {
            if (((ItemDisplay) display).getItemStack() != null && !((ItemDisplay) display).getItemStack().getType().equals(Material.AIR)) itemBuilder = new ItemBuilder(((ItemDisplay) display).getItemStack());
            else itemBuilder = new ItemBuilder(Material.PAPER);
          }
          else if (display instanceof BlockDisplay) {
            if (((BlockDisplay) display).getBlock() != null) itemBuilder = new ItemBuilder(((BlockDisplay) display).getBlock().getMaterial());
            else itemBuilder = new ItemBuilder(Material.STONE);
          }
          else if (display instanceof TextDisplay) {
            if (((TextDisplay) display).getText() != null) itemBuilder = new ItemBuilder(Material.ACACIA_SIGN);
            else itemBuilder = new ItemBuilder(Material.OAK_SIGN);
          }
          stack.add(itemBuilder.toItemStack());
          break;
      }

    }

    return stack;
  }

  public void addDisplaySelected(Display display) {
    this.displaySelected.add(display);

    try {
      McTools.getService(GlowingEntities.class).setGlowing(display, mtPlayer.getPlayer(), ChatColor.WHITE);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }

  }

  public void addAllDisplaySelected(List<Display> displays) {

    for (Display display : displays) {
      try {
        McTools.getService(GlowingEntities.class).setGlowing(display, mtPlayer.getPlayer(), ChatColor.WHITE);
        this.displaySelected.add(display);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void removeDisplaySelected(Display display, boolean removeGlowing) {
    if (removeGlowing) {
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }

    this.displaySelected.remove(display);
  }

  public void removeAllDisplaySelected(DisplayType type, boolean removeGlowing) {

    List<Display> displayList = new ArrayList<>(displaySelected);

    for (Display display : displayList) {
      switch (type) {
        case TEXT:
          if (display instanceof TextDisplay) {
            this.displaySelected.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;
        case ITEM:
          if (display instanceof ItemDisplay) {
            this.displaySelected.remove(display);

            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;
        case BLOCK:
          if (display instanceof BlockDisplay) {
            this.displaySelected.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;
        case ALL:
          this.displaySelected.remove(display);
          try {
            if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
          } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
          }
          break;
      }
    }

    if (type.equals(DisplayType.ALL)) {
      //TODO set page 1;
    }
  }

  public void dispawnDisplaySelected(Display display) {
    this.displaySelected.remove(display);
    display.remove();
  }

  public void dispawnAllDisplaySelected() {
    for (Display display : displaySelected) {
      display.remove();
    }
    this.displaySelected.clear();
    //TODO set page 1;
  }

  public List<ItemStack> getDisplayRadiusStack() {
    List<ItemStack> stack = new ArrayList<>();

    for (Display display : displayRadius) {
      ItemBuilder itemBuilder = null;

      if (display instanceof ItemDisplay) {
        if (((ItemDisplay) display).getItemStack() != null && !((ItemDisplay) display).getItemStack().getType().equals(Material.AIR)) itemBuilder = new ItemBuilder(((ItemDisplay) display).getItemStack());
        else itemBuilder = new ItemBuilder(Material.PAPER);
      }
      else if (display instanceof BlockDisplay) {
        if (((BlockDisplay) display).getBlock() != null) itemBuilder = new ItemBuilder(((BlockDisplay) display).getBlock().getMaterial());
        else itemBuilder = new ItemBuilder(Material.STONE);
      }
      else if (display instanceof TextDisplay) {
        if (((TextDisplay) display).getText() != null) itemBuilder = new ItemBuilder(Material.ACACIA_SIGN);
        else itemBuilder = new ItemBuilder(Material.OAK_SIGN);
      }
      stack.add(itemBuilder.setLore(
          "§fMap : " + display.getWorld().getName(),
          "§fx : " + display.getLocation().getX(),
          "§fy : " + display.getLocation().getY(),
          "§fz : " + display.getLocation().getZ(),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_LEFTCLICKADD, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_RIGHTCLICKREMOVE, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTLEFTCLICKTP, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTRIGHTCLICKSHOW, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_CLICKWHEELMODEL, ""))
        .toItemStack());
    }
    return stack;
  }

  public void addDisplayRadius(Display display) {
    this.displayRadius.add(display);
  }

  public void addAllDisplayRadius(List<Display> displays) {
    for (Display display : displays) {
      this.displayRadius.add(display);
    }
  }

  public void removeDisplayRadius(Display display, boolean removeGlowing) {
    if (removeGlowing) {
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }

    this.displayRadius.remove(display);
  }

  public void removeAllDisplayRadius(DisplayType type, boolean removeGlowing) {

    List<Display> displayList = new ArrayList<>(displayRadius);

    for (Display display : displayList) {
      switch (type) {
        case TEXT:

          if (display instanceof TextDisplay) {
            this.displayRadius.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;

        case ITEM:
          if (display instanceof ItemDisplay) {
            this.displayRadius.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;

        case BLOCK:
          if (display instanceof BlockDisplay) {
            this.displayRadius.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;
        case ALL:
          this.displayRadius.remove(display);
          try {
            if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
          } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
          }
          break;
      }
    }

    if (type.equals(DisplayType.ALL)) {
      //TODO set page 1;
    }
  }

  public void dispawnDisplayRadius(Display display) {
    this.displayRadius.remove(display);
    display.remove();
  }

  public void dispawnAllDisplayRadius() {
    for (Display display : displayRadius) {
      display.remove();
    }
    this.displayRadius.clear();
    //TODO set page 1;
  }

  public List<ItemStack> getDisplayLockedStack() {
    List<ItemStack> stack = new ArrayList<>();

    for (Display display : displayLocked) {
      ItemBuilder itemBuilder = null;

      switch (displayTypeLocked) {
        case ITEM:
          if (display instanceof ItemDisplay) {
            if (((ItemDisplay) display).getItemStack() != null && !((ItemDisplay) display).getItemStack().getType().equals(Material.AIR)) itemBuilder = new ItemBuilder(((ItemDisplay) display).getItemStack());
            else itemBuilder = new ItemBuilder(Material.PAPER);
          }
          stack.add(itemBuilder.toItemStack());
          break;
        case BLOCK:
          if (display instanceof BlockDisplay) {
            if (((BlockDisplay) display).getBlock() != null) itemBuilder = new ItemBuilder(((BlockDisplay) display).getBlock().getMaterial());
            else itemBuilder = new ItemBuilder(Material.STONE);
          }
          stack.add(itemBuilder.toItemStack());
          break;
        case TEXT:
          if (display instanceof TextDisplay) {
            if (((TextDisplay) display).getText() != null) itemBuilder = new ItemBuilder(Material.ACACIA_SIGN);
            else itemBuilder = new ItemBuilder(Material.OAK_SIGN);
          }
          stack.add(itemBuilder.toItemStack());
          break;
        case ALL:
          if (display instanceof ItemDisplay) {
            if (((ItemDisplay) display).getItemStack() != null && !((ItemDisplay) display).getItemStack().getType().equals(Material.AIR)) itemBuilder = new ItemBuilder(((ItemDisplay) display).getItemStack());
            else itemBuilder = new ItemBuilder(Material.PAPER);
          }
          else if (display instanceof BlockDisplay) {
            if (((BlockDisplay) display).getBlock() != null) itemBuilder = new ItemBuilder(((BlockDisplay) display).getBlock().getMaterial());
            else itemBuilder = new ItemBuilder(Material.STONE);
          }
          else if (display instanceof TextDisplay) {
            if (((TextDisplay) display).getText() != null) itemBuilder = new ItemBuilder(Material.ACACIA_SIGN);
            else itemBuilder = new ItemBuilder(Material.OAK_SIGN);
          }
          stack.add(itemBuilder.toItemStack());
          break;
      }
    }
    return stack;
  }

  public void addDisplayLocked(Display display) {
    this.displayLocked.add(display);

    try {
      McTools.getService(GlowingEntities.class).setGlowing(display, mtPlayer.getPlayer(), ChatColor.RED);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  public void addAllDisplayLocked(List<Display> displays) {
    for (Display display : displays) {
      try {
        McTools.getService(GlowingEntities.class).setGlowing(display, mtPlayer.getPlayer(), ChatColor.RED);
        this.displayLocked.add(display);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void removeDisplayLocked(Display display, boolean removeGlowing) {
    if (removeGlowing) {
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }

    this.displayLocked.remove(display);
  }

  public void removeAllDisplayLocked(DisplayType type, boolean removeGlowing) {

    for (Display display : displayLocked) {
      switch (type) {
        case TEXT:
          if (display instanceof TextDisplay) {
            this.displayLocked.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;
        case ITEM:
          if (display instanceof ItemDisplay) {
            this.displayLocked.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;
        case BLOCK:
          if (display instanceof BlockDisplay) {
            this.displayLocked.remove(display);
            try {
              if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          break;
        case ALL:
          this.displayLocked.remove(display);
          try {
            if (removeGlowing) McTools.getService(GlowingEntities.class).unsetGlowing(display, mtPlayer.getPlayer());
          } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
          }
          break;
      }


    }

    if (type.equals(DisplayType.ALL)) {
      //TODO set page 1;
    }
  }

  public void dispawnDisplayLocked(Display display) {
    this.displayLocked.remove(display);
    display.remove();
  }

  public void dispawnAllDisplayLocked() {
    for (Display display : displayLocked) {
      display.remove();
    }
    this.displayLocked.clear();
    //TODO set page 1;
  }

  public Display getIfDisplaySelected(Display armorStand) {
    return this.displaySelected.stream().filter(armorStand1 -> armorStand1.equals(armorStand)).findFirst().orElse(null);
  }
  public Display getIfDisplayLocked(Display armorStand) {
    return this.displayLocked.stream().filter(armorStand1 -> armorStand1.equals(armorStand)).findFirst().orElse(null);
  }

  public List<Display> getDisplayType(DisplayType type, List<Display> displays) {
    List<Display> display = new ArrayList<>();

    for (Display d : displays) {
      switch (type) {
        case ITEM:
          if (d instanceof ItemDisplay) display.add(d);
          break;
        case BLOCK:
          if (d instanceof BlockDisplay) display.add(d);
          break;
        case TEXT:
          if (d instanceof TextDisplay) display.add(d);
          break;
        case ALL:
          display.add(d);
          break;
      }
    }

    return display;
  }

  public void updateDisplayScaleType(DisplayScaleType scaleType) {
    if (this.displayScaleType.contains(scaleType)) this.displayScaleType.remove(scaleType);
    else this.displayScaleType.add(scaleType);
  }

  public List<Display> getAllDisplaySelecteds() {
    return this.displaySelected;
  }

  public List<Display> getDisplaySelected() {
    List<Display> list = new ArrayList();

    for (Display d : this.displaySelected) {
      switch (this.displayTypeSelected) {
        case ITEM:
          if (d instanceof ItemDisplay) list.add(d);
          break;
        case BLOCK:
          if (d instanceof BlockDisplay) list.add(d);
          break;
        case TEXT:
          if (d instanceof TextDisplay) list.add(d);
          break;
        case ALL:
          list.add(d);
          break;
      }
    }
    return list;
  }

  public List<Display> getAllDisplayLockeds() {
    return this.displayLocked;
  }

  public List<Display> getDisplayLocked() {
    List<Display> list = new ArrayList();

    for (Display d : this.displayLocked) {
      switch (this.displayTypeSelected) {
        case ITEM:
          if (d instanceof ItemDisplay) list.add(d);
          break;
        case BLOCK:
          if (d instanceof BlockDisplay) list.add(d);
          break;
        case TEXT:
          if (d instanceof TextDisplay) list.add(d);
          break;
        case ALL:
          list.add(d);
          break;
      }
    }
    return list;
  }

  public enum DisplayType {
    ITEM,
    BLOCK,
    TEXT,
    ALL;
  }

  public enum DisplayScaleType {
    X,
    Y,
    Z;
  }
}

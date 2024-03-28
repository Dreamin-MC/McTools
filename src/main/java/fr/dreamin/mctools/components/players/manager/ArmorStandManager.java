package fr.dreamin.mctools.components.players.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.gui.build.armorStand.ArmorStandListLockedGui;
import fr.dreamin.mctools.components.gui.build.armorStand.ArmorStandListRadiusGui;
import fr.dreamin.mctools.components.gui.build.armorStand.ArmorStandListSelectedGui;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandManager {

  @Getter private List<ArmorStand> armorStandSelected = new ArrayList<>();
  @Getter private List<ArmorStand> armorStandLocked = new ArrayList<>();
  @Getter private List<ArmorStand> armorStandRadius = new ArrayList<>();
  @Getter private List<ArmorStand> armorStandId = new ArrayList<>();
  @Getter @Setter private double distanceMoveArmorStand = 1;
  @Getter @Setter private float armorStandRotation = (float)45;
  @Getter @Setter private double armRotate = 90;
  @Getter @Setter private boolean isInvisibleGui = false;
  @Getter private boolean setInvisibleArmorStand = false;
  @Getter @Setter private boolean leftArmPos = false;
  @Getter private MTPlayer mtPlayer;

  public ArmorStandManager(MTPlayer mtPlayer) {
    this.mtPlayer = mtPlayer;
  }


  public List<ItemStack> getArmorStandSelectedItemStack() {
    List<ItemStack> itemStacks = new ArrayList<>();
    for (ArmorStand armorStand : armorStandSelected) {

      ItemBuilder itemBuilder = new ItemBuilder(armorStand.getHelmet());

      if (armorStand.getPassenger() != null) itemBuilder.setType(Material.SHULKER_BOX);
      else if (itemBuilder.toItemStack().getType().equals(Material.AIR)) itemBuilder.setType(Material.ARMOR_STAND);

      itemStacks.add(itemBuilder.setName(" ").setLore(
        "§fMap : " + armorStand.getWorld().getName(),
        "§fx : " + armorStand.getLocation().getX(),
        "§fy : " + armorStand.getLocation().getY(),
        "§fz : " + armorStand.getLocation().getZ(),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_RIGHTCLICKREMOVE, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTCLICKTP, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_CLICKWHEELMODEL, ""))
        .toItemStack()
      );
    }
    return itemStacks;
  }

  public void addArmorStandSelected(ArmorStand armorStand) {
    this.armorStandSelected.add(armorStand);
    armorStand.setGravity(false);

    if (setInvisibleArmorStand) armorStand.setInvisible(true);

    try {
      McTools.getService(GlowingEntities.class).setGlowing(armorStand, mtPlayer.getPlayer(), ChatColor.WHITE);

      if (armorStand.getPassenger() != null)
        McTools.getService(GlowingEntities.class).setGlowing(armorStand.getPassenger(), mtPlayer.getPlayer(), ChatColor.WHITE);

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }

  }

  public void addAllArmorStandSelected(List<ArmorStand> armorStand) {

    for (ArmorStand armorStand1 : armorStand) {
      armorStand1.setGravity(false);
      try {
        McTools.getService(GlowingEntities.class).setGlowing(armorStand1, mtPlayer.getPlayer(), ChatColor.WHITE);

        if (armorStand1.getPassenger() != null)
          McTools.getService(GlowingEntities.class).setGlowing(armorStand1.getPassenger(), mtPlayer.getPlayer(), ChatColor.WHITE);
        this.addArmorStandSelected(armorStand1);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void removeArmorStandSelected(ArmorStand armorStand, boolean removeGlowing) {
    this.armorStandSelected.remove(armorStand);
    if (removeGlowing)
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

        if (armorStand.getPassenger() != null)
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
  }

  public void removeAllArmorStandSelected(boolean removeGlowing) {
    if (removeGlowing) {
      for (ArmorStand armorStand : armorStandSelected) {
        try {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

          if (armorStand.getPassenger() != null)
            McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());

        } catch (ReflectiveOperationException e) {
          throw new RuntimeException(e);
        }
      }
    }
    this.armorStandSelected.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListSelectedGui.class.getSimpleName(), 1);
  }


  public void dispawnArmorStandSelected(ArmorStand armorStand) {
    try {
      McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

      if (armorStand.getPassenger() != null) {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());
        armorStand.getPassenger().remove();
      }

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
    this.armorStandSelected.remove(armorStand);
    armorStand.remove();
  }

  public void dispawnAllArmorStandSelected() {
    for (ArmorStand armorStand : armorStandSelected) {
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());
          armorStand.getPassenger().remove();
        }

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
      armorStand.remove();
    }
    this.armorStandSelected.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListSelectedGui.class.getSimpleName(), 1);
  }

  public List<ItemStack> getArmorStandRadiusItemStack() {
    List<ItemStack> itemStacks = new ArrayList<>();
    for (ArmorStand armorStand : armorStandRadius) {

      ItemBuilder itemBuilder = new ItemBuilder(armorStand.getHelmet());

      if (armorStand.getPassenger() != null) itemBuilder.setType(Material.SHULKER_BOX);
      else if (itemBuilder.toItemStack().getType().equals(Material.AIR)) itemBuilder.setType(Material.ARMOR_STAND);

      itemStacks.add(itemBuilder.setName(" ").setLore(
        "§fMap : " + armorStand.getWorld().getName(),
        "§fx : " + armorStand.getLocation().getX(),
        "§fy : " + armorStand.getLocation().getY(),
        "§fz : " + armorStand.getLocation().getZ(),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_LEFTCLICKADD, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_RIGHTCLICKREMOVE, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTLEFTCLICKTP, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTRIGHTCLICKSHOW, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_CLICKWHEELMODEL, ""))
        .toItemStack()
      );
    }
    return itemStacks;
  }

  public void addArmorStandRadius(ArmorStand armorStand) {
    this.armorStandRadius.add(armorStand);
    armorStand.setGravity(false);
  }

  public void addAllArmorStandRadius(List<ArmorStand> armorStand) {

    for (ArmorStand armorStand1 : armorStand) {
      armorStand1.setGravity(false);
    }
    armorStandRadius.addAll(armorStand);
  }

  public void removeArmorStandRadius(ArmorStand armorStand, boolean removeGlowing) {
    if (removeGlowing)
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    this.armorStandRadius.remove(armorStand);
  }

  public void removeAllArmorStandRadius(boolean removeGlowing) {
    if (removeGlowing)
      for (ArmorStand armorStand : armorStandRadius) {
        try {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

          if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());

        } catch (ReflectiveOperationException e) {
          throw new RuntimeException(e);
        }
      }
    this.armorStandRadius.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListRadiusGui.class.getSimpleName(), 1);
  }

  public void dispawnArmorStandRadius(ArmorStand armorStand) {
    this.armorStandRadius.remove(armorStand);

    if (armorStand.getPassenger() != null)
      armorStand.getPassenger().remove();

    armorStand.remove();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListRadiusGui.class.getSimpleName(), 1);
  }

  public void dispawnAllArmorStandRadius() {
    for (ArmorStand armorStand : armorStandRadius) {
      armorStand.remove();
      if (armorStand.getPassenger() != null)
        armorStand.getPassenger().remove();
    }
    this.armorStandRadius.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListRadiusGui.class.getSimpleName(), 1);
  }

  public List<ItemStack> getArmorStandLockedItemStack() {
    List<ItemStack> itemStacks = new ArrayList<>();
    for (ArmorStand armorStand : armorStandLocked) {

      ItemBuilder itemBuilder = new ItemBuilder(armorStand.getHelmet());

      if (armorStand.getPassenger() != null) itemBuilder.setType(Material.SHULKER_BOX);
      else if (itemBuilder.toItemStack().getType().equals(Material.AIR)) itemBuilder.setType(Material.ARMOR_STAND);

      itemStacks.add(itemBuilder.setName(" ").setLore(
        "§fMap : " + armorStand.getWorld().getName(),
        "§fx : " + armorStand.getLocation().getX(),
        "§fy : " + armorStand.getLocation().getY(),
        "§fz : " + armorStand.getLocation().getZ(),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_LEFTCLICKADD, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_RIGHTCLICKREMOVE, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTCLICKTP, ""),
        mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_CLICKWHEELMODEL, ""))
        .toItemStack()
      );
    }
    return itemStacks;
  }

  public void addArmorStandLocked(ArmorStand armorStand) {
    this.armorStandLocked.add(armorStand);
    armorStand.setGravity(false);
    try {
      McTools.getService(GlowingEntities.class).setGlowing(armorStand, mtPlayer.getPlayer(), ChatColor.RED);

      if (armorStand.getPassenger() != null)
        McTools.getService(GlowingEntities.class).setGlowing(armorStand.getPassenger(), mtPlayer.getPlayer(), ChatColor.RED);

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  public void addAllArmorStandLocked(List<ArmorStand> armorStand) {

    for (ArmorStand armorStand1 : armorStand) {
      armorStand1.setGravity(false);
      try {
        McTools.getService(GlowingEntities.class).setGlowing(armorStand1, mtPlayer.getPlayer(), ChatColor.RED);

        if (armorStand1.getPassenger() != null) McTools.getService(GlowingEntities.class).setGlowing(armorStand1.getPassenger(), mtPlayer.getPlayer(), ChatColor.RED);

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
    armorStandLocked.addAll(armorStand);
  }

  public void removeArmorStandLocked(ArmorStand armorStand, boolean removeGlowing) {

    if (removeGlowing)
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    this.armorStandLocked.remove(armorStand);
  }

  public void removeAllArmorStandLocked(boolean removeGlowing) {
    if (removeGlowing)
      for (ArmorStand armorStand : armorStandLocked) {
        try {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

          if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());

        } catch (ReflectiveOperationException e) {
          throw new RuntimeException(e);
        }
      }
    this.armorStandLocked.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListLockedGui.class.getSimpleName(), 1);
  }

  public void dispawnArmorStandLocked(ArmorStand armorStand) {
    try {
      McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

      if (armorStand.getPassenger() != null) {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());
        armorStand.getPassenger().remove();
      }

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
    this.armorStandLocked.remove(armorStand);
    armorStand.remove();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListLockedGui.class.getSimpleName(), 1);
  }

  public void dispawnAllArmorStandLocked() {
    for (ArmorStand armorStand : armorStandLocked) {
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), mtPlayer.getPlayer());
          armorStand.getPassenger().remove();
        }

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
      armorStand.remove();
    }
    this.armorStandLocked.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(mtPlayer.getPlayer(), ArmorStandListLockedGui.class.getSimpleName(), 1);
  }

  public void addArmorStandId(ArmorStand armorStand) {
    this.armorStandId.add(armorStand);
  }

  public void addAllArmorStandId(List<ArmorStand> armorStand) {
    this.armorStandId.addAll(armorStand);
  }

  public void removeArmorStandId(ArmorStand armorStand) {
    this.armorStandId.remove(armorStand);
  }

  public void removeAllArmorStandId() {
    this.armorStandId.clear();
  }

  public void dispawnArmorStandId(ArmorStand armorStand) {
    this.armorStandId.remove(armorStand);
    armorStand.remove();
  }

  public void dispawnAllArmorStandId() {
    for (ArmorStand armorStand : armorStandId) {
      armorStand.remove();
    }
    this.armorStandId.clear();
  }

  public List<ItemStack> getArmorStandIdItemStack() {
    List<ItemStack> itemStacks = new ArrayList<>();
    for (ArmorStand armorStand : armorStandId) {

      ItemBuilder itemBuilder = new ItemBuilder(armorStand.getHelmet());

      if (armorStand.getPassenger() != null) itemBuilder.setType(Material.SHULKER_BOX);
      else if (itemBuilder.toItemStack().getType().equals(Material.AIR)) itemBuilder.setType(Material.ARMOR_STAND);

      itemStacks.add(itemBuilder.setName(" ").setLore(
          "§fMap : " + armorStand.getWorld().getName(),
          "§fx : " + armorStand.getLocation().getX(),
          "§fy : " + armorStand.getLocation().getY(),
          "§fz : " + armorStand.getLocation().getZ(),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_LEFTCLICKADD, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_RIGHTCLICKREMOVE, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTLEFTCLICKTP, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTRIGHTCLICKSHOW, ""),
          mtPlayer.getMsg(LangMsg.PLAYER_ARMORSTAND_LORE_GENERAL_CLICKWHEELMODEL, ""))
        .toItemStack()
      );
    }
    return itemStacks;
  }

  public ArmorStand getIfArmorStandSelected(ArmorStand armorStand) {
    return this.armorStandSelected.stream().filter(armorStand1 -> armorStand1.equals(armorStand)).findFirst().orElse(null);
  }
  public ArmorStand getIfArmorStandLocked(ArmorStand armorStand) {
    return this.armorStandLocked.stream().filter(armorStand1 -> armorStand1.equals(armorStand)).findFirst().orElse(null);
  }

  public void setInvisibleArmorStand(boolean setInvisibleArmorStand) {
    this.setInvisibleArmorStand = setInvisibleArmorStand;
    mtPlayer.getPlayer().sendMessage(McTools.getCodex().getBroadcastprefix() + CustomChatColor.GRAY.getColorWithText("Default ArmorStand : ") + (setInvisibleArmorStand ? CustomChatColor.RED.getColorWithText("invisible") : CustomChatColor.GREEN.getColorWithText("visible")));
  }

}

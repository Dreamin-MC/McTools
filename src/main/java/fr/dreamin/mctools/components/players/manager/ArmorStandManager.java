package fr.dreamin.mctools.components.players.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListLocked;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListRadius;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListSelected;
import fr.dreamin.mctools.components.players.DTPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorStandManager {

  private List<ArmorStand> armorStandList = new ArrayList<>();
  private List<ArmorStand> armorStandLocked = new ArrayList<>();
  private List<ArmorStand> armorStandRadius = new ArrayList<>();
  private double distanceMoveArmorStand = 1;
  private float armorStandRotation = (float)45;
  private double armRotate = 90;
  private boolean isInvisibleGui = false;
  private boolean setInvisibleArmorStand = false;
  private boolean leftArmPos = false;
  private DTPlayer dtPlayer;
  private HashMap<String, Integer> guiPage = new HashMap<>();

  public ArmorStandManager(DTPlayer dtPlayer) {
    this.dtPlayer = dtPlayer;
    this.guiPage.put("armorStandSelected", 1);
    this.guiPage.put("armorStandLocked", 1);
    this.guiPage.put("armorStandRadius", 1);
  }


  public double getDistanceMoveArmorStand() {
    return distanceMoveArmorStand;
  }

  public void setDistanceMoveArmorStand(double distanceMoveArmorStand) {
    this.distanceMoveArmorStand = distanceMoveArmorStand;
  }

  public float getArmorStandRotation() {
    return armorStandRotation;
  }

  public void setArmorStandRotation(float armorStandRotation) {
    this.armorStandRotation = armorStandRotation;
  }

  public List<ArmorStand> getArmorStandSelected() {
    return armorStandList;
  }

  public List<ItemStack> getArmorStandSelectedItemStack() {
    List<ItemStack> itemStacks = new ArrayList<>();
    for (ArmorStand armorStand : armorStandList) {

      ItemBuilder itemBuilder = new ItemBuilder(armorStand.getHelmet());

      if (armorStand.getPassenger() != null) {
        itemBuilder.setType(Material.SHULKER_BOX);
      }
      else if (itemBuilder.toItemStack().getType().equals(Material.AIR))

        itemBuilder.setType(Material.ARMOR_STAND);

      itemBuilder.setName("");
      itemBuilder.setLore(
        "Map : " + armorStand.getWorld().getName(),
        "§fx :" + armorStand.getLocation().getX(),
        "§fy : " + armorStand.getLocation().getY(),
        "§fz : " + armorStand.getLocation().getZ(),
        "§fClique droit pour supprimer de la liste",
        "§fshift clique pour se téléporter",
        "§fmiddle pour récupérer le model");

      itemStacks.add(itemBuilder.toItemStack());
    }
    return itemStacks;
  }

  public boolean isLeftArmPos() {
    return leftArmPos;
  }

  public void setLeftArmPos(boolean leftArmPos) {
    this.leftArmPos = leftArmPos;
  }

  public void setArmRotate(double armRotate) {
    this.armRotate = armRotate;
  }

  public double getArmRotate() {
    return armRotate;
  }

  public void addArmorStandSelected(ArmorStand armorStand) {
    this.armorStandList.add(armorStand);
    armorStand.setGravity(false);

    if (setInvisibleArmorStand) armorStand.setInvisible(true);

    try {
      McTools.getService(GlowingEntities.class).setGlowing(armorStand, dtPlayer.getPlayer(), ChatColor.WHITE);

      if (armorStand.getPassenger() != null)
        McTools.getService(GlowingEntities.class).setGlowing(armorStand.getPassenger(), dtPlayer.getPlayer(), ChatColor.WHITE);

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }

  }

  public void addAllArmorStandSelected(List<ArmorStand> armorStand) {

    for (ArmorStand armorStand1 : armorStand) {
      armorStand1.setGravity(false);
      try {
        McTools.getService(GlowingEntities.class).setGlowing(armorStand1, dtPlayer.getPlayer(), ChatColor.WHITE);

        if (armorStand1.getPassenger() != null)
          McTools.getService(GlowingEntities.class).setGlowing(armorStand1.getPassenger(), dtPlayer.getPlayer(), ChatColor.WHITE);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
    armorStandList.addAll(armorStand);
  }

  public void removeArmorStandSelected(ArmorStand armorStand, boolean removeGlowing) {
    this.armorStandList.remove(armorStand);
    if (removeGlowing)
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

        if (armorStand.getPassenger() != null)
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListSelected.class.getSimpleName(), 1);
  }

  public void removeAllArmorStandSelected(boolean removeGlowing) {
    if (removeGlowing) {
      for (ArmorStand armorStand : armorStandList) {
        try {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

          if (armorStand.getPassenger() != null)
            McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());

        } catch (ReflectiveOperationException e) {
          throw new RuntimeException(e);
        }
      }
    }
    this.armorStandList.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListSelected.class.getSimpleName(), 1);
  }


  public void dispawnArmorStandSelected(ArmorStand armorStand) {
    try {
      McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

      if (armorStand.getPassenger() != null) {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());
        armorStand.getPassenger().remove();
      }

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
    this.armorStandList.remove(armorStand);
    armorStand.remove();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListSelected.class.getSimpleName(), 1);
  }

  public void dispawnAllArmorStandSelected() {
    for (ArmorStand armorStand : armorStandList) {
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());
          armorStand.getPassenger().remove();
        }

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
      armorStand.remove();
    }
    this.armorStandList.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListSelected.class.getSimpleName(), 1);
  }

  public List<ArmorStand> getArmorStandRadius() {
    return armorStandRadius;
  }

  public List<ItemStack> getArmorStandRadiusItemStack() {
    List<ItemStack> itemStacks = new ArrayList<>();
    for (ArmorStand armorStand : armorStandRadius) {

      ItemBuilder itemBuilder = new ItemBuilder(armorStand.getHelmet());

      if (armorStand.getPassenger() != null) {
        itemBuilder.setType(Material.SHULKER_BOX);
      }
      else if (itemBuilder.toItemStack().getType().equals(Material.AIR))

        itemBuilder.setType(Material.ARMOR_STAND);

      itemBuilder.setName("");
      itemBuilder.setLore(
        "Map : " + armorStand.getWorld().getName(),
        "§fx :" + armorStand.getLocation().getX(),
        "§fy : " + armorStand.getLocation().getY(),
        "§fz : " + armorStand.getLocation().getZ(),
        "§fClique gauche pour ajouter à la liste",
        "§fClique droit pour supprimer de la liste",
        "§fshift clique gauche pour se téléporter",
        "§fshift clique droit pour l'afficher",
        "§fmiddle pour récupérer le model");

      itemStacks.add(itemBuilder.toItemStack());
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
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    this.armorStandRadius.remove(armorStand);
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListRadius.class.getSimpleName(), 1);
  }

  public void removeAllArmorStandRadius(boolean removeGlowing) {
    if (removeGlowing)
      for (ArmorStand armorStand : armorStandRadius) {
        try {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

          if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());

        } catch (ReflectiveOperationException e) {
          throw new RuntimeException(e);
        }
      }
    this.armorStandRadius.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListRadius.class.getSimpleName(), 1);
  }

  public void dispawnArmorStandRadius(ArmorStand armorStand) {
    this.armorStandRadius.remove(armorStand);

    if (armorStand.getPassenger() != null)
      armorStand.getPassenger().remove();

    armorStand.remove();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListRadius.class.getSimpleName(), 1);
  }

  public void dispawnAllArmorStandRadius() {
    for (ArmorStand armorStand : armorStandRadius) {
      armorStand.remove();
      if (armorStand.getPassenger() != null)
        armorStand.getPassenger().remove();
    }
    this.armorStandRadius.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListRadius.class.getSimpleName(), 1);
  }

  public List<ArmorStand> getArmorStandLocked() {
    return armorStandLocked;
  }

  public List<ItemStack> getArmorStandLockedItemStack() {
    List<ItemStack> itemStacks = new ArrayList<>();
    for (ArmorStand armorStand : armorStandLocked) {

      ItemBuilder itemBuilder = new ItemBuilder(armorStand.getHelmet());

      if (armorStand.getPassenger() != null) itemBuilder.setType(Material.SHULKER_BOX);
      else if (itemBuilder.toItemStack().getType().equals(Material.AIR)) itemBuilder.setType(Material.ARMOR_STAND);

      itemBuilder.setName("");
      itemBuilder.setLore(
        "Map : " + armorStand.getWorld().getName(),
        "§fx : " + armorStand.getLocation().getX(),
        "§fy : " + armorStand.getLocation().getY(),
        "§fz : " + armorStand.getLocation().getZ(),
        "§fClique gauche pour ajouter à la liste",
        "§fClique droit pour supprimer de la liste",
        "§fshift clique pour se téléporter",
        "§fmiddle pour récupérer le model");

      itemStacks.add(itemBuilder.toItemStack());
    }
    return itemStacks;
  }

  public void addArmorStandLocked(ArmorStand armorStand) {
    this.armorStandLocked.add(armorStand);
    armorStand.setGravity(false);
    try {
      McTools.getService(GlowingEntities.class).setGlowing(armorStand, dtPlayer.getPlayer(), ChatColor.RED);

      if (armorStand.getPassenger() != null)
        McTools.getService(GlowingEntities.class).setGlowing(armorStand.getPassenger(), dtPlayer.getPlayer(), ChatColor.RED);

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  public void addAllArmorStandLocked(List<ArmorStand> armorStand) {

    for (ArmorStand armorStand1 : armorStand) {
      armorStand1.setGravity(false);
      try {
        McTools.getService(GlowingEntities.class).setGlowing(armorStand1, dtPlayer.getPlayer(), ChatColor.RED);

        if (armorStand1.getPassenger() != null) McTools.getService(GlowingEntities.class).setGlowing(armorStand1.getPassenger(), dtPlayer.getPlayer(), ChatColor.RED);

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }
    armorStandLocked.addAll(armorStand);
  }


  public void removeArmorStandLocked(ArmorStand armorStand, boolean removeGlowing) {

    if (removeGlowing)
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    this.armorStandLocked.remove(armorStand);
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListLocked.class.getSimpleName(), 1);
  }

  public void removeAllArmorStandLocked(boolean removeGlowing) {
    if (removeGlowing)
      for (ArmorStand armorStand : armorStandLocked) {
        try {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

          if (armorStand.getPassenger() != null) McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());

        } catch (ReflectiveOperationException e) {
          throw new RuntimeException(e);
        }
      }
    this.armorStandLocked.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListLocked.class.getSimpleName(), 1);
  }

  public void dispawnArmorStandLocked(ArmorStand armorStand) {
    try {
      McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

      if (armorStand.getPassenger() != null) {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());
        armorStand.getPassenger().remove();
      }

    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
    this.armorStandLocked.remove(armorStand);
    armorStand.remove();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListLocked.class.getSimpleName(), 1);
  }

  public void dispawnAllArmorStandLocked() {
    for (ArmorStand armorStand : armorStandLocked) {
      try {
        McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());

        if (armorStand.getPassenger() != null) {
          McTools.getService(GlowingEntities.class).unsetGlowing(armorStand.getPassenger(), dtPlayer.getPlayer());
          armorStand.getPassenger().remove();
        }

      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
      armorStand.remove();
    }
    this.armorStandLocked.clear();
    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), ArmorStandListLocked.class.getSimpleName(), 1);
  }

  public ArmorStand getIfArmorStandSelected(ArmorStand armorStand) {
    return this.armorStandList.stream().filter(armorStand1 -> armorStand1.equals(armorStand)).findFirst().orElse(null);
  }
  public ArmorStand getIfArmorStandLocked(ArmorStand armorStand) {
    return this.armorStandLocked.stream().filter(armorStand1 -> armorStand1.equals(armorStand)).findFirst().orElse(null);
  }

  public boolean isInvisibleGui() {
    return isInvisibleGui;
  }

  public void setInvisibleGui(boolean invisibleGui) {
    isInvisibleGui = invisibleGui;
  }

  public boolean isArmorStandLocked(ArmorStand armorStand) {
    return this.armorStandLocked.contains(armorStand);
  }

  public boolean isSetInvisibleArmorStand() {
    return setInvisibleArmorStand;
  }

  public void setInvisibleArmorStand(boolean setInvisibleArmorStand) {
    this.setInvisibleArmorStand = setInvisibleArmorStand;
    dtPlayer.getPlayer().sendMessage(McTools.getCodex().getPrefix() + CustomChatColor.GRAY.getColorWithText("Default ArmorStand : ") + (setInvisibleArmorStand ? CustomChatColor.RED.getColorWithText("invisible") : CustomChatColor.GREEN.getColorWithText("visible")));
  }

}

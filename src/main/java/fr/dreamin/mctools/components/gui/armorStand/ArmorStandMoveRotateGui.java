package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Shulker;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArmorStandMoveRotateGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText((mtPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.ARMOR_MOVE_ROTATE.getName())) : mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MOVEANDROTATE_TITLE, "");
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inventory) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(90)", (mtPlayer.getArmorStandManager().getArmorStandRotation() == 90f ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 90f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(45)", (mtPlayer.getArmorStandManager().getArmorStandRotation() == 45f ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 45f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 9);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(22.5)", (mtPlayer.getArmorStandManager().getArmorStandRotation() == 22.5f ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 22.5f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 18);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(0.5)", (mtPlayer.getArmorStandManager().getArmorStandRotation() == 0.5f ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 0.5f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 27);

    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_TRAVELDISTANCE, "(1)", (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 1 ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 1 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 1);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_TRAVELDISTANCE, "(0.1)", (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.1 ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.1 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 10);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_TRAVELDISTANCE, "(0.05)", (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.05 ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.05 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 19);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_TRAVELDISTANCE, "(0.01)", (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.01 ? LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") : "")), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.01 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 28);

    guiItems.create(new ItemBuilder(ItemsPreset.arrowForWard.getItem()).setName(mtPlayer.getMsg(LangMsg.GENERAL_FORWARD, "")).toItemStack(), 4);
    guiItems.create(new ItemBuilder(ItemsPreset.arrowLeft.getItem()).setName(mtPlayer.getMsg(LangMsg.GENERAL_LEFT, "")).toItemStack(), 12);
    guiItems.create(new ItemBuilder(ItemsPreset.arrowRight.getItem()).setName(mtPlayer.getMsg(LangMsg.GENERAL_RIGHT, "")).toItemStack(), 14);
    guiItems.create(new ItemBuilder(ItemsPreset.arrowBackWard.getItem()).setName(mtPlayer.getMsg(LangMsg.GENERAL_BACKWARD, "")).toItemStack(), 22);

    guiItems.create(new ItemBuilder(ItemsPreset.arrowUp.getItem()).setName(mtPlayer.getMsg(LangMsg.GENERAL_UP, "")).toItemStack(), 7);
    guiItems.create(new ItemBuilder(ItemsPreset.arrowDown.getItem()).setName(mtPlayer.getMsg(LangMsg.GENERAL_DOWN, "")).toItemStack(), 25);

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_GUIIN, (mtPlayer.getArmorStandManager().isInvisibleGui() ? LangMsg.GENERAL_VISIBLE : LangMsg.GENERAL_INVISIBLE)), Material.NAME_TAG, (mtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 40);

    guiItems.create(new ItemBuilder(ItemsPreset.arrowPrevious.getItem()).setName(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MOVEANDROTATE_ROTATELEFT, "")).toItemStack(), 41);
    guiItems.create(new ItemBuilder(ItemsPreset.arrowNext.getItem()).setName(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MOVEANDROTATE_ROTATERIGHT, "")).toItemStack(), 42);

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_BACK, ""), Material.NAME_TAG, 3, 36);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_LEAVE, ""), Material.NAME_TAG, 4, 44);

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 0:
        mtPlayer.getArmorStandManager().setArmorStandRotation(90f);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 9:
        mtPlayer.getArmorStandManager().setArmorStandRotation(45f);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 18:
        mtPlayer.getArmorStandManager().setArmorStandRotation(22.5f);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 27:
        mtPlayer.getArmorStandManager().setArmorStandRotation(0.5f);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;

      case 1:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(1);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 10:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(0.1);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 19:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(0.05);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 28:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(0.01);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;

      case 4:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = getLocationAv(armorStand, roundYawToCardinalDirection(mtPlayer.getPlayer().getLocation().getYaw()), mtPlayer);

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;
      case 12:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = getLocationG(armorStand, roundYawToCardinalDirection(mtPlayer.getPlayer().getLocation().getYaw()), mtPlayer);

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;
      case 14:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = getLocationD(armorStand, roundYawToCardinalDirection(mtPlayer.getPlayer().getLocation().getYaw()), mtPlayer);

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;
      case 22:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = getLocationAr(armorStand, roundYawToCardinalDirection(mtPlayer.getPlayer().getLocation().getYaw()), mtPlayer);

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;

      case 7:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = new Location(
            armorStand.getWorld(), armorStand.getLocation().getX(),
            armorStand.getLocation().getY() + mtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), armorStand.getLocation().getZ()
          );

          Float armorStandYaw = armorStand.getLocation().getYaw();

          location.setYaw(armorStandYaw);

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);

            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;
      case 25:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = new Location(
            armorStand.getWorld(), armorStand.getLocation().getX(),
            armorStand.getLocation().getY() - mtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), armorStand.getLocation().getZ()
          );

          Float armorStandYaw = armorStand.getLocation().getYaw();

          location.setYaw(armorStandYaw);

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;

      case 40:
        mtPlayer.getArmorStandManager().setInvisibleGui(!mtPlayer.getArmorStandManager().isInvisibleGui());
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;

      case 41:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = new Location(
            armorStand.getWorld(), armorStand.getLocation().getX(),
            armorStand.getLocation().getY(), armorStand.getLocation().getZ()
          );

          Float armorStandYaw = armorStand.getLocation().getYaw();

          location.setYaw(armorStandYaw + mtPlayer.getArmorStandManager().getArmorStandRotation());

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;
      case 42:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          Location location = new Location(
            armorStand.getWorld(), armorStand.getLocation().getX(),
            armorStand.getLocation().getY(), armorStand.getLocation().getZ()
          );

          Float armorStandYaw = armorStand.getLocation().getYaw();

          location.setYaw(armorStandYaw - mtPlayer.getArmorStandManager().getArmorStandRotation());

          if (armorStand.getPassenger() != null && armorStand.getPassenger() instanceof Shulker) {
            Shulker shulker = (Shulker) armorStand.getPassenger();
            armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            armorStand.addPassenger(shulker);
          }
          else armorStand.teleport(location);
        });
        return;

      case 36:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMenuGui.class);
        return;
      case 44:
        mtPlayer.getPlayer().closeInventory();
        break;

    }
  }

  @NotNull
  private static Location getLocationAv(ArmorStand armorStand, float roundedYaw, MTPlayer MTPlayer) {
    double rad = 0;

    // Est
    if (roundedYaw == 270) {
      rad = Math.toRadians(roundedYaw);
    }
    //Ouest
    else if (roundedYaw == 90) {
      rad = Math.toRadians(roundedYaw);
    }
    //Sud
    else if (roundedYaw == 0) {
      rad = Math.toRadians(roundedYaw + 180);
    }
    //Nord
    else if (roundedYaw == 180) {
      rad = Math.toRadians(roundedYaw  + 180);
    }


    // Calculer la nouvelle position
    double x = armorStand.getLocation().getX() + Math.sin(rad) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() + Math.cos(rad) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();

    Location newLocation = new Location(armorStand.getWorld(), x, armorStand.getLocation().getY(), z);

    newLocation.setYaw(armorStand.getLocation().getYaw());
    return newLocation;
  }

  @NotNull
  private static Location getLocationAr(ArmorStand armorStand, float roundedYaw, MTPlayer MTPlayer) {
    double rad = 0;

    // Est
    if (roundedYaw == 270) {
      rad = Math.toRadians(roundedYaw);
    }
    //Ouest
    else if (roundedYaw == 90) {
      rad = Math.toRadians(roundedYaw);
    }
    //Sud
    else if (roundedYaw == 0) {
      rad = Math.toRadians(roundedYaw + 180);
    }
    //Nord
    else if (roundedYaw == 180) {
      rad = Math.toRadians(roundedYaw  + 180);
    }


    // Calculer la nouvelle position
    double x = armorStand.getLocation().getX() - Math.sin(rad) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() - Math.cos(rad) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    Location newLocation = new Location(armorStand.getWorld(), x, armorStand.getLocation().getY(), z);
    newLocation.setYaw(armorStand.getLocation().getYaw());
    return newLocation;
  }

  private static Location getLocationG(ArmorStand armorStand, float roundedYaw, MTPlayer MTPlayer) {
    double adjustedYaw = 0;

    //Est
    if (roundedYaw == 270) {
      adjustedYaw = Math.toRadians(roundedYaw - 90);
    }
    //Ouest
    else if (roundedYaw == 90) {
      adjustedYaw = Math.toRadians(roundedYaw - 90);
    }
    //Sud
    else if (roundedYaw == 0) {
      adjustedYaw = Math.toRadians(roundedYaw + 90);
    }
    //Nord
    else if (roundedYaw == 180) {
      adjustedYaw = Math.toRadians(roundedYaw + 90);
    }

    double x = armorStand.getLocation().getX() + Math.sin(adjustedYaw) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() + Math.cos(adjustedYaw) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    Location newLocation = new Location(armorStand.getWorld(), x, armorStand.getLocation().getY(), z);
    newLocation.setYaw(armorStand.getLocation().getYaw());
    return newLocation;
  }

  @NotNull
  private static Location getLocationD(ArmorStand armorStand, float roundedYaw, MTPlayer MTPlayer) {
    // Ajuster l'angle pour se déplacer perpendiculairement à droite
    double adjustedYaw;
    if (roundedYaw == 180 || roundedYaw == 0) {
      // Inverser la direction pour le Sud et le Nord
      adjustedYaw = Math.toRadians(roundedYaw - 90);
    } else {
      adjustedYaw = Math.toRadians(roundedYaw + 90);
    }

    // Calculer la nouvelle position
    double x = armorStand.getLocation().getX() + Math.sin(adjustedYaw) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() + Math.cos(adjustedYaw) * MTPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    Location newLocation = new Location(armorStand.getWorld(), x, armorStand.getLocation().getY(), z);
    newLocation.setYaw(armorStand.getLocation().getYaw());
    return newLocation;
  }

  public float roundYawToCardinalDirection(float yaw) {
    yaw = (yaw % 360 + 360) % 360;
    if (yaw < 45 || yaw >= 315) {
      return 0; // Sud
    } else if (yaw < 135) {
      return 90; // Ouest
    } else if (yaw < 225) {
      return 180; // Nord
    } else {
      return 270; // Est
    }
  }

}

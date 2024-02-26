package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArmorStandMoveRotateGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText((MTPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.ARMOR_MOVE_ROTATE.getName())) : "ArmorStand Move Rotate";
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create("Rotation (90) " + (mtPlayer.getArmorStandManager().getArmorStandRotation() == 90f ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 90f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 0, "§7Rotation de 90°.");
    guiItems.create("Rotation (45) " + (mtPlayer.getArmorStandManager().getArmorStandRotation() == 45f ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 45f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 9, "§7Rotation de 45°.");
    guiItems.create("Rotation (22.5) " + (mtPlayer.getArmorStandManager().getArmorStandRotation() == 22.5f ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 22.5f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 18, "§7Rotation de 22.5°.");
    guiItems.create("Rotation (0.5) " + (mtPlayer.getArmorStandManager().getArmorStandRotation() == 0.5f ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmorStandRotation() == 0.5f ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 27, "§7Rotation de 0.5°.");

    guiItems.create("Distance déplacement (1) " + (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 1 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 1 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 1, 1, "§7Distance de déplacement de 1.");
    guiItems.create("Distance déplacement (0.1) " + (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.1 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.1 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 1, 10, "§7Distance de déplacement de 0.1.");
    guiItems.create("Distance déplacement (0.05) " + (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.05 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.05 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 1, 19, "§7Distance de déplacement de 0.05.");
    guiItems.create("Distance déplacement (0.01) " + (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.01 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getDistanceMoveArmorStand() == 0.01 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 1, 28, "§7Distance de déplacement de 0.01.");

    guiItems.create(new ItemBuilder(ItemsPreset.arrowForWard.getItem()).setName("Avant").toItemStack(), 4, "§7Déplacer les armor stands vers l'avant.");
    guiItems.create(new ItemBuilder(ItemsPreset.arrowLeft.getItem()).setName("Gauche").toItemStack(), 12, "§7Déplacer les armor stands vers la gauche.");
    guiItems.create(new ItemBuilder(ItemsPreset.arrowRight.getItem()).setName("Droite").toItemStack(), 14, "§7Déplacer les armor stands vers la droite.");
    guiItems.create(new ItemBuilder(ItemsPreset.arrowBackWard.getItem()).setName("Arrière").toItemStack(), 22, "§7Déplacer les armor stands vers l'arrière.");

    guiItems.create(new ItemBuilder(ItemsPreset.arrowUp.getItem()).setName("En haut").toItemStack(), 7, "§7Déplacer les armor stands vers le haut.");
    guiItems.create(new ItemBuilder(ItemsPreset.arrowDown.getItem()).setName("En bas").toItemStack(), 25, "§7Déplacer les armor stands vers le bas.");

    guiItems.create((mtPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (mtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 40, "§7Rendre le menu visible ou invisible.");

    guiItems.create(new ItemBuilder(ItemsPreset.arrowPrevious.getItem()).setName("Rotation gauche").toItemStack(), 41, "§7Rotation de 0.5° vers la gauche.");
    guiItems.create(new ItemBuilder(ItemsPreset.arrowNext.getItem()).setName("Rotation droite").toItemStack(), 42, "§7Rotation de 0.5° vers la droite.");

    guiItems.create("Retourner au menu", Material.NAME_TAG, 3, 36, "§7Retourner au menu des armor stands.");
    guiItems.create("Quitter", Material.NAME_TAG, 4, 44, "§7Fermer le menu.");

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 0:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(0.1);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 9:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(0.01);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 18:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(1);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 27:
        mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(0.05);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;

      case 1:
        mtPlayer.getArmorStandManager().setArmorStandRotation((float)90);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 10:
        mtPlayer.getArmorStandManager().setArmorStandRotation((float)45);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 19:
        mtPlayer.getArmorStandManager().setArmorStandRotation((float)22.5);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        return;
      case 28:
        mtPlayer.getArmorStandManager().setArmorStandRotation((float)0.5);
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

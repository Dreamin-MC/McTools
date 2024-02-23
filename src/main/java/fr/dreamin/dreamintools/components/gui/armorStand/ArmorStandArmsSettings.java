package fr.dreamin.dreamintools.components.gui.armorStand;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.colors.CustomChatColor;
import fr.dreamin.dreamintools.api.gui.GuiBuilder;
import fr.dreamin.dreamintools.api.gui.GuiItems;
import fr.dreamin.dreamintools.api.gui.PaginationManager;
import fr.dreamin.dreamintools.api.gui.PictureGui;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArmorStandArmsSettings implements GuiBuilder {

  @Override
  public String name(Player player) {
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return CustomChatColor.WHITE.getColorWithText((dtPlayer.getArmorStandManager().isInvisibleGui() ? "七七七七七七七七七七七七七七七七七七七七七七七七七七" : PictureGui.ARMOR_MOVE_ROTATE.getName() + "七七七七七七七七七七七七七七七七七七七七七七七七七七"));
  }


  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return null;
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("Rotation (90) " + (dtPlayer.getArmorStandManager().getArmRotate() == 90 ? "(Active)" : ""), (dtPlayer.getArmorStandManager().getArmRotate() == 90 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 0, "§7Rotation de 90°.");
    guiItems.create("Rotation (45) " + (dtPlayer.getArmorStandManager().getArmRotate() == 45 ? "(Active)" : ""), (dtPlayer.getArmorStandManager().getArmRotate() == 45 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 9, "§7Rotation de 45°.");
    guiItems.create("Rotation (22.5) " + (dtPlayer.getArmorStandManager().getArmRotate() == 22.5 ? "(Active)" : ""), (dtPlayer.getArmorStandManager().getArmRotate() == 22.5 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 18, "§7Rotation de 22.5°.");
    guiItems.create("Rotation (0.5) " + (dtPlayer.getArmorStandManager().getArmRotate() == 0.5 ? "(Active)" : ""), (dtPlayer.getArmorStandManager().getArmRotate() == 0.5 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 27, "§7Rotation de 0.5°.");

    guiItems.create("Bras : " + (dtPlayer.getArmorStandManager().isLeftArmPos() ? "Gauche" : "Droit"), Material.IRON_SWORD,4, "§7Déplacer les armor stands vers la gauche.");
    guiItems.create("Move x: " + dtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), Material.GLOWSTONE_DUST,12);
    guiItems.create("Move y: " + dtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), Material.GLOWSTONE_DUST,13);
    guiItems.create("Move z: " + dtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), Material.GLOWSTONE_DUST,14);

    guiItems.create((dtPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (dtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 40, "§7Rendre le menu visible ou invisible.");

    guiItems.create("Retourner au menu", Material.NAME_TAG, 3, 36, "§7Retourner au menu des armor stands.");
    guiItems.create("Quitter", Material.NAME_TAG, 4, 44, "§7Fermer le menu.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);


    if (current.getItemMeta().getDisplayName().contains("Bras")) {
      dtPlayer.getArmorStandManager().setLeftArmPos(!dtPlayer.getArmorStandManager().isLeftArmPos());
      McTools.getInstance().getGuiManager().open(player, ArmorStandArmsSettings.class);
      return;
    }
    else if (current.getItemMeta().getDisplayName().contains("Rotation (90)")) {
      dtPlayer.getArmorStandManager().setArmRotate(90);
      McTools.getInstance().getGuiManager().open(player, ArmorStandArmsSettings.class);
      return;
    }
    else if (current.getItemMeta().getDisplayName().contains("Rotation (45)")) {
      dtPlayer.getArmorStandManager().setArmRotate(45);
      McTools.getInstance().getGuiManager().open(player, ArmorStandArmsSettings.class);
      return;
    }
    else if (current.getItemMeta().getDisplayName().contains("Rotation (22.5)")) {
      dtPlayer.getArmorStandManager().setArmRotate(22.5);
      McTools.getInstance().getGuiManager().open(player, ArmorStandArmsSettings.class);
      return;
    }
    else if (current.getItemMeta().getDisplayName().contains("Rotation (0.5)")) {
      dtPlayer.getArmorStandManager().setArmRotate(0.5);
      McTools.getInstance().getGuiManager().open(player, ArmorStandArmsSettings.class);
      return;
    }
    else if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Passage")) {
      dtPlayer.getArmorStandManager().setInvisibleGui(!dtPlayer.getArmorStandManager().isInvisibleGui());
      McTools.getInstance().getGuiManager().open(player, ArmorStandArmsSettings.class);
      return;
    }
    else if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Retour")) {
      McTools.getInstance().getGuiManager().open(player, ArmorStandMenuGui.class);
      return;
    }
    else if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Quitter")) {
      player.closeInventory();
      return;
    }

    for (ArmorStand armorStand : dtPlayer.getArmorStandManager().getArmorStandSelected()) {

      if (armorStand != null) {

        if (current.getItemMeta().getDisplayName().contains("Move x")) {

          if (dtPlayer.getArmorStandManager().isLeftArmPos()) {
            armorStand.setLeftArmPose(armorStand.getLeftArmPose().setX(armorStand.getLeftArmPose().getX() + dtPlayer.getArmorStandManager().getArmRotate()));
          }
          else {
            armorStand.setRightArmPose(armorStand.getRightArmPose().setX(armorStand.getRightArmPose().getX() + dtPlayer.getArmorStandManager().getArmRotate()));
          }

        }
        else if (current.getItemMeta().getDisplayName().contains("Move y")) {

          if (dtPlayer.getArmorStandManager().isLeftArmPos()) {
            armorStand.setLeftArmPose(armorStand.getLeftArmPose().setY(armorStand.getLeftArmPose().getY() + dtPlayer.getArmorStandManager().getArmRotate()));
          }
          else {
            armorStand.setRightArmPose(armorStand.getRightArmPose().setY(armorStand.getRightArmPose().getY() + dtPlayer.getArmorStandManager().getArmRotate()));
          }

        }
        else if (current.getItemMeta().getDisplayName().contains("Move z")) {

          if (dtPlayer.getArmorStandManager().isLeftArmPos()) {
            armorStand.setLeftArmPose(armorStand.getLeftArmPose().setZ(armorStand.getLeftArmPose().getZ() + dtPlayer.getArmorStandManager().getArmRotate()));
          }
          else {
            armorStand.setRightArmPose(armorStand.getRightArmPose().setZ(armorStand.getRightArmPose().getZ() + dtPlayer.getArmorStandManager().getArmRotate()));
          }

        }


      }

    }

  }

  @NotNull
  private static Location getLocationAv(ArmorStand armorStand, float roundedYaw, DTPlayer dtPlayer) {
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
    double x = armorStand.getLocation().getX() + Math.sin(rad) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() + Math.cos(rad) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();

    Location newLocation = new Location(armorStand.getWorld(), x, armorStand.getLocation().getY(), z);

    newLocation.setYaw(armorStand.getLocation().getYaw());
    return newLocation;
  }

  @NotNull
  private static Location getLocationAr(ArmorStand armorStand, float roundedYaw, DTPlayer dtPlayer) {
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
    double x = armorStand.getLocation().getX() - Math.sin(rad) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() - Math.cos(rad) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    Location newLocation = new Location(armorStand.getWorld(), x, armorStand.getLocation().getY(), z);
    newLocation.setYaw(armorStand.getLocation().getYaw());
    return newLocation;
  }

  private static Location getLocationG(ArmorStand armorStand, float roundedYaw, DTPlayer dtPlayer) {
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

    double x = armorStand.getLocation().getX() + Math.sin(adjustedYaw) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() + Math.cos(adjustedYaw) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    Location newLocation = new Location(armorStand.getWorld(), x, armorStand.getLocation().getY(), z);
    newLocation.setYaw(armorStand.getLocation().getYaw());
    return newLocation;
  }

  @NotNull
  private static Location getLocationD(ArmorStand armorStand, float roundedYaw, DTPlayer dtPlayer) {
    // Ajuster l'angle pour se déplacer perpendiculairement à droite
    double adjustedYaw;
    if (roundedYaw == 180 || roundedYaw == 0) {
      // Inverser la direction pour le Sud et le Nord
      adjustedYaw = Math.toRadians(roundedYaw - 90);
    } else {
      adjustedYaw = Math.toRadians(roundedYaw + 90);
    }

    // Calculer la nouvelle position
    double x = armorStand.getLocation().getX() + Math.sin(adjustedYaw) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();
    double z = armorStand.getLocation().getZ() + Math.cos(adjustedYaw) * dtPlayer.getArmorStandManager().getDistanceMoveArmorStand();
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

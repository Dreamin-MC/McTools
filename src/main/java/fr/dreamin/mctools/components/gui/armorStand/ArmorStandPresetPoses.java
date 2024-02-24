package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ArmorStandPresetPoses implements GuiBuilder {

  @Override
  public String name(Player player) {
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return CustomChatColor.WHITE.getColorWithText((dtPlayer.getArmorStandManager().isInvisibleGui() ? "七" : PictureGui.ARMOR_POSE.getName()));
  }

  @Override
  public int getLines() {
    return 4;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {
    return null;
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("Defaut 1", Material.ARMOR_STAND, 0, "§7Définir la pose par défaut 1.");
    guiItems.create("Defaut 2", Material.ARMOR_STAND, 1, "§7Définir la pose par défaut 2.");
    guiItems.create("Defaut 3", Material.ARMOR_STAND, 2, "§7Définir la pose par défaut 2.");
    guiItems.create("Marche 1", Material.ARMOR_STAND, 3, "§7Définir la pose de marche 1.");
    guiItems.create("Marche 2", Material.ARMOR_STAND, 4, "§7Définir la pose de marche 2.");
    guiItems.create("Assis", Material.ARMOR_STAND, 5, "§7Définir la pose assis.");

    guiItems.create((dtPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (dtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 31, "§7Passer en mode " + (dtPlayer.getArmorStandManager().isInvisibleGui() ? "visible" : "invisible") + ".");

    guiItems.create("Retour en arrière", Material.NAME_TAG, 3, 27, "§7Retourner à la liste des armor stands.");
    guiItems.create("Quitter", Material.NAME_TAG, 4, 35, "§7Fermer le menu.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Passage")) {
      dtPlayer.getArmorStandManager().setInvisibleGui(!dtPlayer.getArmorStandManager().isInvisibleGui());
    }
    else if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Retour")) {
      McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
    }
    else if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Quitter")) {
      player.closeInventory();
    }
    else {
      for (ArmorStand armorStand : dtPlayer.getArmorStandManager().getArmorStandSelected()) {
        if (current.getItemMeta().getDisplayName().contains("Defaut 1"))
          setArmorStandPose(armorStand, 345, 0, 10, 350, 0, 350, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        else if (current.getItemMeta().getDisplayName().contains("Defaut 2"))
          setArmorStandPose(armorStand, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        else if (current.getItemMeta().getDisplayName().contains("Defaut 3")) {
          armorStand.setRightArmPose(new EulerAngle(Math.PI *1.5, 0, 0));
          armorStand.setLeftArmPose(new EulerAngle(Math.PI *1.5, 0, 0));
        }
        else if (current.getItemMeta().getDisplayName().contains("Marche 1"))
          setArmorStandPose(armorStand, 345, 0, 10, 350, 0, 350, 340, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0);
        else if (current.getItemMeta().getDisplayName().contains("Marche 2"))
          setArmorStandPose(armorStand, 300, 40, 350, 20, 0, 350, 10, 0, 0, 350, 0, 0, 0, 0, 0, 0, 0, 0);
        else if (current.getItemMeta().getDisplayName().contains("Assis"))
          setArmorStandPose(armorStand, 345, 0, 10, 350, 0, 350, 280, 20, 0, 280, 340, 0, 0, 0, 0, 0, 0, 0);
      }
    }

  }

  private void setArmorStandPose(ArmorStand armorStand, double rightArmRoll, double rightArmYaw, double rightArmPitch, double leftArmRoll, double leftArmYaw, double leftArmPitch, double rightLegRoll, double rightLegYaw, double rightLegPitch, double leftLegRoll, double LeftLegYaw, double llp_yaw, double headRoll, double headYaw, double headPitch, double bodyRoll, double bodyYaw, double bodyPitch) {

    // Set general settings
    armorStand.setArms(true);
    armorStand.setBasePlate(false);
    armorStand.setGravity(false);

    // Calculate and set right arm settings
    rightArmRoll = Math.toRadians(rightArmRoll);
    rightArmYaw = Math.toRadians(rightArmYaw);
    rightArmPitch = Math.toRadians(rightArmPitch);
    EulerAngle rightArmEulerAngle = new EulerAngle(rightArmRoll, rightArmYaw, rightArmPitch);
    armorStand.setRightArmPose(rightArmEulerAngle);

    // Calculate and set left arm settings
    leftArmRoll = Math.toRadians(leftArmRoll);
    leftArmYaw = Math.toRadians(leftArmYaw);
    leftArmPitch = Math.toRadians(leftArmPitch);
    EulerAngle leftArmEulerAngle = new EulerAngle(leftArmRoll, leftArmYaw, leftArmPitch);
    armorStand.setLeftArmPose(leftArmEulerAngle);

    // Calculate and set right leg settings
    rightLegRoll = Math.toRadians(rightLegRoll);
    rightLegYaw = Math.toRadians(rightLegYaw);
    rightLegPitch = Math.toRadians(rightLegPitch);
    EulerAngle rightLegEulerAngle = new EulerAngle(rightLegRoll, rightLegYaw, rightLegPitch);
    armorStand.setRightLegPose(rightLegEulerAngle);

    // Calculate and set left leg settings
    leftLegRoll = Math.toRadians(leftLegRoll);
    LeftLegYaw = Math.toRadians(LeftLegYaw);
    llp_yaw = Math.toRadians(llp_yaw);
    EulerAngle leftLegEulerAngle = new EulerAngle(leftLegRoll, LeftLegYaw, llp_yaw);
    armorStand.setLeftLegPose(leftLegEulerAngle);

    // Calculate and set body settings
    bodyRoll = Math.toRadians(bodyRoll);
    bodyYaw = Math.toRadians(bodyYaw);
    bodyPitch = Math.toRadians(bodyPitch);
    EulerAngle bodyEulerAngle = new EulerAngle(bodyRoll, bodyYaw, bodyPitch);
    armorStand.setBodyPose(bodyEulerAngle);

    // Calculate and set head settings
    headRoll = Math.toRadians(headRoll);
    headYaw = Math.toRadians(headYaw);
    headPitch = Math.toRadians(headPitch);
    EulerAngle headEulerAngle = new EulerAngle(headRoll, headYaw, headPitch);
    armorStand.setHeadPose(headEulerAngle);

  }



}

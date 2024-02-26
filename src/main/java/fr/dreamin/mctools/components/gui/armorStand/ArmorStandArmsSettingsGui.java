package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandArmsSettingsGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText((MTPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.ARMOR_MOVE_ROTATE.getName())) : "ArmorStand Arms Settings";
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
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create("Rotation (90) " + (mtPlayer.getArmorStandManager().getArmRotate() == 90 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmRotate() == 90 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 0, "§7Rotation de 90°.");
    guiItems.create("Rotation (45) " + (mtPlayer.getArmorStandManager().getArmRotate() == 45 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmRotate() == 45 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 9, "§7Rotation de 45°.");
    guiItems.create("Rotation (22.5) " + (mtPlayer.getArmorStandManager().getArmRotate() == 22.5 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmRotate() == 22.5 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 18, "§7Rotation de 22.5°.");
    guiItems.create("Rotation (0.5) " + (mtPlayer.getArmorStandManager().getArmRotate() == 0.5 ? "(Active)" : ""), (mtPlayer.getArmorStandManager().getArmRotate() == 0.5 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 27, "§7Rotation de 0.5°.");

    guiItems.create("Bras : " + (mtPlayer.getArmorStandManager().isLeftArmPos() ? "Gauche" : "Droit"), Material.IRON_SWORD,4, "§7Déplacer les armor stands vers la gauche.");
    guiItems.create("Move x: " + mtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), Material.GLOWSTONE_DUST,12);
    guiItems.create("Move y: " + mtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), Material.GLOWSTONE_DUST,13);
    guiItems.create("Move z: " + mtPlayer.getArmorStandManager().getDistanceMoveArmorStand(), Material.GLOWSTONE_DUST,14);

    guiItems.create((mtPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (mtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 40, "§7Rendre le menu visible ou invisible.");

    guiItems.create("Retourner au menu", Material.NAME_TAG, 3, 36, "§7Retourner au menu des armor stands.");
    guiItems.create("Quitter", Material.NAME_TAG, 4, 44, "§7Fermer le menu.");

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {
    MTPlayer mTPlayer = McTools.getService(PlayersService.class).getPlayer(mtPlayer.getPlayer());

    switch (slot) {
      case 0:
        mTPlayer.getArmorStandManager().setArmRotate(90);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandArmsSettingsGui.class);
        return;
      case 9:
        mTPlayer.getArmorStandManager().setArmRotate(45);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandArmsSettingsGui.class);
        return;
      case 18:
        mTPlayer.getArmorStandManager().setArmRotate(22.5);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandArmsSettingsGui.class);
        return;
      case 27:
        mTPlayer.getArmorStandManager().setArmRotate(0.5);
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandArmsSettingsGui.class);
        return;

      case 4:
        mTPlayer.getArmorStandManager().setLeftArmPos(!mTPlayer.getArmorStandManager().isLeftArmPos());
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandArmsSettingsGui.class);
        return;
      case 12:
        mTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          if (mTPlayer.getArmorStandManager().isLeftArmPos()) {
            armorStand.setLeftArmPose(armorStand.getLeftArmPose().setX(armorStand.getLeftArmPose().getX() + mTPlayer.getArmorStandManager().getArmRotate()));
          }
          else {
            armorStand.setRightArmPose(armorStand.getRightArmPose().setX(armorStand.getRightArmPose().getX() + mTPlayer.getArmorStandManager().getArmRotate()));
          }
        });
        return;
      case 13:
        mTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          if (mTPlayer.getArmorStandManager().isLeftArmPos()) {
            armorStand.setLeftArmPose(armorStand.getLeftArmPose().setY(armorStand.getLeftArmPose().getY() + mTPlayer.getArmorStandManager().getArmRotate()));
          }
          else {
            armorStand.setRightArmPose(armorStand.getRightArmPose().setY(armorStand.getRightArmPose().getY() + mTPlayer.getArmorStandManager().getArmRotate()));
          }
        });
        return;
      case 14:
        mTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          if (mTPlayer.getArmorStandManager().isLeftArmPos()) {
            armorStand.setLeftArmPose(armorStand.getLeftArmPose().setZ(armorStand.getLeftArmPose().getZ() + mTPlayer.getArmorStandManager().getArmRotate()));
          }
          else {
            armorStand.setRightArmPose(armorStand.getRightArmPose().setZ(armorStand.getRightArmPose().getZ() + mTPlayer.getArmorStandManager().getArmRotate()));
          }
        });
        return;

      case 40:
        mTPlayer.getArmorStandManager().setInvisibleGui(!mTPlayer.getArmorStandManager().isInvisibleGui());
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandArmsSettingsGui.class);
        return;

      case 36:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMenuGui.class);
        return;
      case 44:
        mtPlayer.getPlayer().closeInventory();
        break;
    }
  }
}

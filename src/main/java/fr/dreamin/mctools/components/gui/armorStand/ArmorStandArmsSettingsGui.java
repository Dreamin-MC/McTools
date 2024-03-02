package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandArmsSettingsGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText((mtPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.ARMOR_MOVE_ROTATE.getName())) : mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_ARMSSETTINGS_TITLE, "");
  }


  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(90)", (mtPlayer.getArmorStandManager().getArmRotate() == 90 ? "("+  LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") +")" : "")), (mtPlayer.getArmorStandManager().getArmRotate() == 90 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 0);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(45)", (mtPlayer.getArmorStandManager().getArmRotate() == 45 ? "("+  LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") +")" : "")), (mtPlayer.getArmorStandManager().getArmRotate() == 45 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 9);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(22.5)", (mtPlayer.getArmorStandManager().getArmRotate() == 22.5 ? "("+  LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") +")" : "")), (mtPlayer.getArmorStandManager().getArmRotate() == 22.5 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 18);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_ROTATION, "(0.5)", (mtPlayer.getArmorStandManager().getArmRotate() == 0.5 ? "("+  LangMsg.GENERAL_ACTIVATE.getMsg(mtPlayer.getLang(), "") +")" : "")), (mtPlayer.getArmorStandManager().getArmRotate() == 0.5 ? Material.BLUE_TERRACOTTA : Material.ORANGE_TERRACOTTA), 0, 27);

    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_ARMSETTINGS_ARM, (mtPlayer.getArmorStandManager().isLeftArmPos() ? LangMsg.GENERAL_LEFT : LangMsg.GENERAL_RIGHT)), Material.IRON_SWORD,4);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_ARMSETTINGS_MOVE, "X", String.valueOf(mtPlayer.getArmorStandManager().getDistanceMoveArmorStand())), Material.GLOWSTONE_DUST,12);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_ARMSETTINGS_MOVE, "Y", String.valueOf(mtPlayer.getArmorStandManager().getDistanceMoveArmorStand())), Material.GLOWSTONE_DUST,13);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_ARMSETTINGS_MOVE, "Z", String.valueOf(mtPlayer.getArmorStandManager().getDistanceMoveArmorStand())), Material.GLOWSTONE_DUST,14);

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_GUIIN, (mtPlayer.getArmorStandManager().isInvisibleGui() ? LangMsg.GENERAL_VISIBLE : LangMsg.GENERAL_INVISIBLE)), Material.NAME_TAG, (mtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 40);

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_RETURNMENU, ""), Material.NAME_TAG, 3, 36);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_LEAVE, ""), Material.NAME_TAG, 4, 44);
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

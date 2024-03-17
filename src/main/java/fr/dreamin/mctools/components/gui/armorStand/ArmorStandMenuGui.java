package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.components.gui.tag.TagCategoryListGui;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandMenuGui implements GuiBuilder {


  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.ARMORS.getName()) : mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MAIN_TITLE, "");
  }

  @Override
  public int getLines() {
    return 3;
  }

  @Override
  public GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inventory) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MAIN_BASICSETTINGS, ""), Material.ARMOR_STAND, 2);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MAIN_BODYSETTINGS, ""), Material.STONE_SLAB, 3);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MAIN_MOVEANDROTATE, ""), Material.GLOWSTONE_DUST, 5);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MAIN_SETPOSE, ""), Material.IRON_SWORD, 6);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MAIN_LISTARMORSTAND, ""), Material.PAPER, 13);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_MAIN_TAG, ""), Material.NAME_TAG, 18);

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_LEAVE, ""), Material.NAME_TAG, 4, 26);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_GUIIN, (mtPlayer.getArmorStandManager().isInvisibleGui() ? LangMsg.GENERAL_VISIBLE : LangMsg.GENERAL_INVISIBLE)), Material.NAME_TAG, (mtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 22);
  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 2:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandBasicSettingsGui.class);
        break;
      case 3:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandArmsSettingsGui.class);
        break;
      case 5:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMoveRotateGui.class);
        break;
      case 6:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandPresetPosesGui.class);
        break;
      case 13:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListSelectedGui.class);
        break;
      case 18:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), TagCategoryListGui.class);
        break;
      case 22:
        mtPlayer.getArmorStandManager().setInvisibleGui(!mtPlayer.getArmorStandManager().isInvisibleGui());
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMenuGui.class);
        break;
      case 26:
        mtPlayer.getPlayer().closeInventory();
        break;

    }
  }
}

package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandBasicSettingsGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText((mtPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.GENERIC_27.getName())) : "ArmorStand Basic Settings";
  }


  @Override
  public int getLines() {
    return 3;
  }

  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inventory) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_ARM, ""), Material.IRON_SWORD, 0);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_BASICSETTINGS_BASEARMORSTAND, LangMsg.GENERAL_VISIBLE), Material.STONE_SLAB, 1);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_BASICSETTINGS_BASEARMORSTAND, LangMsg.GENERAL_INVISIBLE), Material.STONE_SLAB, 2);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_BASICSETTINGS_GRAVITY, LangMsg.GENERAL_ENABLED), Material.FEATHER, 3);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_BASICSETTINGS_GRAVITY, LangMsg.GENERAL_DISABLED), Material.FEATHER, 4);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_INVISIBLE, ""), Material.GLASS, 5);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_SIZE, ""), Material.OAK_PLANKS, 6);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_INVULNERABLE,""), Material.OBSIDIAN, 7);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_GLOWING, ""), Material.GLOWSTONE_DUST, 8);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_GUIIN, (mtPlayer.getArmorStandManager().isInvisibleGui() ? LangMsg.GENERAL_VISIBLE : LangMsg.GENERAL_INVISIBLE)), Material.NAME_TAG, (mtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 22);

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_RETURNMENU, ""), Material.NAME_TAG, 3, 18);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_LEAVE, ""), Material.NAME_TAG, 4, 26);
  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 0:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setArms(!armorStand.hasArms());
        });
        break;
      case 1:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setBasePlate(true);
        });
        break;
      case 2:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setBasePlate(false);
        });
        break;
      case 3:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setGravity(false);
        });
        break;
      case 4:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setGravity(true);
        });
        break;
      case 5:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setVisible(false);
        });
        break;
      case 6:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setSmall(false);
        });
        break;
      case 7:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setInvulnerable(true);
        });
        break;
      case 8:
        mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setInvulnerable(false);
        });
        break;
      case 22:
        mtPlayer.getArmorStandManager().setInvisibleGui(!mtPlayer.getArmorStandManager().isInvisibleGui());
        break;

      case 18:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMenuGui.class);
        break;
      case 26:
        mtPlayer.getPlayer().closeInventory();
        break;

    }
  }

}

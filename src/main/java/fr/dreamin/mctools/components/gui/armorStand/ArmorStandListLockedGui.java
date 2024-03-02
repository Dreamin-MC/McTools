package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ArmorStandListLockedGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_45) : mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_LISTLOCKED_TITLE, "");
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inventory) {
    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 36, ItemsPreset.arrowNext.getItem(), 44, 0, 35, PaginationType.PAGE, mtPlayer.getArmorStandManager().getArmorStandLockedItemStack(), new ArrayList<>(), false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_REMOVEALL, ""), Material.BARRIER, 37);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_DESTROYARMORSTAND, ""), Material.IRON_AXE, 40);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_ADDALL, ""), Material.EMERALD_BLOCK, 43);

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 37:
        mtPlayer.getArmorStandManager().removeAllArmorStandLocked(true);
        mtPlayer.getPlayer().closeInventory();
        mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GENERAL_HAVEALLARMORSTAND, LangMsg.GENERAL_REMOVED, LangMsg.GENERAL_SELECTED));
        break;
      case 43:

        mtPlayer.getArmorStandManager().addAllArmorStandSelected(mtPlayer.getArmorStandManager().getArmorStandLocked());
        mtPlayer.getArmorStandManager().removeAllArmorStandLocked(false);

        mtPlayer.getPlayer().closeInventory();
        mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GENERAL_HAVEALLARMORSTAND, LangMsg.GENERAL_ADDED, LangMsg.GENERAL_LOCKED));
        break;
      case 40:

        mtPlayer.getArmorStandManager().dispawnAllArmorStandLocked();

        mtPlayer.getPlayer().closeInventory();
        mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GENERAL_HAVEALLARMORSTAND, LangMsg.GENERAL_DESTROY, LangMsg.GENERAL_SELECTED));
        break;
      default:
        ArmorStand armorStand = mtPlayer.getArmorStandManager().getArmorStandLocked().get(indexPagination);

        if (action.equals(ClickType.LEFT)) {
          mtPlayer.getArmorStandManager().removeArmorStandLocked(armorStand, false);
          mtPlayer.getArmorStandManager().addArmorStandSelected(armorStand);

          McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListLockedGui.class);
          mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_HAVEAARMORSTANDSELECTED, LangMsg.GENERAL_ADDED));
        }
        else if (action.equals(ClickType.RIGHT)) {

          mtPlayer.getArmorStandManager().removeArmorStandLocked(armorStand, true);

          McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListLockedGui.class);
          mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GENERAL_HAVEARMORSTAND, LangMsg.GENERAL_REMOVED, LangMsg.GENERAL_LOCKED));
        }
        else if (action.equals(ClickType.SHIFT_LEFT) || action.equals(ClickType.SHIFT_RIGHT)) mtPlayer.getPlayer().teleport(armorStand.getLocation());
        else if (action.equals(ClickType.MIDDLE)) {
          if (!armorStand.getHelmet().getType().equals(Material.AIR)) mtPlayer.getPlayer().getInventory().addItem(armorStand.getHelmet());
          else mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_ERROR_NOTITEMHEAD, ""));
        }
        break;
    }
  }
}

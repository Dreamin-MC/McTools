package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ArmorStandListSelectedGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.ARMOR_LIST.getName()) : mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_LISTSELECTED_TITLE, "");
  }

  @Override
  public int getLines() {
    return 6;
  }

  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inventory) {
    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 45, ItemsPreset.arrowNext.getItem(), 53, 0, 35, PaginationType.PAGE, mtPlayer.getArmorStandManager().getArmorStandSelectedItemStack(), new ArrayList<>(), false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_DESTROYARMORSTAND, ""), Material.IRON_AXE, 48);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_BACK, ""), Material.NAME_TAG, 3, 46);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_LEAVE, ""), Material.NAME_TAG, 4, 52);
    guiItems.create(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_DELETELIST, ""), Material.NAME_TAG,  4, 49);
  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 48:
        mtPlayer.getArmorStandManager().dispawnAllArmorStandSelected();
        mtPlayer.getPlayer().closeInventory();
        mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GENERAL_HAVEALLARMORSTAND, ""), LangMsg.GENERAL_DESTROY.getMsg(mtPlayer.getLang(), mtPlayer.getMsg(LangMsg.GENERAL_SELECTED, "")));
        break;
      case 46:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMenuGui.class);
        break;
      case 52:
        mtPlayer.getPlayer().closeInventory();
        break;
      case 49:
        mtPlayer.getArmorStandManager().removeAllArmorStandSelected(true);
        mtPlayer.getPlayer().closeInventory();
        break;
      default:
        if (indexPagination > -1) {
          ArmorStand armorStand = mtPlayer.getArmorStandManager().getArmorStandSelected().get(indexPagination);

          if (action.equals(ClickType.RIGHT)) {
            mtPlayer.getArmorStandManager().removeArmorStandSelected(armorStand, true);

            McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListSelectedGui.class);
            mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_HAVEAARMORSTANDSELECTED, LangMsg.GENERAL_REMOVED));
          }
          else if(action.equals(ClickType.SHIFT_RIGHT)) {
            mtPlayer.getPlayer().teleport(armorStand.getLocation());
            mtPlayer.getPlayer().closeInventory();
            mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_GENERAL_TELEPORTTOARMORSTAND, ""));
          }
          else if (action.equals(ClickType.MIDDLE)) {
            if (!armorStand.getHelmet().getType().equals(Material.AIR)) mtPlayer.getPlayer().getInventory().addItem(armorStand.getHelmet());
            else mtPlayer.getPlayer().sendMessage(mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_ERROR_NOTITEMHEAD, ""));
          }
        }
        break;
    }
  }
}

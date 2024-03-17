package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ArmorStandListRadiusGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_45.getName()) : mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_LISTRADIUS_TITLE, "");
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inventory) {
    return new GuiPaginationManager(ItemsPreset.arrowPrevious.getItem(), 36, ItemsPreset.arrowNext.getItem(), 44, 0, 35, GuiPaginationType.PAGE, mtPlayer.getArmorStandManager().getArmorStandRadiusItemStack(), new ArrayList<>(), false);
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
        mtPlayer.getArmorStandManager().removeAllArmorStandRadius(true);
        mtPlayer.getPlayer().closeInventory();
        mtPlayer.sendMsg(LangMsg.GENERAL_HAVEALLARMORSTAND, LangMsg.GENERAL_REMOVED, LangMsg.GENERAL_RADIUS);
        break;
      case 40:
        mtPlayer.getArmorStandManager().dispawnAllArmorStandRadius();
        mtPlayer.getPlayer().closeInventory();
        mtPlayer.sendMsg(LangMsg.GENERAL_HAVEALLARMORSTAND, LangMsg.GENERAL_DESTROY, LangMsg.GENERAL_SELECTED);
        break;
      case 43:
        mtPlayer.getArmorStandManager().addAllArmorStandSelected(mtPlayer.getArmorStandManager().getArmorStandRadius());
        mtPlayer.getArmorStandManager().removeAllArmorStandRadius(false);

        mtPlayer.getPlayer().closeInventory();
        mtPlayer.sendMsg(LangMsg.GENERAL_HAVEALLARMORSTAND, LangMsg.GENERAL_ADDED, LangMsg.GENERAL_RADIUS);
        break;
      default:

        if (indexPagination > -1) {
          ArmorStand armorStand = mtPlayer.getArmorStandManager().getArmorStandRadius().get(indexPagination);
          if (action.equals(ClickType.LEFT)) {
            mtPlayer.getArmorStandManager().removeArmorStandRadius(armorStand, false);
            mtPlayer.getArmorStandManager().addArmorStandSelected(armorStand);

            McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListRadiusGui.class);
            mtPlayer.sendMsg(LangMsg.GUI_ARMORSTAND_GENERAL_HAVEAARMORSTANDSELECTED, LangMsg.GENERAL_ADDED);
          }
          else if (action.equals(ClickType.RIGHT)) {
            mtPlayer.getArmorStandManager().removeArmorStandRadius(armorStand, true);

            McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListRadiusGui.class);
            mtPlayer.sendMsg(LangMsg.GUI_ARMORSTAND_GENERAL_HAVEAARMORSTANDSELECTED, LangMsg.GENERAL_REMOVED);
          }
          else if (action.equals(ClickType.SHIFT_LEFT)) {
            mtPlayer.getPlayer().teleport(armorStand.getLocation());
            mtPlayer.sendMsg(LangMsg.GUI_ARMORSTAND_GENERAL_TELEPORTTOARMORSTAND, "");
          }
          else if (action.equals(ClickType.SHIFT_RIGHT)) {
            armorStand.setGlowing(!armorStand.isGlowing());

            if (McTools.getService(GlowingEntities.class).isGlowing(mtPlayer.getPlayer(), armorStand)) {
              try {
                McTools.getService(GlowingEntities.class).setGlowing(armorStand, mtPlayer.getPlayer(), ChatColor.BLUE);
              } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
              }
            }
            else {
              try {
                McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, mtPlayer.getPlayer());
              } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
              }
            }
          }
          else if (action.equals(ClickType.MIDDLE)) {
            if (!armorStand.getHelmet().getType().equals(Material.AIR)) mtPlayer.getPlayer().getInventory().addItem(armorStand.getHelmet());
            else mtPlayer.sendMsg(LangMsg.GUI_ARMORSTAND_ERROR_NOTITEMHEAD, "");
          }
        }
        break;
    }
  }
}

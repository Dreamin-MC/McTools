package fr.dreamin.mctools.components.gui.tag;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.game.manager.BuildManager;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TagCategoryListGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_36.getName()) : LangMsg.GUI_TAGCATEGORY_TITLE.getMsg(mtPlayer.getLang(), "");
  }

  @Override
  public int getLines() {
    return 4;
  }

  @Override
  public GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return new GuiPaginationManager(new ItemBuilder(Material.ARROW).toItemStack(), 27, new ItemBuilder(Material.ARROW).toItemStack(), 35, 0, 26, GuiPaginationType.PAGE, BuildManager.getTagCategoryItemStacks(), Arrays.asList(), false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {
    mtPlayer.getBuildManager().setTagCategory(BuildManager.getTagCategorys().get(indexPagination));

    McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), TagListGui.class);
  }
}

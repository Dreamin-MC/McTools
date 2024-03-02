package fr.dreamin.mctools.components.gui.tag;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.build.Tag;
import fr.dreamin.mctools.components.game.manager.BuildManager;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TagListGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_45.getName()) : LangMsg.GUI_TAG_TITLE.getMsg(mtPlayer.getLang(), "");
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return new PaginationManager(new ItemBuilder(Material.ARROW).toItemStack(), 36, new ItemBuilder(Material.ARROW).toItemStack(), 44, 0, 35, PaginationType.PAGE, BuildManager.getTagItemStacks(mtPlayer), Arrays.asList(), false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {
    Tag tag = BuildManager.getTagsByCategory(mtPlayer.getBuildManager().getTagCategory()).get(indexPagination);

    mtPlayer.getBuildManager().setTag(tag);
    mtPlayer.getPlayer().closeInventory();
  }
}

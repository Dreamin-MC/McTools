package fr.dreamin.mctools.components.gui.tag;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.game.manager.BuildManager;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TagCategoryListGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_36.getName()) : "Tag Category List";
  }

  @Override
  public int getLines() {
    return 4;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return new PaginationManager(new ItemBuilder(Material.ARROW).toItemStack(), 27, new ItemBuilder(Material.ARROW).toItemStack(), 35, 0, 26, PaginationType.PAGE, BuildManager.getTagCategoryItemStacks(), Arrays.asList(), false);
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

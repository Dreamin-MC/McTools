package fr.dreamin.dreamintools.components.gui.tag;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.colors.CustomChatColor;
import fr.dreamin.dreamintools.api.gui.*;
import fr.dreamin.dreamintools.api.items.ItemBuilder;
import fr.dreamin.dreamintools.components.build.Tag;
import fr.dreamin.dreamintools.components.game.manager.BuildManager;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TagList implements GuiBuilder {

  @Override
  public String name(Player player) {
    return CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_45.getName()) + "七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七";
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return new PaginationManager(new ItemBuilder(Material.ARROW).toItemStack(), 36, new ItemBuilder(Material.ARROW).toItemStack(), 44, 0, 35, PaginationType.PAGE, BuildManager.getTagItemStacks(McTools.getService(PlayersService.class).getPlayer(player)), Arrays.asList(), false);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
      int index = McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

      Tag tag = BuildManager.getTagsByCategory(dtPlayer.getBuildManager().getTagCategory()).get(index);

      dtPlayer.getBuildManager().setTag(tag);

      player.closeInventory();

    }
  }
}

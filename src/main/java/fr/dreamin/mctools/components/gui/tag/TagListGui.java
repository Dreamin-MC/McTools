package fr.dreamin.mctools.components.gui.tag;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.build.Tag;
import fr.dreamin.mctools.components.game.manager.BuildManager;
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
  public String name(Player player) {
    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_45.getName()) : "Tag List";
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
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
      int index = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

      Tag tag = BuildManager.getTagsByCategory(MTPlayer.getBuildManager().getTagCategory()).get(index);

      MTPlayer.getBuildManager().setTag(tag);

      player.closeInventory();
    }
  }
}

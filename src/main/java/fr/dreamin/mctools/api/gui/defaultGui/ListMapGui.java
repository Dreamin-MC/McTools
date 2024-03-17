package fr.dreamin.mctools.api.gui.defaultGui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ListMapGui implements GuiBuilder {

  private List<ItemStack> items = new ArrayList<>();
  {

    for (World world : Bukkit.getWorlds()) {
      items.add(new ItemBuilder(Material.BEDROCK).setName(world.getName()).toItemStack());
    }

  }

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_36.getName()) : mtPlayer.getMsg(LangMsg.GUI_LISTMAP_TITLE, "");
  }

  @Override
  public int getLines() {
    return 4;
  }

  @Override
  public GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return new GuiPaginationManager(new ItemStack(Material.ARROW), 19, new ItemStack(Material.ARROW), 25, 10, 16, GuiPaginationType.LOOP_LINE, items, List.of() ,false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {
    if (Bukkit.getWorlds().get(indexPagination) != null) mtPlayer.getPlayer().teleport(Bukkit.getWorlds().get(indexPagination).getSpawnLocation());
  }
}

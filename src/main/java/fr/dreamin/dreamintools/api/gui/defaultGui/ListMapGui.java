package fr.dreamin.dreamintools.api.gui.defaultGui;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.colors.CustomChatColor;
import fr.dreamin.dreamintools.api.gui.*;
import fr.dreamin.dreamintools.api.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
  public String name(Player player) {
    return CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_36.getName());
  }

  @Override
  public int getLines() {
    return 4;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return new PaginationManager(new ItemStack(Material.ARROW), 19, new ItemStack(Material.ARROW), 25, 10, 16, PaginationType.LOOP_LINE, items, List.of() ,false);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {
    if (McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
      int index = McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());
      player.teleport(Bukkit.getWorlds().get(index).getSpawnLocation());

    }

  }
}

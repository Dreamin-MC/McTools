package fr.dreamin.dreamintools.api.gui.defaultGui;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.gui.GuiBuilder;
import fr.dreamin.dreamintools.api.gui.PaginationManager;
import fr.dreamin.dreamintools.api.gui.PaginationType;
import fr.dreamin.dreamintools.api.items.ItemBuilder;
import fr.dreamin.dreamintools.api.gui.GuiItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TestGui implements GuiBuilder {

  private List<ItemStack> items = new ArrayList<>();

  {
    items.add(new ItemBuilder(Material.DIAMOND).setName("§c§lDIAMOND").toItemStack());
    items.add(new ItemBuilder(Material.GOLD_INGOT).setName("§c§lGOLD").toItemStack());
    items.add(new ItemBuilder(Material.IRON_INGOT).setName("§c§lIRON").toItemStack());
    items.add(new ItemBuilder(Material.COAL).setName("§c§lCOAL").toItemStack());
    items.add(new ItemBuilder(Material.EMERALD).setName("§c§lEMERALD").toItemStack());
    items.add(new ItemBuilder(Material.REDSTONE).setName("§c§lREDSTONE").toItemStack());
    items.add(new ItemBuilder(Material.LAPIS_LAZULI).setName("§c§lLAPIS").toItemStack());
    items.add(new ItemBuilder(Material.QUARTZ).setName("§c§lQUARTZ").toItemStack());
    items.add(new ItemBuilder(Material.NETHERITE_INGOT).setName("§c§lNETHERITE").toItemStack());
    items.add(new ItemBuilder(Material.DIAMOND).setName("§c§lDIAMOND").toItemStack());
    items.add(new ItemBuilder(Material.GOLD_INGOT).setName("§c§lGOLD").toItemStack());
    items.add(new ItemBuilder(Material.IRON_INGOT).setName("§c§lIRON").toItemStack());
    items.add(new ItemBuilder(Material.COAL).setName("§c§lCOAL").toItemStack());
    items.add(new ItemBuilder(Material.EMERALD).setName("§c§lEMERALD").toItemStack());
    items.add(new ItemBuilder(Material.REDSTONE).setName("§c§lREDSTONE").toItemStack());
    items.add(new ItemBuilder(Material.LAPIS_LAZULI).setName("§c§lLAPIS").toItemStack());
    items.add(new ItemBuilder(Material.QUARTZ).setName("§c§lQUARTZ").toItemStack());
    items.add(new ItemBuilder(Material.NETHERITE_INGOT).setName("§c§lNETHERITE").toItemStack());
    items.add(new ItemBuilder(Material.DIAMOND).setName("§c§lDIAMOND").toItemStack());
  }

  @Override
  public String name(Player player) {
    return "TT";
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return new PaginationManager(new ItemBuilder(Material.ARROW).toItemStack(), 18, new ItemBuilder(Material.ARROW).toItemStack(), 26, 1, 17, PaginationType.LINE, items, List.of(0,8, 9, 17, 18) ,true);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems items) {
  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    McTools.getInstance().getGuiManager().getGuiConfig().openGuiForAll(getClass());

    if (McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
      int index = McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());
      player.sendMessage("Item " + items.get(index).getItemMeta().getDisplayName() + " clicked");

    }

  }
}

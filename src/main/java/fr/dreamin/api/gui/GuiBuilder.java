package fr.dreamin.api.gui;

import fr.dreamin.mctools.component.player.MTPlayer;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {
  public abstract String name(MTPlayer mtPlayer);
  public abstract int getLines();
  public abstract GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv);
  public abstract void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items);
  public abstract void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination);
}

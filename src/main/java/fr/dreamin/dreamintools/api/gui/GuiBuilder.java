package fr.dreamin.dreamintools.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {
  public abstract String name(Player player);
  public abstract int getLines();
  public abstract PaginationManager getPaginationManager(Player player, Inventory inv);
  public abstract void contents(Player player, Inventory inv, GuiItems items);
  public abstract void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action);


}

package fr.dreamin.mctools.components.gui.staff;

import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiItems;
import fr.dreamin.mctools.api.gui.GuiPaginationManager;
import fr.dreamin.mctools.api.gui.PictureGui;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FreezeGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return CustomChatColor.WHITE.getColorWithText(PictureGui.FREEZE.getName());
  }

  @Override
  public int getLines() {
    return 6;
  }

  @Override
  public GuiPaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

  }
}

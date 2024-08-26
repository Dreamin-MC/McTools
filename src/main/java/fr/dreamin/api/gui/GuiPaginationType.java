package fr.dreamin.api.gui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.api.math.Math;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public enum GuiPaginationType {
  PAGE {
    public void setPagination(Player player, GuiPaginationManager GUiPaginationManager, GuiBuilder menu, GuiItems items) {

      int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

      int itemsPerPage = GUiPaginationManager.getItemsPerPage();
      int totalPages = GUiPaginationManager.getTotalPages();
      int startSlot = GUiPaginationManager.getSlotStart();
      int endSlot = GUiPaginationManager.getSlotEnd();
      int startIndex = (page - 1) * itemsPerPage;

      if (page > 1) items.create(GUiPaginationManager.getPrevious(), GUiPaginationManager.getSlotPrevious());
      if (page < totalPages) items.create(GUiPaginationManager.getNext(), GUiPaginationManager.getSlotNext());

      for (int slot = startSlot; slot <= endSlot && startIndex < GUiPaginationManager.getItems().size(); slot++) {
        if (GUiPaginationManager.getNotAccountSlots().contains(slot)) continue;
        items.create(GUiPaginationManager.getItems().get(startIndex), slot);
        startIndex++;
      }
    }

    @Override
    public void setNext(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {

      if (GUiPaginationManager.isSync()) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPageForAll(gClass.getSimpleName());
      else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPage(player, gClass.getSimpleName());
      McTools.getService(GuiManager.class).open(player, gClass);
    }

    @Override
    public void setPrevious(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {
      if (GUiPaginationManager.isSync()) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePageForAll(gClass.getSimpleName());
      else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePage(player, gClass.getSimpleName());
      McTools.getService(GuiManager.class).open(player, gClass);
    }
  },
  LOOP_PAGE {
    @Override
    public void setPagination(Player player, GuiPaginationManager GUiPaginationManager, GuiBuilder menu, GuiItems items) {

      int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

      int itemsPerPage = GUiPaginationManager.getItemsPerPage();
      int totalPages = GUiPaginationManager.getTotalPages();
      int startSlot = GUiPaginationManager.getSlotStart();
      int endSlot = GUiPaginationManager.getSlotEnd();
      int startIndex = (page - 1) * itemsPerPage;

      items.create(GUiPaginationManager.getPrevious(), GUiPaginationManager.getSlotPrevious());
      items.create(GUiPaginationManager.getNext(), GUiPaginationManager.getSlotNext());
      for (int slot = startSlot; slot <= endSlot && startIndex < GUiPaginationManager.getItems().size(); slot++) {
        if (GUiPaginationManager.getNotAccountSlots().contains(slot)) continue;
        items.create(GUiPaginationManager.getItems().get(startIndex), slot);
        startIndex++;
      }
    }

    @Override
    public void setNext(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {
      int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, gClass.getSimpleName());
      int totalPages = GUiPaginationManager.getTotalPages();

      if (GUiPaginationManager.isSync()) {
        if (page == totalPages) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPageForAll(gClass.getSimpleName(), 1);
        else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPageForAll(gClass.getSimpleName());
      }
      else {
        if (page == totalPages) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(player, gClass.getSimpleName(), 1);
        else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPage(player, gClass.getSimpleName());
      }
      McTools.getService(GuiManager.class).open(player, gClass);
    }

    @Override
    public void setPrevious(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {
      int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, gClass.getSimpleName());
      int totalPages = GUiPaginationManager.getTotalPages();


      if (GUiPaginationManager.isSync()) {
        if (page == 1) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPageForAll(gClass.getSimpleName(), totalPages);
        else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePageForAll(gClass.getSimpleName());
      }
      else {
        if (page == 1) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addGuiPage(player, gClass.getSimpleName(), totalPages);
        else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePage(player, gClass.getSimpleName());
      }
      McTools.getService(GuiManager.class).open(player, gClass);
    }
  },
  LINE {
    @Override
    public void setPagination(Player player, GuiPaginationManager GUiPaginationManager, GuiBuilder menu, GuiItems items) {

      int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

      int itemsPerPage = GUiPaginationManager.getItemsPerPage();
      int startSlot = GUiPaginationManager.getSlotStart();
      int endSlot = GUiPaginationManager.getSlotEnd();

      List<ItemStack> listItems = Math.reorderItems(GUiPaginationManager.getItems(), indexStart - 1);

      if (indexStart > 1) items.create(GUiPaginationManager.getPrevious(), GUiPaginationManager.getSlotPrevious());
      if (indexStart - 1 + itemsPerPage < GUiPaginationManager.getItems().size()) items.create(GUiPaginationManager.getNext(), GUiPaginationManager.getSlotNext());

      int s = 0;

      for (int slot = startSlot; slot <= endSlot && s < listItems.size() ; slot++) {
        if (GUiPaginationManager.getNotAccountSlots().contains(slot)) continue;
        items.create(listItems.get(s), slot);
        s++;
      }
    }

    @Override
    public void setNext(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {
      if (GUiPaginationManager.isSync()) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPageForAll(gClass.getSimpleName());
      else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPage(player, gClass.getSimpleName());
      McTools.getService(GuiManager.class).open(player, gClass);
    }

    @Override
    public void setPrevious(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {
      if (GUiPaginationManager.isSync()) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePageForAll(gClass.getSimpleName());
      else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePage(player, gClass.getSimpleName());
      McTools.getService(GuiManager.class).open(player, gClass);
    }
  },
  LOOP_LINE {
    @Override
    public void setPagination(Player player, GuiPaginationManager GUiPaginationManager, GuiBuilder menu, GuiItems items) {

      int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

      int itemsPerPage = GUiPaginationManager.getItemsPerPage();
      int startSlot = GUiPaginationManager.getSlotStart();
      int endSlot = GUiPaginationManager.getSlotEnd();

      List<ItemStack> listItems = Math.reorderItemsAZ(GUiPaginationManager.getItems(), indexStart - 1);

      items.create(GUiPaginationManager.getPrevious(), GUiPaginationManager.getSlotPrevious());
      items.create(GUiPaginationManager.getNext(), GUiPaginationManager.getSlotNext());

      int s = 0;

      for (int slot = startSlot; slot <= endSlot && s < listItems.size(); slot++) {
        if (GUiPaginationManager.getNotAccountSlots().contains(slot)) continue;
        items.create(listItems.get(s), slot);
        s++;
      }
    }

    @Override
    public void setNext(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {
      if (GUiPaginationManager.isSync()) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPageForAll(gClass.getSimpleName());
      else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPage(player, gClass.getSimpleName());
      McTools.getService(GuiManager.class).open(player, gClass);
    }

    @Override
    public void setPrevious(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {
      if (GUiPaginationManager.isSync()) McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePageForAll(gClass.getSimpleName());
      else McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePage(player, gClass.getSimpleName());
      McTools.getService(GuiManager.class).open(player, gClass);
    }
  },
  AUTO_LINE(){
    @Override
    public void setPagination(Player player, GuiPaginationManager GUiPaginationManager, GuiBuilder menu, GuiItems items) {

    }

    @Override
    public void setNext(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {

    }

    @Override
    public void setPrevious(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass) {

    }
  };

  public abstract void setPagination(Player player, GuiPaginationManager GUiPaginationManager, GuiBuilder menu, GuiItems items);
  public abstract void setNext(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass);
  public abstract void setPrevious(Player player, GuiPaginationManager GUiPaginationManager, Class<? extends GuiBuilder> gClass);

}
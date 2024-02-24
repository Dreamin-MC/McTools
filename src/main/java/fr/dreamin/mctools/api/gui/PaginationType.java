package fr.dreamin.mctools.api.gui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.math.Math;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public enum PaginationType {
    PAGE {
      public void setPagination(Player player, PaginationManager paginationManager, GuiBuilder menu, GuiItems items) {

        int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

        int itemsPerPage = paginationManager.getItemsPerPage();
        int totalPages = paginationManager.getTotalPages();
        int startSlot = paginationManager.getSlotStart();
        int endSlot = paginationManager.getSlotEnd();
        int startIndex = (page - 1) * itemsPerPage;

        if (page > 1)
          items.create(paginationManager.getPrevious(), paginationManager.getSlotPrevious());
        if (page < totalPages)
          items.create(paginationManager.getNext(), paginationManager.getSlotNext());

        for (int slot = startSlot; slot <= endSlot && startIndex < paginationManager.getItems().size(); slot++) {

          if (paginationManager.getNotAccountSlots().contains(slot)) continue;

          items.create(paginationManager.getItems().get(startIndex), slot);
          startIndex++;
        }
      }

      @Override
      public void setNext(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {

        if (paginationManager.isSync()) {
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPageForAll(gClass.getSimpleName());
        }
        else
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPage(player, gClass.getSimpleName());
        McTools.getService(GuiManager.class).open(player, gClass);
      }

      @Override
      public void setPrevious(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {
        if (paginationManager.isSync()) {
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePageForAll(gClass.getSimpleName());
        }
        else
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePage(player, gClass.getSimpleName());
        McTools.getService(GuiManager.class).open(player, gClass);
      }
    },
    LOOP_PAGE {
      @Override
      public void setPagination(Player player, PaginationManager paginationManager, GuiBuilder menu, GuiItems items) {

        int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

        int itemsPerPage = paginationManager.getItemsPerPage();
        int totalPages = paginationManager.getTotalPages();
        int startSlot = paginationManager.getSlotStart();
        int endSlot = paginationManager.getSlotEnd();
        int startIndex = (page - 1) * itemsPerPage;

        items.create(paginationManager.getPrevious(), paginationManager.getSlotPrevious());
        items.create(paginationManager.getNext(), paginationManager.getSlotNext());
        for (int slot = startSlot; slot <= endSlot && startIndex < paginationManager.getItems().size(); slot++) {

          if (paginationManager.getNotAccountSlots().contains(slot)) continue;

          items.create(paginationManager.getItems().get(startIndex), slot);
          startIndex++;
        }
      }

      @Override
      public void setNext(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {
        int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, gClass.getSimpleName());
        int totalPages = paginationManager.getTotalPages();

        if (paginationManager.isSync()) {
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
      public void setPrevious(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {
        int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, gClass.getSimpleName());
        int totalPages = paginationManager.getTotalPages();


        if (paginationManager.isSync()) {
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
      public void setPagination(Player player, PaginationManager paginationManager, GuiBuilder menu, GuiItems items) {

        int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

        int itemsPerPage = paginationManager.getItemsPerPage();
        int startSlot = paginationManager.getSlotStart();
        int endSlot = paginationManager.getSlotEnd();

        List<ItemStack> listItems = Math.reorderItems(paginationManager.getItems(), indexStart - 1);

        if (indexStart > 1)
          items.create(paginationManager.getPrevious(), paginationManager.getSlotPrevious());
        if (indexStart - 1 + itemsPerPage < paginationManager.getItems().size())
          items.create(paginationManager.getNext(), paginationManager.getSlotNext());

        int s = 0;

        for (int slot = startSlot; slot <= endSlot && s < listItems.size() ; slot++) {

          if (paginationManager.getNotAccountSlots().contains(slot)) continue;

          items.create(listItems.get(s), slot);
          s++;

        }
      }

      @Override
      public void setNext(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {
        if (paginationManager.isSync()) {
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPageForAll(gClass.getSimpleName());
        }
        else
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPage(player, gClass.getSimpleName());
        McTools.getService(GuiManager.class).open(player, gClass);
      }

      @Override
      public void setPrevious(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {
        if (paginationManager.isSync()) {
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePageForAll(gClass.getSimpleName());
        }
        else
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePage(player, gClass.getSimpleName());
        McTools.getService(GuiManager.class).open(player, gClass);
      }
    },
    LOOP_LINE {
      @Override
      public void setPagination(Player player, PaginationManager paginationManager, GuiBuilder menu, GuiItems items) {

        int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());

        int itemsPerPage = paginationManager.getItemsPerPage();
        int startSlot = paginationManager.getSlotStart();
        int endSlot = paginationManager.getSlotEnd();

        List<ItemStack> listItems = Math.reorderItemsAZ(paginationManager.getItems(), indexStart - 1);

        items.create(paginationManager.getPrevious(), paginationManager.getSlotPrevious());
        items.create(paginationManager.getNext(), paginationManager.getSlotNext());

        int s = 0;

        for (int slot = startSlot; slot <= endSlot && s < listItems.size(); slot++) {

          if (paginationManager.getNotAccountSlots().contains(slot)) continue;

          items.create(listItems.get(s), slot);
          s++;

        }
      }

      @Override
      public void setNext(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {
        if (paginationManager.isSync()) {
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPageForAll(gClass.getSimpleName());
        }
        else
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().addPage(player, gClass.getSimpleName());
        McTools.getService(GuiManager.class).open(player, gClass);
      }

      @Override
      public void setPrevious(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {
        if (paginationManager.isSync()) {
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePageForAll(gClass.getSimpleName());
        }
        else
          McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removePage(player, gClass.getSimpleName());
        McTools.getService(GuiManager.class).open(player, gClass);
      }
    },
    AUTO_LINE(){
      @Override
      public void setPagination(Player player, PaginationManager paginationManager, GuiBuilder menu, GuiItems items) {

      }

      @Override
      public void setNext(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {

      }

      @Override
      public void setPrevious(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass) {

      }
    };

    public abstract void setPagination(Player player, PaginationManager paginationManager, GuiBuilder menu, GuiItems items);
    public abstract void setNext(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass);
    public abstract void setPrevious(Player player, PaginationManager paginationManager, Class<? extends GuiBuilder> gClass);

  }
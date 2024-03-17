package fr.dreamin.mctools.api.gui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.math.Math;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class GuiPageManager {

  private HashMap<Player, HashMap<String, Integer>> guiPage = new HashMap<>();

  public HashMap<Player, HashMap<String, Integer>> getTest() {return guiPage;}

  public void addGuiPage(Player player, String name, int page) {
    if (!guiPage.containsKey(player)) guiPage.put(player, new HashMap<>());
    guiPage.get(player).put(name, page);
  }

  public void addGuiPageForAll(String name, int page) {
    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getMtPlayers()) {
      addGuiPage(MTPlayer.getPlayer(), name, page);
    }
  }

  public void addPage(Player player, String name) {
    if (!guiPage.containsKey(player)) guiPage.put(player, new HashMap<>());
    if (!guiPage.get(player).containsKey(name)) guiPage.get(player).put(name, 0);
    guiPage.get(player).put(name, guiPage.get(player).get(name) + 1);
  }

  public void addPageForAll(String name) {
    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getMtPlayers()) {
      addPage(MTPlayer.getPlayer(), name);
    }
  }

  public void removePage(Player player, String name) {
    if (!guiPage.containsKey(player)) guiPage.put(player, new HashMap<>());
    if (!guiPage.get(player).containsKey(name)) guiPage.get(player).put(name, 0);
    guiPage.get(player).put(name, guiPage.get(player).get(name) - 1);
  }

  public void removePageForAll(String name) {
    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getMtPlayers()) {
      removePage(MTPlayer.getPlayer(), name);
    }
  }

  public void removeGuiPage(Player player, String name) {
    if (!guiPage.containsKey(player)) return;
    guiPage.get(player).remove(name);
  }

  public void clearGuiPage(Player player) {
    if (!guiPage.containsKey(player)) return;
    guiPage.get(player).clear();
  }

  public int getGuiPage(Player player, String name) {
    if (!containsGuiPage(player, name)) return -1;
    return guiPage.get(player).get(name);
  }

  public boolean containsGuiPage(Player player, String name) {
    if (!guiPage.containsKey(player)) return false;
    return guiPage.get(player).containsKey(name);
  }

  public boolean containsItemInPagination(GuiPaginationManager paginationmanager, int slot) {
    if (paginationmanager.getNotAccountSlots().contains(slot)) return false;
    if (slot >= paginationmanager.getSlotStart() && slot <= paginationmanager.getSlotEnd()) return true;
    return false;
  }

  public static int getIdItemInPagination(Player player, GuiPaginationManager GUiPaginationManager, int slot, GuiBuilder menu) {

    int index = 0;

    if (GUiPaginationManager.getType().equals(GuiPaginationType.PAGE) || GUiPaginationManager.getType().equals(GuiPaginationType.LOOP_PAGE)) {
      int page = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());
      int itemsPerPage = GUiPaginationManager.getItemsPerPage();
      int slotStart = GUiPaginationManager.getSlotStart();

      if (page == -1) return -1;

      List<Integer> notAccountSlots = GUiPaginationManager.getNotAccountSlots();

      // Filtrer pour ajuster l'index basé sur les slots exclus
      int adjustedIndex = 0;
      for (int i = slotStart; i < slot; i++) {
        if (!notAccountSlots.contains(i)) adjustedIndex++;
      }

      // Calcul ajusté pour l'index dans la pagination
      index = (page - 1) * itemsPerPage + adjustedIndex;

    }
    else if (GUiPaginationManager.getType().equals(GuiPaginationType.LINE)) {

      int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());
      int slotStart = GUiPaginationManager.getSlotStart();

      if (indexStart == -1) return -1;

      List<ItemStack> items = Math.reorderItems(GUiPaginationManager.getItems(), indexStart - 1);

      List<Integer> notAccountSlots = GUiPaginationManager.getNotAccountSlots();

      // Ajustement de l'index basé sur les slots non pris en compte
      int adjustedSlot = slot;
      for (Integer notAccountSlot : notAccountSlots) {
        // Si le slot cliqué est après un slot non pris en compte, on ajuste
        if (slot > notAccountSlot) adjustedSlot--;
      }

      // Trouver l'item correspondant dans les items réordonnés
      if (adjustedSlot >= 0 && adjustedSlot < items.size()) {
        ItemStack item = items.get(adjustedSlot);

        // Retourner l'index de l'item dans la liste originale des items
        for (int i = 0; i < GUiPaginationManager.getItems().size(); i++) {
          if (item.equals(GUiPaginationManager.getItems().get(i))) index = i;
        }
      }
    }
    else if (GUiPaginationManager.getType().equals(GuiPaginationType.LOOP_LINE)) {

      int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, menu.getClass().getSimpleName());
      int slotStart = GUiPaginationManager.getSlotStart();

      if (indexStart == -1) return -1;

      List<ItemStack> items = Math.reorderItemsAZ(GUiPaginationManager.getItems(), indexStart - 1);

      List<Integer> notAccountSlots = GUiPaginationManager.getNotAccountSlots();

      // Calcul de l'index ajusté pour les slots non pris en compte
      int adjustedSlot = slot;
      for (Integer notAccountSlot : notAccountSlots) {
        // Ajustement seulement si le slot cliqué est après un slot ignoré
        if (slot > notAccountSlot) {
          adjustedSlot--;
        }

        // Gestion du bouclage
        adjustedSlot = adjustedSlot % items.size(); // S'assure que l'index est valide dans le contexte du bouclage

        // Trouver l'item correspondant dans la liste des items réordonnés
        if (adjustedSlot >= 0 && adjustedSlot < items.size()) {
          ItemStack item = items.get(adjustedSlot);

          // Retourner l'index de l'item dans la liste originale des items
          for (int i = 0; i < GUiPaginationManager.getItems().size(); i++) {
            if (item.equals(GUiPaginationManager.getItems().get(i))) index = i;
          }
        }
      }
    }
    return index;
  }

}

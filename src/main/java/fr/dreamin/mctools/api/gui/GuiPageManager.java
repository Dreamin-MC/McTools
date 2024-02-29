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
  public HashMap<Player, HashMap<String, Integer>> getGuiPage() {
    return guiPage;
  }

  public void addGuiPage(Player player, String name, int page) {
    if (!guiPage.containsKey(player)) guiPage.put(player, new HashMap<>());
    guiPage.get(player).put(name, page);
  }

  public void addGuiPageForAll(String name, int page) {
    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
      addGuiPage(MTPlayer.getPlayer(), name, page);
    }
  }

  public void addPage(Player player, String name) {
    if (!guiPage.containsKey(player)) guiPage.put(player, new HashMap<>());
    if (!guiPage.get(player).containsKey(name)) guiPage.get(player).put(name, 0);
    guiPage.get(player).put(name, guiPage.get(player).get(name) + 1);
  }

  public void addPageForAll(String name) {
    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
      addPage(MTPlayer.getPlayer(), name);
    }
  }

  public void removePage(Player player, String name) {
    if (!guiPage.containsKey(player)) guiPage.put(player, new HashMap<>());
    if (!guiPage.get(player).containsKey(name)) guiPage.get(player).put(name, 0);
    guiPage.get(player).put(name, guiPage.get(player).get(name) - 1);
  }

  public void removePageForAll(String name) {
    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
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
    if (!guiPage.containsKey(player)) return 0;
    if (!guiPage.get(player).containsKey(name)) return 0;
    return guiPage.get(player).get(name);
  }

  public boolean containsGuiPage(Player player, String name) {
    if (!guiPage.containsKey(player)) return false;
    return guiPage.get(player).containsKey(name);
  }

  public boolean containsItemInPagination(PaginationManager paginationmanager, int slot) {
    if (paginationmanager.getNotAccountSlots().contains(slot)) return false;
    if (slot >= paginationmanager.getSlotStart() && slot <= paginationmanager.getSlotEnd()) return true;
    return false;
  }

  public int getIdItemInPagination(Player player, PaginationManager paginationManager, int slot, Class<? extends GuiBuilder> gClass) {

    int index = 0;

    if (paginationManager.getType().equals(PaginationType.PAGE) || paginationManager.getType().equals(PaginationType.LOOP_PAGE)) {
      int page = getGuiPage(player, gClass.getSimpleName());
      int itemsPerPage = paginationManager.getItemsPerPage();
      int slotStart = paginationManager.getSlotStart();

      List<Integer> notAccountSlots = paginationManager.getNotAccountSlots();

      // Filtrer pour ajuster l'index basé sur les slots exclus
      int adjustedIndex = 0;
      for (int i = slotStart; i < slot; i++) {
        if (!notAccountSlots.contains(i)) adjustedIndex++;
      }

      // Calcul ajusté pour l'index dans la pagination
      index = (page - 1) * itemsPerPage + adjustedIndex;
    } else if (paginationManager.getType().equals(PaginationType.LINE)) {

      int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, gClass.getSimpleName());
      int slotStart = paginationManager.getSlotStart();

      List<ItemStack> items = Math.reorderItems(paginationManager.getItems(), indexStart - 1);

      List<Integer> notAccountSlots = paginationManager.getNotAccountSlots();

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
        for (int i = 0; i < paginationManager.getItems().size(); i++) {
          if (item.equals(paginationManager.getItems().get(i))) index = i;
        }
      }
    } else if (paginationManager.getType().equals(PaginationType.LOOP_LINE)) {

      int indexStart = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getGuiPage(player, gClass.getSimpleName());
      int slotStart = paginationManager.getSlotStart();

      List<ItemStack> items = Math.reorderItemsAZ(paginationManager.getItems(), indexStart - 1);

      List<Integer> notAccountSlots = paginationManager.getNotAccountSlots();

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
          for (int i = 0; i < paginationManager.getItems().size(); i++) {
            if (item.equals(paginationManager.getItems().get(i))) index = i;
          }
        }
      }
    }
    return index;
  }
}

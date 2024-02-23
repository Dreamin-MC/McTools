package fr.dreamin.dreamintools.api.gui;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiManager implements Listener{

  private GuiConfig guiConfig = new GuiConfig();


  @EventHandler
  public void onClick(InventoryClickEvent event){
    Player player = (Player) event.getWhoClicked();
    Inventory inv = event.getInventory();
    ItemStack current = event.getCurrentItem();

    if(event.getCurrentItem() == null) return;


    McTools.getInstance().getRegisteredMenus().values().stream()
      .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name(player)))
      .forEach(menu -> {

        PaginationManager paginationManager = menu.getPaginationManager(player, inv);

        if (paginationManager != null) {

          if (event.getSlot() == paginationManager.getSlotNext() && current.getItemMeta().getDisplayName().equals(paginationManager.getNext().getItemMeta().getDisplayName())) {
            paginationManager.getType().setNext(player, paginationManager, menu.getClass());
          }
          else if (event.getSlot() == paginationManager.getSlotPrevious() && current.getItemMeta().getDisplayName().equals(paginationManager.getPrevious().getItemMeta().getDisplayName())) {
            paginationManager.getType().setPrevious(player, paginationManager, menu.getClass());
          }
        }

        menu.onClick(player, inv, current, event.getSlot(), event.getClick());
        event.setCancelled(true);
      });


  }


  @EventHandler(priority = EventPriority.HIGHEST)
  public void onCloseInventory(InventoryCloseEvent event) {

    Player player = (Player) event.getPlayer();

    if (!(event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW))) {
      McTools.getInstance().getGuiManager().getGuiConfig().getGuiOpen().remove(player.getUniqueId());
    }

  }

  public void addMenu(GuiBuilder m){
    McTools.getInstance().getRegisteredMenus().put(m.getClass(), m);
  }

  public void removeMenu(GuiBuilder m) {
    McTools.getInstance().getRegisteredMenus().remove(m.getClass(), m);}

  public void open(Player player, Class<? extends GuiBuilder> gClass){

    if(!McTools.getInstance().getRegisteredMenus().containsKey(gClass)) return;

    GuiBuilder menu = McTools.getInstance().getRegisteredMenus().get(gClass);

    Inventory inv = null;

    if (McTools.getCodex().isDefaultGui()) {
      if (getGuiConfig().containsNotAccountDefaultItem(gClass)) inv = Bukkit.createInventory(null, menu.getLines() * 9, menu.name(player));
      else inv = Bukkit.createInventory(null, getGuiConfig().getDefaultLines() * 9, menu.name(player));
    }
    else inv = Bukkit.createInventory(null, menu.getLines() * 9, menu.name(player));

    GuiItems items = new GuiItems(inv);

    try {

      PaginationManager paginationManager = menu.getPaginationManager(player, inv);

      if (paginationManager != null) {

        if (!paginationManager.isSync()) {
          if (!getGuiConfig().getGuiPageManager().containsGuiPage(player, menu.getClass().getSimpleName())) {
            getGuiConfig().getGuiPageManager().addGuiPage(player, menu.getClass().getSimpleName(), 1);
          }
        }
        else {
         for (DTPlayer dtPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
           if (!getGuiConfig().getGuiPageManager().containsGuiPage(dtPlayer.getPlayer(), menu.getClass().getSimpleName())) {
             getGuiConfig().getGuiPageManager().addGuiPage(dtPlayer.getPlayer(), menu.getClass().getSimpleName(), 1);
           }
         }
        }

        if (paginationManager.getSlotNext() > menu.getLines() * 9 || paginationManager.getSlotPrevious() > menu.getLines() *9)
          throw new IllegalArgumentException("The next or previous slot is out of the inventory | " + menu.getClass().getSimpleName());
        paginationManager.getType().setPagination(player, paginationManager, menu, items);

      }

      if (McTools.getCodex().isDefaultGui())
        if (!getGuiConfig().containsNotAccountDefaultItem(gClass)) getGuiConfig().setGuiDefaultItems(inv);

      menu.contents(player, inv, items);
      getGuiConfig().addGuiOpen(player, menu.getClass());

    } catch (Exception e) {
      throw new RuntimeException(e + " " + menu.getClass().getSimpleName());
    }

    Inventory finalInv = inv;
    new BukkitRunnable() {

      @Override
      public void run() {
        assert finalInv != null;
        player.openInventory(finalInv);
      }

    }.runTaskLater(McTools.getInstance(), 1);

  }

  public GuiConfig getGuiConfig() {
    return guiConfig;
  }

}
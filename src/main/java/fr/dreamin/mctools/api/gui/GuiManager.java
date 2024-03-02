package fr.dreamin.mctools.api.gui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.minecraft.Minecraft;
import fr.dreamin.mctools.api.service.Service;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiManager extends Service implements Listener{

  private GuiConfig guiConfig = new GuiConfig();
  private HashMap<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus = new HashMap<>();
  private HashMap<Player, HashMap<Inventory, GuiBuilder>> registeredPlayers = new HashMap<>();

  @Override
  public void onEnable() {
    super.onEnable();
    McTools.getInstance().getServer().getPluginManager().registerEvents(this, McTools.getInstance());
  }

  @EventHandler
  public void onClick(InventoryClickEvent event){
    Player player = (Player) event.getWhoClicked();
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);
    Inventory inv = event.getInventory();

    ItemStack current = event.getCurrentItem();
    AtomicInteger indexPagination = new AtomicInteger();
    indexPagination.set(-1);

    if(event.getCurrentItem() == null) return;

    HashMap<Inventory, GuiBuilder> guiPlayer = getRegisteredPlayers().get(player);

    if (guiPlayer == null) {
      getRegisteredMenus().values().stream()
        .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name(mtPlayer)))
        .forEach(menu -> {

          PaginationManager paginationManager = menu.getPaginationManager(mtPlayer, inv);

          if (paginationManager != null) {
            if (event.getSlot() == paginationManager.getSlotNext() && Minecraft.compareItem(paginationManager.getNext(), current)) paginationManager.getType().setNext(player, paginationManager, menu.getClass());
            else if (event.getSlot() == paginationManager.getSlotPrevious() && Minecraft.compareItem(paginationManager.getPrevious(), current)) paginationManager.getType().setPrevious(player, paginationManager, menu.getClass());
          }

          menu.onClick(mtPlayer, inv, current, event.getSlot(), event.getClick(), indexPagination.get());
          event.setCancelled(true);
        });
    }
    else {

      GuiBuilder m = guiPlayer.get(inv);

      if (m != null) {
        PaginationManager paginationManager = m.getPaginationManager(mtPlayer, inv);

        if (paginationManager != null) {
          if (event.getSlot() == paginationManager.getSlotNext() && Minecraft.compareItem(paginationManager.getNext(), current)) paginationManager.getType().setNext(player, paginationManager, m.getClass());
          else if (event.getSlot() == paginationManager.getSlotPrevious() && Minecraft.compareItem(paginationManager.getPrevious(), current)) paginationManager.getType().setPrevious(player, paginationManager, m.getClass());

          if (getGuiConfig().getGuiPageManager().containsItemInPagination(paginationManager, event.getSlot())) indexPagination.set(getGuiConfig().getGuiPageManager().getIdItemInPagination(mtPlayer.getPlayer(), paginationManager, event.getSlot(), m));

        }

        if (indexPagination.get() < -1) indexPagination.set(-1);

        m.onClick(mtPlayer, inv, current, event.getSlot(), event.getClick(), indexPagination.get());
        event.setCancelled(true);
      }
      else {
        getRegisteredMenus().values().stream()
          .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name(mtPlayer)))
          .forEach(menu -> {

            PaginationManager paginationManager = menu.getPaginationManager(mtPlayer, inv);

            if (paginationManager != null) {
              if (event.getSlot() == paginationManager.getSlotNext() && current.getItemMeta().getDisplayName().equals(paginationManager.getNext().getItemMeta().getDisplayName())) paginationManager.getType().setNext(player, paginationManager, menu.getClass());
              else if (event.getSlot() == paginationManager.getSlotPrevious() && current.getItemMeta().getDisplayName().equals(paginationManager.getPrevious().getItemMeta().getDisplayName())) paginationManager.getType().setPrevious(player, paginationManager, menu.getClass());
            }

            menu.onClick(mtPlayer, inv, current, event.getSlot(), event.getClick(), indexPagination.get());
            event.setCancelled(true);
          });
      }

    }
  }

  public void onCloseInventory(InventoryCloseEvent event) {

    Player player = (Player) event.getPlayer();
    if (!(event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW))) McTools.getService(GuiManager.class).getGuiConfig().getGuiOpen().remove(player.getUniqueId());
  }

  public void addMenu(GuiBuilder m){
    getRegisteredMenus().put(m.getClass(), m);
  }

  public void addMenu(GuiBuilder... m){
    for (GuiBuilder menu : m) {
      getRegisteredMenus().put(menu.getClass(), menu);
    }
  }

  public void addMenu(List<GuiBuilder> menus) {
    for (GuiBuilder menu : menus) {
      getRegisteredMenus().put(menu.getClass(), menu);
    }
  }

  public void removeMenu(GuiBuilder m) {
    getRegisteredMenus().remove(m.getClass(), m);}


  public void open(Player player, Class<? extends GuiBuilder> gClass){

    if(!getRegisteredMenus().containsKey(gClass)) return;

    GuiBuilder menu = getRegisteredMenus().get(gClass);
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);
    Inventory inv = null;

    if (McTools.getCodex().isDefaultGui()) {
      if (getGuiConfig().containsNotAccountDefaultItem(gClass)) inv = Bukkit.createInventory(null, menu.getLines() * 9, menu.name(mtPlayer));
      else inv = Bukkit.createInventory(null, getGuiConfig().getDefaultLines() * 9, menu.name(mtPlayer));
    }
    else inv = Bukkit.createInventory(null, menu.getLines() * 9, menu.name(mtPlayer));

    GuiItems items = new GuiItems(inv);

    try {

      PaginationManager paginationManager = menu.getPaginationManager(mtPlayer, inv);

      if (paginationManager != null) {

        if (!paginationManager.isSync())
          if (!getGuiConfig().getGuiPageManager().containsGuiPage(player, menu.getClass().getSimpleName())) getGuiConfig().getGuiPageManager().addGuiPage(player, menu.getClass().getSimpleName(), 1);
        else {
         for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getMtPlayers()) {
           if (!getGuiConfig().getGuiPageManager().containsGuiPage(MTPlayer.getPlayer(), menu.getClass().getSimpleName())) getGuiConfig().getGuiPageManager().addGuiPage(MTPlayer.getPlayer(), menu.getClass().getSimpleName(), 1);
         }
        }

        if (paginationManager.getSlotNext() > menu.getLines() * 9 || paginationManager.getSlotPrevious() > menu.getLines() *9) throw new IllegalArgumentException("The next or previous slot is out of the inventory | " + menu.getClass().getSimpleName());
        paginationManager.getType().setPagination(player, paginationManager, menu, items);
      }

      if (McTools.getCodex().isDefaultGui())
        if (!getGuiConfig().containsNotAccountDefaultItem(gClass)) getGuiConfig().setGuiDefaultItems(inv);

      menu.contents(mtPlayer, inv, items);
      getGuiConfig().addGuiOpen(player, menu.getClass());

    } catch (Exception e) {
      throw new RuntimeException(e + " " + menu.getClass().getSimpleName());
    }

    Inventory finalInv = inv;
    GuiBuilder finalGui = menu;

    new BukkitRunnable() {

      @Override
      public void run() {
        assert finalInv != null;
        assert finalGui != null;
        player.openInventory(finalInv);

        HashMap<Inventory, GuiBuilder> items = new HashMap<>();
        items.put(finalInv, finalGui);
        if (getRegisteredPlayers().containsKey(player)) {
          getRegisteredPlayers().remove(player);
          getRegisteredPlayers().put(player, items);
        }
        else getRegisteredPlayers().put(player, items);
      }

    }.runTaskLater(McTools.getInstance(), 1);

  }

  public HashMap<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
    return registeredMenus;
  }

  public HashMap<Player, HashMap<Inventory, GuiBuilder>> getRegisteredPlayers() {
    return registeredPlayers;
  }

  public GuiConfig getGuiConfig() {
    return guiConfig;
  }

}
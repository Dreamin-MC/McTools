package fr.dreamin.api.gui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.api.listener.gui.OnGuiClose;
import fr.dreamin.api.listener.gui.OnGuiOpen;
import fr.dreamin.api.minecraft.Minecraft;
import fr.dreamin.api.service.Service;
import fr.dreamin.mctools.component.player.MTPlayer;
import fr.dreamin.api.service.manager.players.PlayersService;
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

          GuiPaginationManager GUiPaginationManager = menu.getPaginationManager(mtPlayer, inv);

          if (GUiPaginationManager != null) {
            if (event.getSlot() == GUiPaginationManager.getSlotNext() && Minecraft.compareItem(GUiPaginationManager.getNext(), current)) GUiPaginationManager.getType().setNext(player, GUiPaginationManager, menu.getClass());
            else if (event.getSlot() == GUiPaginationManager.getSlotPrevious() && Minecraft.compareItem(GUiPaginationManager.getPrevious(), current)) GUiPaginationManager.getType().setPrevious(player, GUiPaginationManager, menu.getClass());
          }



          menu.onClick(mtPlayer, inv, current, event.getSlot(), event.getClick(), indexPagination.get());
          event.setCancelled(true);
        });
    }
    else {

      GuiBuilder m = guiPlayer.get(inv);

      if (m != null) {

        GuiPaginationManager GUiPaginationManager = m.getPaginationManager(mtPlayer, inv);

        if (GUiPaginationManager != null) {
          if (event.getSlot() == GUiPaginationManager.getSlotNext() && Minecraft.compareItem(GUiPaginationManager.getNext(), current)) GUiPaginationManager.getType().setNext(player, GUiPaginationManager, m.getClass());
          else if (event.getSlot() == GUiPaginationManager.getSlotPrevious() && Minecraft.compareItem(GUiPaginationManager.getPrevious(), current)) GUiPaginationManager.getType().setPrevious(player, GUiPaginationManager, m.getClass());

          if (getGuiConfig().getGuiPageManager().containsItemInPagination(GUiPaginationManager, event.getSlot())) indexPagination.set(getGuiConfig().getGuiPageManager().getIdItemInPagination(mtPlayer.getPlayer(), GUiPaginationManager, event.getSlot(), m));

        }

        if (indexPagination.get() < -1) indexPagination.set(-1);

        m.onClick(mtPlayer, inv, current, event.getSlot(), event.getClick(), indexPagination.get());
        event.setCancelled(true);
      }
      else {
        getRegisteredMenus().values().stream()
          .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name(mtPlayer)))
          .forEach(menu -> {

            GuiPaginationManager GUiPaginationManager = menu.getPaginationManager(mtPlayer, inv);

            if (GUiPaginationManager != null) {
              if (event.getSlot() == GUiPaginationManager.getSlotNext() && current.getItemMeta().getDisplayName().equals(GUiPaginationManager.getNext().getItemMeta().getDisplayName())) GUiPaginationManager.getType().setNext(player, GUiPaginationManager, menu.getClass());
              else if (event.getSlot() == GUiPaginationManager.getSlotPrevious() && current.getItemMeta().getDisplayName().equals(GUiPaginationManager.getPrevious().getItemMeta().getDisplayName())) GUiPaginationManager.getType().setPrevious(player, GUiPaginationManager, menu.getClass());
            }

            menu.onClick(mtPlayer, inv, current, event.getSlot(), event.getClick(), indexPagination.get());
            event.setCancelled(true);
          });
      }

    }
  }

  @EventHandler
  public void onCloseInventory(InventoryCloseEvent event) {

    Player player = (Player) event.getPlayer();

    Inventory close = event.getInventory();

    HashMap<Inventory, GuiBuilder> guiPlayer = getRegisteredPlayers().get(player);

    if (guiPlayer != null) {
      GuiBuilder m = guiPlayer.get(close);

      if (m != null) {
        MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

        OnGuiClose.callEvent(mtPlayer, m, close);
      }
      else
        if (!(event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW))) McTools.getService(GuiManager.class).getGuiConfig().getGuiOpen().remove(player.getUniqueId());
    }
    else if (!(event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW))) McTools.getService(GuiManager.class).getGuiConfig().getGuiOpen().remove(player.getUniqueId());

    if (!(event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW))) McTools.getService(GuiManager.class).getGuiConfig().getGuiOpen().remove(player.getUniqueId());


  }

  public void addMenu(GuiBuilder... m){
    for (GuiBuilder menu : m) {
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

    inv = Bukkit.createInventory(null, menu.getLines() * 9, menu.name(mtPlayer));

    GuiItems items = new GuiItems(inv);

    try {

      GuiPaginationManager GUiPaginationManager = menu.getPaginationManager(mtPlayer, inv);

      if (GUiPaginationManager != null) {

        if (!GUiPaginationManager.isSync())
          if (!getGuiConfig().getGuiPageManager().containsGuiPage(player, menu.getClass().getSimpleName())) getGuiConfig().getGuiPageManager().addGuiPage(player, menu.getClass().getSimpleName(), 1);
        else {
         for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getMtPlayers()) {
           if (!getGuiConfig().getGuiPageManager().containsGuiPage(MTPlayer.getPlayer(), menu.getClass().getSimpleName())) getGuiConfig().getGuiPageManager().addGuiPage(MTPlayer.getPlayer(), menu.getClass().getSimpleName(), 1);
         }
        }

        if (GUiPaginationManager.getSlotNext() > menu.getLines() * 9 || GUiPaginationManager.getSlotPrevious() > menu.getLines() *9) throw new IllegalArgumentException("The next or previous slot is out of the inventory | " + menu.getClass().getSimpleName());
        GUiPaginationManager.getType().setPagination(player, GUiPaginationManager, menu, items);
      }

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

        OnGuiOpen.callEvent(mtPlayer, finalGui, finalInv);

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
package fr.dreamin.mctools.api.gui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.paper.services.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuiConfig {

  private GuiBuilder mainGui;
  private GuiPageManager guiPageManager = new GuiPageManager();
  private HashMap<Integer, Material> defaultItems = new HashMap<>();
  private HashMap<UUID, Class<? extends GuiBuilder>> guiOpen = new HashMap<>();
  private List<Class<? extends GuiBuilder>> notAccountDefaultItems = new ArrayList<>();
  private int defaultLines = 5;


  public GuiConfig() {
  }

  public void setMainGui(GuiBuilder mainGui) {
    this.mainGui = mainGui;
  }

  public GuiBuilder getMainGui() {
    return mainGui;
  }

  public GuiPageManager getGuiPageManager() {
    return guiPageManager;
  }

  //GuiOpen
  public void addGuiOpen(Player player, Class<? extends GuiBuilder> gClass) {
    getGuiOpen().put(player.getUniqueId(), gClass);
  }

  public void removeGuiOpen(Player player) {
    getGuiOpen().remove(player.getUniqueId());
  }

  public boolean containsGuiOpen(Player player) {
    return getGuiOpen().containsKey(player.getUniqueId());
  }

  public Class<? extends GuiBuilder> getPlayerGuiOpen(Player player) {
    return getGuiOpen().get(player.getUniqueId());
  }

  public HashMap<UUID, Class<? extends GuiBuilder>> getGuiOpen() {
    return guiOpen;
  }

  public void openGui(Player player) {
    if (!containsGuiOpen(player)) return;
    McTools.getInstance().getGuiManager().open(player, getGuiOpen().get(player));
  }

  public void openGuiForAll(Class<? extends GuiBuilder> gClass) {
    for (DTPlayer dtPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      Player player = dtPlayer.getPlayer();

      if (!containsGuiOpen(player)) continue;

      if (getPlayerGuiOpen(player).getSimpleName().equals(gClass.getSimpleName()))
        McTools.getInstance().getGuiManager().open(player, gClass);
    }
  }


  //DefaultItems

  public HashMap<Integer, Material> getDefaultItems() {
    return defaultItems;
  }

  public void setDefaultItems(HashMap<Integer, Material> defaultItems) {
    this.defaultItems = defaultItems;
  }

  public void setDefaultItem(int slot, Material material) {
    defaultItems.put(slot, material);
  }

  public void addDefaultItem(int slot, Material material) {
    defaultItems.put(slot, material);
  }

  public void setGuiDefaultItems(Inventory inv) {
    for (int slot : defaultItems.keySet()) {
      inv.setItem(slot, new ItemStack(defaultItems.get(slot)));
    }
  }



  //NotAccountDefaultItems
  public List<Class<? extends GuiBuilder>> getNotAccountDefaultItems() {
    return notAccountDefaultItems;
  }

  public void addNotAccountDefaultItem(Class<? extends GuiBuilder> gClass) {
    notAccountDefaultItems.add(gClass);
  }

  public void removeNotAccountDefaultItem(Class<? extends GuiBuilder> gClass) {
    notAccountDefaultItems.remove(gClass);
  }

  public boolean containsNotAccountDefaultItem(Class<? extends GuiBuilder> gClass) {
    return notAccountDefaultItems.contains(gClass);
  }


  //DefaultLines

  public int getDefaultLines() {
    return defaultLines;
  }

  public void setDefaultLines(int defaultLines) {
    this.defaultLines = defaultLines;
  }
}

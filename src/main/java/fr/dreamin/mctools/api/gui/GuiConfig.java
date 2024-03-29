package fr.dreamin.mctools.api.gui;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuiConfig {

  @Getter @Setter private GuiBuilder mainGui;
  @Getter private GuiPageManager guiPageManager = new GuiPageManager();
  @Getter @Setter private HashMap<Integer, Material> defaultItems = new HashMap<>();
  private HashMap<UUID, Class<? extends GuiBuilder>> guiOpen = new HashMap<>();
  private List<Class<? extends GuiBuilder>> notAccountDefaultItems = new ArrayList<>();

  @Getter @Setter private int defaultLines = 5;

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
    McTools.getService(GuiManager.class).open(player, getGuiOpen().get(player));
  }

  public void openGuiForAll(Class<? extends GuiBuilder> gClass) {
    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getMtPlayers()) {

      Player player = MTPlayer.getPlayer();

      if (!containsGuiOpen(player)) continue;

      if (getPlayerGuiOpen(player).getSimpleName().equals(gClass.getSimpleName()))
        McTools.getService(GuiManager.class).open(player, gClass);
    }
  }


  //DefaultItems

  public void setDefaultItem(int slot, Material material) {
    defaultItems.put(slot, material);
  }

  public void addDefaultItem(int slot, Material material) {
    defaultItems.put(slot, material);
  }

  public void setGuiDefaultItems(Inventory inv) {
    defaultItems.forEach((slot, material) -> inv.setItem(slot, new ItemStack(material)));
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
}

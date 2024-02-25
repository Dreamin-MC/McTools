package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ArmorStandListLockedGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    return "§8» §e§lLOCKED";
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 36, ItemsPreset.arrowNext.getItem(), 44, 0, 35, PaginationType.PAGE, MTPlayer.getArmorStandManager().getArmorStandLockedItemStack(), new ArrayList<>(), false);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    guiItems.create("§cTout supprimer", Material.BARRIER, 37, "§7Supprimer tous les armor stands de votre liste verouillé.");
    guiItems.create("§cDétruir les armors stands", Material.IRON_AXE, 40, "§7Détruire tous les armor stands de votre liste verouillé.");
    guiItems.create("§aTout ajouter", Material.EMERALD_BLOCK, 43, "§7Ajouter tous les armor stands de votre liste verouillé à votre liste sélectionné.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    switch (slot) {
      case 37:
        MTPlayer.getArmorStandManager().removeAllArmorStandLocked(true);
        player.closeInventory();
        player.sendMessage("§cVous avez supprimé tous les armor stands de votre liste verouillé.");
        break;
      case 43:

        MTPlayer.getArmorStandManager().addAllArmorStandSelected(MTPlayer.getArmorStandManager().getArmorStandLocked());
        MTPlayer.getArmorStandManager().removeAllArmorStandLocked(false);

        player.closeInventory();
        player.sendMessage("§aVous avez ajouter tous les armorstand verouillé.");
        break;
      case 40:

        MTPlayer.getArmorStandManager().dispawnAllArmorStandLocked();

        player.closeInventory();
        player.sendMessage("§aVous avez détruit tous les armorstand sélectioné.");
        break;
      default:
        if (McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
          int index = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

          ArmorStand armorStand = MTPlayer.getArmorStandManager().getArmorStandLocked().get(index);

          if (action.equals(ClickType.LEFT)) {
            MTPlayer.getArmorStandManager().removeArmorStandLocked(armorStand, false);
            MTPlayer.getArmorStandManager().addArmorStandSelected(armorStand);

            McTools.getService(GuiManager.class).open(player, ArmorStandListLockedGui.class);
            player.sendMessage("§aVous avez ajouté un armor stand à votre sélection.");
          }
          else if (action.equals(ClickType.RIGHT)) {

            MTPlayer.getArmorStandManager().removeArmorStandLocked(armorStand, true);

            McTools.getService(GuiManager.class).open(player, ArmorStandListLockedGui.class);
            player.sendMessage("§cVous avez retiré un armor stand à votre list locked.");
          }
          else if (action.equals(ClickType.SHIFT_LEFT) || action.equals(ClickType.SHIFT_RIGHT)) player.teleport(armorStand.getLocation());
          else if (action.equals(ClickType.MIDDLE)) {
            if (!armorStand.getHelmet().getType().equals(Material.AIR)) player.getInventory().addItem(armorStand.getHelmet());
            else player.sendMessage("§cErreur : §7Cet armor stand n'a pas d'item en tête.");
          }
        }
        break;
    }
  }
}

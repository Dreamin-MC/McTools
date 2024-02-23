package fr.dreamin.dreamintools.components.gui.armorStand;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.colors.CustomChatColor;
import fr.dreamin.dreamintools.api.gui.*;
import fr.dreamin.dreamintools.api.packUtils.ItemsPreset;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ArmorStandListSelected implements GuiBuilder {

  @Override
  public String name(Player player) {
    return CustomChatColor.WHITE.getColorWithText(PictureGui.ARMOR_LIST.getName());
  }

  @Override
  public int getLines() {
    return 6;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 45, ItemsPreset.arrowNext.getItem(), 53, 0, 35, PaginationType.PAGE, dtPlayer.getArmorStandManager().getArmorStandSelectedItemStack(), new ArrayList<>(), false);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("§cDétruir les armors stands", Material.IRON_AXE, 48, "§7Détruire tous les armor stands de votre liste sélectionné.");
    guiItems.create("Retour en arrière", Material.NAME_TAG, 3, 46, "§7Retourner à la liste des armor stands.");
    guiItems.create("Quitter", Material.NAME_TAG, 4, 52, "§7Fermer le menu.");
    guiItems.create("Supprimer la liste", Material.NAME_TAG,  4, 49, "§7Supprimer tous les armor stands de votre liste sélectionné.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (slot == 49) {

      dtPlayer.getArmorStandManager().removeAllArmorStandSelected(true);

      player.closeInventory();
    }
    else if (slot == 46) {
      McTools.getInstance().getGuiManager().open(player, ArmorStandMenuGui.class);
    }
    else if (slot == 52) {
      player.closeInventory();
    }
    else if (slot == 48) {
      dtPlayer.getArmorStandManager().dispawnAllArmorStandSelected();
      player.closeInventory();
      player.sendMessage("§aVous avez détruit tous les armorstand sélectioné.");
    }
    else {

      if (McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
        int index = McTools.getInstance().getGuiManager().getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

        ArmorStand armorStand = dtPlayer.getArmorStandManager().getArmorStandSelected().get(index);

        if (action.equals(ClickType.RIGHT)) {
          dtPlayer.getArmorStandManager().removeArmorStandSelected(armorStand, true);

          McTools.getInstance().getGuiManager().open(player, ArmorStandListSelected.class);
          player.sendMessage("§cVous avez supprimé l'armor stand de votre sélection.");
        }
        else if(action.equals(ClickType.SHIFT_RIGHT)) {
          player.teleport(armorStand.getLocation());
          player.closeInventory();
          player.sendMessage("§aVous avez été téléporté à l'armor stand.");
        }
        else if (action.equals(ClickType.MIDDLE)) {

          if (!armorStand.getHelmet().getType().equals(Material.AIR)) {
            player.getInventory().addItem(armorStand.getHelmet());
          }
          else
            player.sendMessage("§cErreur : §7Cet armor stand n'a pas d'item en tête.");
        }

      }
    }
  }
}

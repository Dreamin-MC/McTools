package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
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

public class ArmorStandListSelectedGui implements GuiBuilder {

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

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 45, ItemsPreset.arrowNext.getItem(), 53, 0, 35, PaginationType.PAGE, MTPlayer.getArmorStandManager().getArmorStandSelectedItemStack(), new ArrayList<>(), false);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("§cDétruir les armors stands", Material.IRON_AXE, 48, "§7Détruire tous les armor stands de votre liste sélectionné.");
    guiItems.create("Retour en arrière", Material.NAME_TAG, 3, 46, "§7Retourner au menu armor stands.");
    guiItems.create("Quitter", Material.NAME_TAG, 4, 52, "§7Fermer le menu.");
    guiItems.create("Supprimer la liste", Material.NAME_TAG,  4, 49, "§7Supprimer tous les armor stands de votre liste sélectionné.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    switch (slot) {
      case 48:
        MTPlayer.getArmorStandManager().dispawnAllArmorStandSelected();
        player.closeInventory();
        player.sendMessage("§aVous avez détruit tous les armorstand sélectioné.");
        break;
      case 46:
        McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
        break;
      case 52:
        player.closeInventory();
        break;
      case 49:
        MTPlayer.getArmorStandManager().removeAllArmorStandSelected(true);
        player.closeInventory();
        break;
      default:
        if (McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
          int index = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

          ArmorStand armorStand = MTPlayer.getArmorStandManager().getArmorStandSelected().get(index);

          if (action.equals(ClickType.RIGHT)) {
            MTPlayer.getArmorStandManager().removeArmorStandSelected(armorStand, true);

            McTools.getService(GuiManager.class).open(player, ArmorStandListSelectedGui.class);
            player.sendMessage("§cVous avez supprimé l'armor stand de votre sélection.");
          }
          else if(action.equals(ClickType.SHIFT_RIGHT)) {
            player.teleport(armorStand.getLocation());
            player.closeInventory();
            player.sendMessage("§aVous avez été téléporté à l'armor stand.");
          }
          else if (action.equals(ClickType.MIDDLE)) {
            if (!armorStand.getHelmet().getType().equals(Material.AIR)) player.getInventory().addItem(armorStand.getHelmet());
            else player.sendMessage("§cErreur : §7Cet armor stand n'a pas d'item en tête.");
          }

        }
        break;
    }
  }
}

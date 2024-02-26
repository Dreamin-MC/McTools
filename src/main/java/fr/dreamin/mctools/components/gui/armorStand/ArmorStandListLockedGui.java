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

public class ArmorStandListLockedGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_45) : "§8» §e§lLOCKED";
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
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.create("§cTout supprimer", Material.BARRIER, 37, "§7Supprimer tous les armor stands de votre liste verouillé.");
    guiItems.create("§cDétruir les armors stands", Material.IRON_AXE, 40, "§7Détruire tous les armor stands de votre liste verouillé.");
    guiItems.create("§aTout ajouter", Material.EMERALD_BLOCK, 43, "§7Ajouter tous les armor stands de votre liste verouillé à votre liste sélectionné.");

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 37:
        mtPlayer.getArmorStandManager().removeAllArmorStandLocked(true);
        mtPlayer.getPlayer().closeInventory();
        mtPlayer.getPlayer().sendMessage("§cVous avez supprimé tous les armor stands de votre liste verouillé.");
        break;
      case 43:

        mtPlayer.getArmorStandManager().addAllArmorStandSelected(mtPlayer.getArmorStandManager().getArmorStandLocked());
        mtPlayer.getArmorStandManager().removeAllArmorStandLocked(false);

        mtPlayer.getPlayer().closeInventory();
        mtPlayer.getPlayer().sendMessage("§aVous avez ajouter tous les armorstand verouillé.");
        break;
      case 40:

        mtPlayer.getArmorStandManager().dispawnAllArmorStandLocked();

        mtPlayer.getPlayer().closeInventory();
        mtPlayer.getPlayer().sendMessage("§aVous avez détruit tous les armorstand sélectioné.");
        break;
      default:
        if (McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(mtPlayer.getPlayer(), inv), slot)) {
          int index = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getIdItemInPagination(mtPlayer.getPlayer(), getPaginationManager(mtPlayer.getPlayer(), inv), slot, getClass());

          ArmorStand armorStand = mtPlayer.getArmorStandManager().getArmorStandLocked().get(index);

          if (action.equals(ClickType.LEFT)) {
            mtPlayer.getArmorStandManager().removeArmorStandLocked(armorStand, false);
            mtPlayer.getArmorStandManager().addArmorStandSelected(armorStand);

            McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListLockedGui.class);
            mtPlayer.getPlayer().sendMessage("§aVous avez ajouté un armor stand à votre sélection.");
          }
          else if (action.equals(ClickType.RIGHT)) {

            mtPlayer.getArmorStandManager().removeArmorStandLocked(armorStand, true);

            McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandListLockedGui.class);
            mtPlayer.getPlayer().sendMessage("§cVous avez retiré un armor stand à votre list locked.");
          }
          else if (action.equals(ClickType.SHIFT_LEFT) || action.equals(ClickType.SHIFT_RIGHT)) mtPlayer.getPlayer().teleport(armorStand.getLocation());
          else if (action.equals(ClickType.MIDDLE)) {
            if (!armorStand.getHelmet().getType().equals(Material.AIR)) mtPlayer.getPlayer().getInventory().addItem(armorStand.getHelmet());
            else mtPlayer.getPlayer().sendMessage("§cErreur : §7Cet armor stand n'a pas d'item en tête.");
          }
        }
        break;
    }
  }
}

package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ArmorStandListRadius implements GuiBuilder {

  @Override
  public String name(Player player) {
    return "§8» §e§lRADIUS";
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);
    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 36, ItemsPreset.arrowNext.getItem(), 44, 0, 35, PaginationType.PAGE, dtPlayer.getArmorStandManager().getArmorStandRadiusItemStack(), new ArrayList<>(), false);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("§cTout supprimer", Material.BARRIER, 37, "§7Supprimer tous les armor stands de votre liste radius.");
    guiItems.create("§cDétruir les armors stands", Material.IRON_AXE, 40, "§7Détruire tous les armor stands de votre liste radius.");
    guiItems.create("§aTout ajouter", Material.EMERALD_BLOCK, 43, "§7Ajouter tous les armor stands de votre liste radius à votre liste sélectionné.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (slot == 37) {
      dtPlayer.getArmorStandManager().removeAllArmorStandRadius(true);
      player.closeInventory();
      player.sendMessage("§cVous avez supprimé tous les armor stands de votre liste radius.");
    }
    else if (slot == 43) {

      dtPlayer.getArmorStandManager().addAllArmorStandSelected(dtPlayer.getArmorStandManager().getArmorStandRadius());
      dtPlayer.getArmorStandManager().removeAllArmorStandRadius(false);

      player.closeInventory();
      player.sendMessage("§aVous avez ajouter tous les armorstand radius.");
    }
    else if (slot == 40) {

      dtPlayer.getArmorStandManager().dispawnAllArmorStandRadius();

      player.closeInventory();
      player.sendMessage("§aVous avez détruit tous les armorstand sélectioné.");
    }
    else {

      if (McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
        int index = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

        ArmorStand armorStand = dtPlayer.getArmorStandManager().getArmorStandRadius().get(index);
        if (action.equals(ClickType.LEFT)) {


          dtPlayer.getArmorStandManager().removeArmorStandRadius(armorStand, false);
          dtPlayer.getArmorStandManager().addArmorStandSelected(armorStand);

          McTools.getService(GuiManager.class).open(player, ArmorStandListRadius.class);
          player.sendMessage("§aVous avez ajouté un armor stand à votre sélection.");
        } else if (action.equals(ClickType.RIGHT)) {
          dtPlayer.getArmorStandManager().removeArmorStandRadius(armorStand, true);

          McTools.getService(GuiManager.class).open(player, ArmorStandListRadius.class);
          player.sendMessage("§cVous avez retiré un armor stand à votre sélection.");
        } else if (action.equals(ClickType.SHIFT_LEFT)) {
          player.teleport(armorStand.getLocation());
          player.sendMessage("§aVous avez été téléporté à l'armor stand.");
        } else if (action.equals(ClickType.SHIFT_RIGHT)) {
          armorStand.setGlowing(!armorStand.isGlowing());

          if (McTools.getService(GlowingEntities.class).isGlowing(dtPlayer.getPlayer(), armorStand)) {
            try {
              McTools.getService(GlowingEntities.class).setGlowing(armorStand, dtPlayer.getPlayer(), ChatColor.BLUE);
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          } else {
            try {
              McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, dtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }


          player.sendMessage("§aVous avez " + (armorStand.isGlowing() ? "mis" : "retiré") + " le glowing de l'armor stand.");
        } else if (action.equals(ClickType.MIDDLE)) {

          if (!armorStand.getHelmet().getType().equals(Material.AIR)) {
            player.getInventory().addItem(armorStand.getHelmet());
          } else
            player.sendMessage("§cErreur : §7Cet armor stand n'a pas d'item en tête.");
        }

      }

    }
  }
}

package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ArmorStandListRadiusGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    return McTools.getCodex().isPack() ? CustomChatColor.WHITE.getColorWithText(PictureGui.GENERIC_45) : "§8» §e§lRADIUS";
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);
    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 36, ItemsPreset.arrowNext.getItem(), 44, 0, 35, PaginationType.PAGE, MTPlayer.getArmorStandManager().getArmorStandRadiusItemStack(), new ArrayList<>(), false);
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("§cTout supprimer", Material.BARRIER, 37, "§7Supprimer tous les armor stands de votre liste radius.");
    guiItems.create("§cDétruir les armors stands", Material.IRON_AXE, 40, "§7Détruire tous les armor stands de votre liste radius.");
    guiItems.create("§aTout ajouter", Material.EMERALD_BLOCK, 43, "§7Ajouter tous les armor stands de votre liste radius à votre liste sélectionné.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    switch (slot) {
      case 37:
        MTPlayer.getArmorStandManager().removeAllArmorStandRadius(true);
        player.closeInventory();
        player.sendMessage("§cVous avez supprimé tous les armor stands de votre liste radius.");
        break;
      case 40:
        MTPlayer.getArmorStandManager().dispawnAllArmorStandRadius();
        player.closeInventory();
        player.sendMessage("§aVous avez détruit tous les armorstand sélectioné.");
        break;
      case 43:
        MTPlayer.getArmorStandManager().addAllArmorStandSelected(MTPlayer.getArmorStandManager().getArmorStandRadius());
        MTPlayer.getArmorStandManager().removeAllArmorStandRadius(false);

        player.closeInventory();
        player.sendMessage("§aVous avez ajouter tous les armorstand radius.");
        break;
      default:
        if (McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
          int index = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

          ArmorStand armorStand = MTPlayer.getArmorStandManager().getArmorStandRadius().get(index);
          if (action.equals(ClickType.LEFT)) {
            MTPlayer.getArmorStandManager().removeArmorStandRadius(armorStand, false);
            MTPlayer.getArmorStandManager().addArmorStandSelected(armorStand);

            McTools.getService(GuiManager.class).open(player, ArmorStandListRadiusGui.class);
            player.sendMessage("§aVous avez ajouté un armor stand à votre sélection.");
          }
          else if (action.equals(ClickType.RIGHT)) {
            MTPlayer.getArmorStandManager().removeArmorStandRadius(armorStand, true);

            McTools.getService(GuiManager.class).open(player, ArmorStandListRadiusGui.class);
            player.sendMessage("§cVous avez retiré un armor stand à votre sélection.");
          }
          else if (action.equals(ClickType.SHIFT_LEFT)) {
            player.teleport(armorStand.getLocation());
            player.sendMessage("§aVous avez été téléporté à l'armor stand.");
          }
          else if (action.equals(ClickType.SHIFT_RIGHT)) {
            armorStand.setGlowing(!armorStand.isGlowing());

            if (McTools.getService(GlowingEntities.class).isGlowing(MTPlayer.getPlayer(), armorStand)) {
              try {
                McTools.getService(GlowingEntities.class).setGlowing(armorStand, MTPlayer.getPlayer(), ChatColor.BLUE);
              } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
              }
            }
            else {
              try {
                McTools.getService(GlowingEntities.class).unsetGlowing(armorStand, MTPlayer.getPlayer());
              } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
              }
            }

            player.sendMessage("§aVous avez " + (armorStand.isGlowing() ? "mis" : "retiré") + " le glowing de l'armor stand.");
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

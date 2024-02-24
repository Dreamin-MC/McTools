package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.components.gui.tag.TagCategoryList;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandMenuGui implements GuiBuilder {


  @Override
  public String name(Player player) {
    return CustomChatColor.WHITE.getColorWithText(PictureGui.ARMORS.getName());
  }

  @Override
  public int getLines() {
    return 3;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {
    return null;
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("Paramètres de base", Material.ARMOR_STAND, 2, "Simple modifications pour l'armor stand");
    guiItems.create("Paramètres du corps", Material.STONE_SLAB, 3, "Simple ajustement du corps de l'armor stand");
    guiItems.create("Mouvement et rotation", Material.GLOWSTONE_DUST, 5, "Mauvaise position ? Déplacez et faites simplement pivoter l'armor stand");
    guiItems.create("Poses prédéfinies", Material.IRON_SWORD, 6, "Vous voulez juste du beau truc sans trop réfléchir ?");
    guiItems.create("Liste Armor Stand", Material.PAPER, 13, "Liste de tous les armors stand séléctionné");
    guiItems.create("Tag", Material.NAME_TAG, 18, "List des Catégories et des tags");

    guiItems.create("Quitter", Material.NAME_TAG, 4, 26);
    guiItems.create((dtPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (dtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 22);


  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    switch (slot) {
      case 2:
        McTools.getService(GuiManager.class).open(player, ArmorStandBasicSettings.class);
        break;
      case 3:
        McTools.getService(GuiManager.class).open(player, ArmorStandArmsSettings.class);
        break;
      case 5:
        McTools.getService(GuiManager.class).open(player, ArmorStandMoveRotate.class);
        break;
      case 6:
        McTools.getService(GuiManager.class).open(player, ArmorStandPresetPoses.class);
        break;
      case 13:
        McTools.getService(GuiManager.class).open(player, ArmorStandListSelected.class);
        break;
      case 18:
        McTools.getService(GuiManager.class).open(player, TagCategoryList.class);
        break;
      case 22:
        dtPlayer.getArmorStandManager().setInvisibleGui(!dtPlayer.getArmorStandManager().isInvisibleGui());
        McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
        break;
      case 26:
        player.closeInventory();
        return;

    }
  }
}

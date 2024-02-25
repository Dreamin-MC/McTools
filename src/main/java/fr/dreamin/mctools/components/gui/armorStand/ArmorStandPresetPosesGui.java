package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.minecraft.Minecraft;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandPresetPosesGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return McTools.getCodex().isRessourcepack() ? CustomChatColor.WHITE.getColorWithText((MTPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.ARMOR_POSE.getName())) : "ArmorStandPreset Preset Poses";
  }

  @Override
  public int getLines() {
    return 4;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inventory) {
    return null;
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("Defaut 1", Material.ARMOR_STAND, 0, "§7Définir la pose par défaut 1.");
    guiItems.create("Defaut 2", Material.ARMOR_STAND, 1, "§7Définir la pose par défaut 2.");
    guiItems.create("Defaut 3", Material.ARMOR_STAND, 2, "§7Définir la pose par défaut 2.");
    guiItems.create("Marche 1", Material.ARMOR_STAND, 3, "§7Définir la pose de marche 1.");
    guiItems.create("Marche 2", Material.ARMOR_STAND, 4, "§7Définir la pose de marche 2.");
    guiItems.create("Assis", Material.ARMOR_STAND, 5, "§7Définir la pose assis.");

    guiItems.create((MTPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (MTPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 31, "§7Passer en mode " + (MTPlayer.getArmorStandManager().isInvisibleGui() ? "visible" : "invisible") + ".");

    guiItems.create("Retour en arrière", Material.NAME_TAG, 3, 27, "§7Retourner à la liste des armor stands.");
    guiItems.create("Quitter", Material.NAME_TAG, 4, 35, "§7Fermer le menu.");

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    //TODO à refaire
    switch (slot) {
      case 0:
        Minecraft.setArmorStandPose(MTPlayer.getArmorStandManager().getArmorStandSelected().get(0), 345, 0, 10, 350, 0, 350, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        break;
      case 1:
        Minecraft.setArmorStandPose(MTPlayer.getArmorStandManager().getArmorStandSelected().get(0), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        break;
      case 2:
        Minecraft.setArmorStandPose(MTPlayer.getArmorStandManager().getArmorStandSelected().get(0), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        break;
      case 3:
        Minecraft.setArmorStandPose(MTPlayer.getArmorStandManager().getArmorStandSelected().get(0), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        break;
      case 4:
        Minecraft.setArmorStandPose(MTPlayer.getArmorStandManager().getArmorStandSelected().get(0), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        break;
      case 5:
        Minecraft.setArmorStandPose(MTPlayer.getArmorStandManager().getArmorStandSelected().get(0), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        break;
      case 31:
        MTPlayer.getArmorStandManager().setInvisibleGui(!MTPlayer.getArmorStandManager().isInvisibleGui());
        break;
      case 27:
        McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
        break;
      case 35:
        player.closeInventory();
        break;
    }
  }





}

package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.glowing.GlowingEntities;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandBasicSettingsGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return McTools.getCodex().isRessourcepack() ? CustomChatColor.WHITE.getColorWithText((MTPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.GENERIC_27.getName())) : "ArmorStand Basic Settings";

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
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    guiItems.create("Bras", Material.IRON_SWORD, 0);
    guiItems.create("Base Armor stand visible", Material.STONE_SLAB, 1, "Changer entre visible et invisible");
    guiItems.create("Base Armor stand invisible", Material.STONE_SLAB, 2, "Changer entre visible et invisible");
    guiItems.create("Gravité off", Material.FEATHER, 3, "Changer entre gravité ou non");
    guiItems.create("Gravité on", Material.FEATHER, 4, "Changer entre gravité ou non");
    guiItems.create("Invisible", Material.GLASS, 5, "Changer entre visible ou non");
    guiItems.create("Taille", Material.OAK_PLANKS, 6, "Mettre une taille normale ou petite");
    guiItems.create("Invulnerable", Material.OBSIDIAN, 7, "Invicible ou non");
    guiItems.create("Glowing", Material.GLOWSTONE_DUST, 8, "Glowing ou non");
    guiItems.create((MTPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (MTPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 22);

    guiItems.create("Retour en arrière", Material.NAME_TAG, 3, 18);
    guiItems.create("Quitter", Material.NAME_TAG, 4, 26);

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);


    switch (slot) {
      case 0:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setArms(!armorStand.hasArms());
        });
        break;
      case 1:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setBasePlate(true);
        });
        break;
      case 2:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setBasePlate(false);
        });
        break;
      case 3:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setGravity(false);
        });
        break;
      case 4:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setGravity(true);
        });
        break;
      case 5:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setVisible(false);
        });
        break;
      case 6:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setSmall(false);
        });
        break;
      case 7:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setInvulnerable(true);
        });
        break;
      case 8:
        MTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
          armorStand.setInvulnerable(false);
        });
        break;
      case 22:
        MTPlayer.getArmorStandManager().setInvisibleGui(!MTPlayer.getArmorStandManager().isInvisibleGui());
        break;

      case 18:
        McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
        break;
      case 26:
        player.closeInventory();
        break;

    }
  }

}

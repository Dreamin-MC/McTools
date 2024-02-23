package fr.dreamin.dreamintools.components.gui.armorStand;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.colors.CustomChatColor;
import fr.dreamin.dreamintools.api.gui.GuiBuilder;
import fr.dreamin.dreamintools.api.gui.GuiItems;
import fr.dreamin.dreamintools.api.gui.PaginationManager;
import fr.dreamin.dreamintools.api.gui.PictureGui;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmorStandBasicSettings implements GuiBuilder {

  @Override
  public String name(Player player) {
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    return CustomChatColor.WHITE.getColorWithText((dtPlayer.getArmorStandManager().isInvisibleGui() ? "七七七七七七七" : PictureGui.GENERIC_27.getName()));

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

    guiItems.create("Bras", Material.IRON_SWORD, 0);
    guiItems.create("Base Armor stand visible", Material.STONE_SLAB, 1, "Changer entre visible et invisible");
    guiItems.create("Base Armor stand invisible", Material.STONE_SLAB, 2, "Changer entre visible et invisible");
    guiItems.create("Gravité off", Material.FEATHER, 3, "Changer entre gravité ou non");
    guiItems.create("Gravité on", Material.FEATHER, 4, "Changer entre gravité ou non");
    guiItems.create("Invisible", Material.GLASS, 5, "Changer entre visible ou non");
    guiItems.create("Taille", Material.OAK_PLANKS, 6, "Mettre une taille normale ou petite");
    guiItems.create("Invulnerable", Material.OBSIDIAN, 7, "Invicible ou non");
    guiItems.create("Glowing", Material.GLOWSTONE_DUST, 8, "Glowing ou non");
    guiItems.create((dtPlayer.getArmorStandManager().isInvisibleGui() ? "Passage Visible" : "Passage Invisible"), Material.NAME_TAG, (dtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 22);

    guiItems.create("Retour en arrière", Material.NAME_TAG, 3, 18);
    guiItems.create("Quitter", Material.NAME_TAG, 4, 26);

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);


    if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Passage")) {
      dtPlayer.getArmorStandManager().setInvisibleGui(!dtPlayer.getArmorStandManager().isInvisibleGui());
    }
    else if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Retour")) {
      McTools.getInstance().getGuiManager().open(player, ArmorStandMenuGui.class);
      return;
    }
    else if (current.getType().equals(Material.NAME_TAG) && current.getItemMeta().getDisplayName().contains("Quitter")) {
      player.closeInventory();
      return;
    }
    else {
      for (ArmorStand armorStand : dtPlayer.getArmorStandManager().getArmorStandSelected()) {
        if (current.getItemMeta().getDisplayName().contains("Bras")) {
          armorStand.setArms(!armorStand.hasArms());
        }
        else if (current.getItemMeta().getDisplayName().contains("Base Armor stand visible")) {
          armorStand.setBasePlate(true);
        }
        else if (current.getItemMeta().getDisplayName().contains("Base Armor stand invisible")) {
          armorStand.setBasePlate(false);
        }
        else if (current.getItemMeta().getDisplayName().contains("Gravité off")) {
          armorStand.setGravity(false);
        }
        else if (current.getItemMeta().getDisplayName().contains("Gravité on")) {
          armorStand.setGravity(true);
        }
        else if (current.getItemMeta().getDisplayName().contains("Invisible")) {
          armorStand.setVisible(!armorStand.isVisible());
        }
        else if (current.getItemMeta().getDisplayName().contains("Taille")) {
          armorStand.setSmall(!armorStand.isSmall());
        }
        else if (current.getItemMeta().getDisplayName().contains("Invulnerable")) {
          armorStand.setInvulnerable(!armorStand.isInvulnerable());
        }
        else if (current.getItemMeta().getDisplayName().contains("Glowing")) {

          if (McTools.getInstance().getGlowingEntities().isGlowing(dtPlayer.getPlayer(), armorStand)) {
            try {
              McTools.getInstance().getGlowingEntities().setGlowing(armorStand, dtPlayer.getPlayer(), ChatColor.BLUE);
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
          else {
            try {
              McTools.getInstance().getGlowingEntities().unsetGlowing(armorStand, dtPlayer.getPlayer());
            } catch (ReflectiveOperationException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }
    }





    McTools.getInstance().getGuiManager().open(player, ArmorStandBasicSettings.class);

  }

}

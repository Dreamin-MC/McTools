package fr.dreamin.dreamintools.api.gui.defaultGui.voice;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.colors.CustomChatColor;
import fr.dreamin.dreamintools.api.gui.GuiBuilder;
import fr.dreamin.dreamintools.api.gui.GuiItems;
import fr.dreamin.dreamintools.api.gui.PaginationManager;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfigPlayerGui implements GuiBuilder {

  @Override
  public String name(Player player) {

    String name = "";

    for (DTPlayer vPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      if (vPlayer.getVoiceManager().isSelected())
        name = vPlayer.getPlayer().getName();

    }

    return "Modification de " + name;
  }

  @Override
  public int getLines() {
    return 6;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return null;
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    guiItems.createList(CustomChatColor.WHITE.getColorWithText(""), Material.BLACK_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 21, 23, 30, 32, 36, 37, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53});
    guiItems.createList(CustomChatColor.WHITE.getColorWithText(""), Material.WHITE_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 21, 23, 30, 32, 36, 37, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53});

    for (DTPlayer dtPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      if (dtPlayer.getVoiceManager().isSelected()) {

        guiItems.create((dtPlayer.getVoiceManager().getClient().isConnected() ?
          CustomChatColor.GREEN.getColorWithText(dtPlayer.getPlayer().getName()) :
          CustomChatColor.RED.getColorWithText(dtPlayer.getPlayer().getName())),
          dtPlayer.getPlayer().getName(), GuiItems.PlayerHeadMethod.PLAYER_NAME, 4);


        if (dtPlayer.getVoiceManager().getClient().isConnected()) {
          if (dtPlayer.getVoiceManager().isForcedMute())
            guiItems.create("Mute Forcé : Activé", Material.BARRIER, 2, 40);
          else
            guiItems.create("Mute Forcé : Désactivé", Material.BARRIER, 2, 40);
        }


      }

    }

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    for (DTPlayer dtPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      if (dtPlayer.getVoiceManager().isSelected()) {

        if (current.getItemMeta().getDisplayName().contains("Activé"))
          dtPlayer.getVoiceManager().setForcedMutet(false);
        else if (current.getItemMeta().getDisplayName().contains("Désactivé"))
          dtPlayer.getVoiceManager().setForcedMutet(true);


        McTools.getInstance().getGuiManager().open(player, ConfigPlayerGui.class);
      }
    }


  }
}

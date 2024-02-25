package fr.dreamin.mctools.api.gui.defaultGui.voice;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiItems;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.PaginationManager;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ListPlayerGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    return "Liste des joueurs";
  }

  @Override
  public int getLines() {
    return 3;
  }


  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return null;
  }

  @Override
  public void contents(Player player, Inventory inv, GuiItems guiItems) {

    int i = 0;

    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      MTPlayer.getVoiceManager().setSelected(false);

      guiItems.create((MTPlayer.getVoiceManager().getClient().isConnected() ?
        CustomChatColor.GREEN.getColorWithText(MTPlayer.getPlayer().getName()) :
        CustomChatColor.RED.getColorWithText(MTPlayer.getPlayer().getName())),
        MTPlayer.getPlayer().getName(), GuiItems.PlayerHeadMethod.PLAYER_NAME, i,
        "§7",
        "§7Cliquez pour modifier les paramètres de " + MTPlayer.getPlayer().getName(),
        "§7",
        "§7Information :",
        "§7Etat de connection : " + (MTPlayer.getVoiceManager().getClient().isConnected() ?
          CustomChatColor.GREEN.getColorWithText("Oui") :
          CustomChatColor.RED.getColorWithText("Non")),
        (MTPlayer.getVoiceManager().getClient().isConnected() ? "§7Volume côté client : " + MTPlayer.getVoiceManager().getClient().getVolume() + "%" : ""),
        (MTPlayer.getVoiceManager().getClient().isConnected() ? "§7Etat du micro : " + (MTPlayer.getVoiceManager().getClient().isMicrophoneActive() ? CustomChatColor.GREEN.getColorWithText("Activé") : CustomChatColor.RED.getColorWithText("Désactivé")) : ""),
        (MTPlayer.getVoiceManager().isForcedMute() ? "§7Etat du mute forcé : " + CustomChatColor.RED.getColorWithText("Activé") : "§7Etat du mute forcé : " + CustomChatColor.GREEN.getColorWithText("Désactivé"))

      );

      i++;
    }

    guiItems.create(CustomChatColor.YELLOW.getColorWithText("Mute tout le monde"), Material.BARRIER, 2, 21);
    guiItems.create(CustomChatColor.YELLOW.getColorWithText("Démute tout le monde"), Material.BARRIER, 1, 23);

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    if (current.getType().equals(Material.PLAYER_HEAD))
      for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

        if (current.getItemMeta().getDisplayName().contains(MTPlayer.getPlayer().getName())) {
          MTPlayer.getVoiceManager().setSelected(true);
          McTools.getService(GuiManager.class).open(player, ConfigPlayerGui.class);
        }
      }
    else if (current.getItemMeta().getDisplayName().contains("Mute")) {

      for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
        MTPlayer.getVoiceManager().setForcedMutet(true);
      }
      McTools.getService(GuiManager.class).open(player, ListPlayerGui.class);
    }
    else {
      for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
        MTPlayer.getVoiceManager().setForcedMutet(false);
      }
      McTools.getService(GuiManager.class).open(player, ListPlayerGui.class);
    }
  }
}

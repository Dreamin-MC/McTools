package fr.dreamin.mctools.api.gui.defaultGui.voice;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class VoiceListPlayerGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_TITLE, "");
  }

  @Override
  public int getLines() {
    return 3;
  }


  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 18, ItemsPreset.arrowNext.getItem(), 26, 0, 17, PaginationType.PAGE, McTools.getService(PlayersService.class).getPlayersStackForVoice(), Arrays.asList(), false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    if (mtPlayer.getVoiceManager() != null) mtPlayer.getVoiceManager().setSelected(null);

    guiItems.create(CustomChatColor.YELLOW.getColorWithText(mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_MUTEALL, "")), Material.BARRIER, 2, 21);
    guiItems.create(CustomChatColor.YELLOW.getColorWithText(mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_UNMUTEALL, "")), Material.BARRIER, 1, 23);

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    switch (slot) {
      case 21:
        McTools.getService(PlayersService.class).getMtPlayers().forEach(mtPlayer1 -> {
          if (mtPlayer1.getVoiceManager() != null) mtPlayer1.getVoiceManager().setForcedMutet(true);
        });
        break;
      case 23:
        McTools.getService(PlayersService.class).getMtPlayers().forEach(mtPlayer1 -> {
          if (mtPlayer1.getVoiceManager() != null) mtPlayer1.getVoiceManager().setForcedMutet(false);
        });
        break;
      default:
        if (indexPagination != -1) {
          MTPlayer mtPlayer1 = McTools.getService(PlayersService.class).getMtPlayers().get(indexPagination);
          if (mtPlayer1.getVoiceManager() != null && mtPlayer.getVoiceManager() != null) mtPlayer.getVoiceManager().setSelected(mtPlayer1);
          McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), VoiceConfigPlayerGui.class);
        }
        break;
    }
  }
}

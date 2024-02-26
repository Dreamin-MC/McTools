package fr.dreamin.mctools.api.gui.defaultGui.voice;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiItems;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.PaginationManager;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
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
    return LangMsg.VOICE_LISTPLAYER_TITLE.getMsg(McTools.getService(PlayersService.class).getPlayer(player).getLang());
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
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    int i = 0;

    for (MTPlayer mPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      mPlayer.getVoiceManager().setSelected(false);

      if (mPlayer.getVoiceManager().getClient().isConnected())
        guiItems.create(CustomChatColor.GREEN.getColorWithText(mPlayer.getPlayer().getName()), mPlayer.getPlayer().getName(), GuiItems.PlayerHeadMethod.PLAYER_NAME, i,
          "§7",
          LangMsg.VOICE_LISTPLAYER_LORE_MODIFPLAYER.getMsg(mtPlayer.getLang(), mPlayer.getPlayer().getName()),
          "§7",
          LangMsg.INFORMATION.getMsg(mPlayer.getLang()) + " :",
          LangMsg.VOICE_LISTPLAYER_LORE_CONNECTIONSTATUS.getMsg(mtPlayer.getLang(), LangMsg.YES.getMsg(mtPlayer.getLang())),
          LangMsg.VOICE_LISTPLAYER_LORE_CLIENTVOLUME.getMsg(mtPlayer.getLang(), mPlayer.getVoiceManager().getClient().getVolume() + "%"),
          LangMsg.VOICE_LISTPLAYER_LORE_MICROSTATUS.getMsg(mtPlayer.getLang(), (mPlayer.getVoiceManager().getClient().isMicrophoneActive() ? LangMsg.ENABLED.getMsg(mtPlayer.getLang()) : LangMsg.DISABLED.getMsg(mtPlayer.getLang()))),
          LangMsg.VOICE_LISTPLAYER_LORE_FORCEDMUTESTATUS.getMsg(mtPlayer.getLang(), mPlayer.getVoiceManager().isForcedMute() ? LangMsg.ENABLED.getMsg(mtPlayer.getLang()) : LangMsg.DISABLED.getMsg(mtPlayer.getLang()))
        );
      else
        guiItems.create(CustomChatColor.RED.getColorWithText(mPlayer.getPlayer().getName()), mPlayer.getPlayer().getName(), GuiItems.PlayerHeadMethod.PLAYER_NAME, i,
          "§7",
          LangMsg.VOICE_LISTPLAYER_LORE_MODIFPLAYER.getMsg(mtPlayer.getLang(), mPlayer.getPlayer().getName()),
          "§7",
          LangMsg.INFORMATION.getMsg(mPlayer.getLang()) + " :",
          LangMsg.VOICE_LISTPLAYER_LORE_CONNECTIONSTATUS.getMsg(mtPlayer.getLang(), LangMsg.NO.getMsg(mtPlayer.getLang()))
        );

      i++;
    }

    guiItems.create(CustomChatColor.YELLOW.getColorWithText(LangMsg.VOICE_LISTPLAYER_MUTEALL.getMsg(mtPlayer.getLang())), Material.BARRIER, 2, 21);
    guiItems.create(CustomChatColor.YELLOW.getColorWithText(LangMsg.VOICE_LISTPLAYER_UNMUTEALL.getMsg(mtPlayer.getLang())), Material.BARRIER, 1, 23);

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    if (current.getType().equals(Material.PLAYER_HEAD))
      for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

        if (current.getItemMeta().getDisplayName().contains(MTPlayer.getPlayer().getName())) {
          MTPlayer.getVoiceManager().setSelected(true);
          McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ConfigPlayerGui.class);
        }
      }
    else if (current.getItemMeta().getDisplayName().contains("Mute")) {

      for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
        MTPlayer.getVoiceManager().setForcedMutet(true);
      }
      McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ListPlayerGui.class);
    }
    else {
      for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
        MTPlayer.getVoiceManager().setForcedMutet(false);
      }
      McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ListPlayerGui.class);
    }
  }
}

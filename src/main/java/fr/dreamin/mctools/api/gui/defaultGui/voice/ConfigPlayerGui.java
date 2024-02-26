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

public class ConfigPlayerGui implements GuiBuilder {

  @Override
  public String name(Player player) {

    String name = "NULL";

    for (MTPlayer mtPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      if (mtPlayer.getVoiceManager().isSelected()) name = mtPlayer.getPlayer().getName();

      return LangMsg.VOICE_CONFIGPLAYER_TITLE.getMsg(mtPlayer.getLang(), name);
    }
    return LangMsg.VOICE_CONFIGPLAYER_TITLE.getMsg(Lang.en_US, name);
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
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.createList(CustomChatColor.WHITE.getColorWithText(""), Material.BLACK_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 21, 23, 30, 32, 36, 37, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53});
    guiItems.createList(CustomChatColor.WHITE.getColorWithText(""), Material.WHITE_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 21, 23, 30, 32, 36, 37, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53});

    for (MTPlayer mTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

      if (mTPlayer.getVoiceManager().isSelected()) {

        guiItems.create((mTPlayer.getVoiceManager().getClient().isConnected() ?
          CustomChatColor.GREEN.getColorWithText(mTPlayer.getPlayer().getName()) :
          CustomChatColor.RED.getColorWithText(mTPlayer.getPlayer().getName())),
          mTPlayer.getPlayer().getName(), GuiItems.PlayerHeadMethod.PLAYER_NAME, 4);

        if (mTPlayer.getVoiceManager().getClient().isConnected()) guiItems.create(LangMsg.VOICE_CONFIGPLAYER_FORCEDMUTE.getMsg(mTPlayer.getLang(), mTPlayer.getVoiceManager().isForcedMute() ? LangMsg.ENABLED.getMsg(mTPlayer.getLang()) : LangMsg.DISABLED.getMsg(mTPlayer.getLang())), Material.BARRIER, 2, 40);
      }

    }

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    for (MTPlayer MTPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {
      if (MTPlayer.getVoiceManager().isSelected()) MTPlayer.getVoiceManager().setForcedMutet(!MTPlayer.getVoiceManager().isForcedMute());
    }
    McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ConfigPlayerGui.class);
  }
}

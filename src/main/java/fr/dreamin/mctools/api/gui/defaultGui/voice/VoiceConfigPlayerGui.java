package fr.dreamin.mctools.api.gui.defaultGui.voice;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiBuilder;
import fr.dreamin.mctools.api.gui.GuiItems;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.PaginationManager;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VoiceConfigPlayerGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {

    String name = "NULL";

    if (mtPlayer.getVoiceManager() != null) name = mtPlayer.getVoiceManager().getSelected().getPlayer().getName();

    return mtPlayer.getMsg(LangMsg.GUI_VOICE_CONFIGPLAYER_TITLE, name);
  }

  @Override
  public int getLines() {
    return 6;
  }

  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inv) {
    return null;
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {

    guiItems.createList(CustomChatColor.WHITE.getColorWithText(""), Material.BLACK_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 21, 23, 30, 32, 36, 37, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53});
    guiItems.createList(CustomChatColor.WHITE.getColorWithText(""), Material.WHITE_STAINED_GLASS_PANE, new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 21, 23, 30, 32, 36, 37, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53});

    if (mtPlayer.getVoiceManager() != null) {
      MTPlayer mtPlayer1 = mtPlayer.getVoiceManager().getSelected();

      if (mtPlayer1.getVoiceManager() != null) {
        guiItems.create((mtPlayer1.getVoiceManager().getClient().isConnected() ? CustomChatColor.GREEN.getColorWithText(mtPlayer1.getPlayer().getName()) : CustomChatColor.RED.getColorWithText(mtPlayer1.getPlayer().getName())), mtPlayer1.getPlayer().getName(), GuiItems.PlayerHeadMethod.PLAYER_NAME, 4);
        if (mtPlayer1.getVoiceManager().getClient().isConnected()) guiItems.create(mtPlayer.getMsg(LangMsg.GUI_VOICE_CONFIGPLAYER_FORCEDMUTE, mtPlayer1.getVoiceManager().isForcedMute() ? LangMsg.GENERAL_ENABLED : LangMsg.GENERAL_DISABLED), Material.BARRIER, 2, 40);
      }

    }
  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {
    if (mtPlayer.getVoiceManager() != null) mtPlayer.getVoiceManager().getSelected().getVoiceManager().setForcedMutet(!mtPlayer.getVoiceManager().getSelected().getVoiceManager().isForcedMute());
    McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), VoiceConfigPlayerGui.class);
  }
}

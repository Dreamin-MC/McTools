package fr.dreamin.mctools.api.service.manager.players;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiItems;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.Service;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayersService extends Service {

  @Getter @Setter
  private Class<? extends MTPlayer> playerClass = null;

  @Getter @Setter
  private List<MTPlayer> MtPlayers = new ArrayList<>(), spectators = new ArrayList<>();

  //------ADDER------//

  public void addDTPlayer(MTPlayer dTPlayer) {
    if (!MtPlayers.contains(dTPlayer)) MtPlayers.add(dTPlayer);
  }

  public void addSpectators(Player player) {
    MTPlayer dTPlayer = getPlayer(player);

    if (!spectators.contains(dTPlayer)) spectators.add(dTPlayer);
  }

  //------REMOVER------//

  public void removeDTPlayer(MTPlayer dTPlayer) {
    if (MtPlayers.contains(dTPlayer)) MtPlayers.remove(dTPlayer);
  }

  public void removeSpectators(Player player) {
    MTPlayer dTPlayer = getPlayer(player);
    spectators.remove(dTPlayer);
  }

  //------METHODS------//

  public MTPlayer getPlayer(Player player) {
    for (MTPlayer dTPlayer : getMtPlayers()) {
      if (dTPlayer.getPlayer().equals(player)) return dTPlayer;
    }
    return null;
  }

  public MTPlayer getPlayerByName(String name) {
    for (MTPlayer dTPlayer : getMtPlayers()) {
      if (dTPlayer.getPlayer().getName().equals(name)) return dTPlayer;
    }
    return null;
  }

  public boolean contains(Player player) {
    for (MTPlayer MTPlayer : getMtPlayers()) {
      if (MTPlayer.getPlayer().equals(player)) return true;
    }
    return false;
  }

  public boolean containsByName(String name) {
    for (MTPlayer MTPlayer : getMtPlayers()) {
      if (MTPlayer.getPlayer().getName().equals(name)) return true;
    }
    return false;
  }

  public boolean ifPossibleToStart() {
    boolean result = false;

    for (MTPlayer MTPlayer : getMtPlayers()) {
      if (MTPlayer.getVoiceManager().getClient().isConnected()) result = true;
      else result = false;
    }
    return result;
  }

  public List<MTPlayer> playersNotConnected() {
    List<MTPlayer> MTPlayers = new ArrayList<>();
    for (MTPlayer MTPlayer : getMtPlayers()) {
      if (!MTPlayer.getVoiceManager().getClient().isConnected()) MTPlayers.add(MTPlayer);
    }
    return MTPlayers;
  }

  public List<ItemStack> getPlayersStackForVoice() {

    List<ItemStack> list = new ArrayList<>();

    getMtPlayers().forEach(mtPlayer -> {

      if (mtPlayer.getVoiceManager() != null) {

        if (mtPlayer.getVoiceManager().getClient().isConnected())
          list.add(new ItemBuilder(Material.PLAYER_HEAD).setPlayerHFromName(mtPlayer.getPlayer().getName()).setName(CustomChatColor.GREEN.getColorWithText(mtPlayer.getPlayer().getName())).setLore(
            "§7",
            mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_LORE_MODIFPLAYER, mtPlayer.getPlayer().getName()),
            "§7",
            mtPlayer.getMsg(LangMsg.GENERAL_INFORMATION, "") + " :",
            mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_LORE_CONNECTIONSTATUS, LangMsg.GENERAL_YES),
            mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_LORE_CLIENTVOLUME, mtPlayer.getVoiceManager().getClient().getVolume() + "%"),
            mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_LORE_MICROSTATUS, (mtPlayer.getVoiceManager().getClient().isMicrophoneActive() ? LangMsg.GENERAL_ENABLED : LangMsg.GENERAL_DISABLED)),
            mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_LORE_FORCEDMUTESTATUS, (mtPlayer.getVoiceManager().isForcedMute() ? LangMsg.GENERAL_ENABLED : LangMsg.GENERAL_DISABLED)))
            .toItemStack()
          );
        else
          list.add(new ItemBuilder(Material.PLAYER_HEAD).setPlayerHFromName(mtPlayer.getPlayer().getName()).setName(CustomChatColor.RED.getColorWithText(mtPlayer.getPlayer().getName())).setLore(
            "§7",
            mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_LORE_MODIFPLAYER, mtPlayer.getPlayer().getName()),
            "§7",
            mtPlayer.getMsg(LangMsg.GENERAL_INFORMATION, "") + " :",
            mtPlayer.getMsg(LangMsg.GUI_VOICE_LISTPLAYER_LORE_CONNECTIONSTATUS, LangMsg.GENERAL_NO))
            .toItemStack()
          );
      }

    });
    return list;
  }

  public List<String> getPlayersNameNotFreeze() {
    List<String> list = new ArrayList<>();

    getMtPlayers().forEach(mtPlayer -> {
      if (mtPlayer.isCanMove()) list.add(mtPlayer.getPlayer().getName());
    });
    return list;
  }

  public List<String> getPlayersNameFreeze() {
    List<String> list = new ArrayList<>();

    getMtPlayers().forEach(mtPlayer -> {
      if (!mtPlayer.isCanMove()) list.add(mtPlayer.getPlayer().getName());
    });
    return list;
  }

}

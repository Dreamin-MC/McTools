package fr.dreamin.mctools.components.players.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.player.ActionPlayerKey;
import fr.dreamin.mctools.api.time.TimerManager;
import fr.dreamin.mctools.components.gui.staff.FreezeGui;
import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import lombok.Setter;

public class StaffPlayerManager {

  @Getter private MTPlayer mtPlayer;
  @Getter @Setter private String sentenceFreeze;

  public StaffPlayerManager(MTPlayer mtPlayer) {
    this.mtPlayer = mtPlayer;
  }

  public void freezePlayer(MTPlayer mtPlayer1, String sentenceFreeze) {

    mtPlayer1.setCanMove(false);

    this.sentenceFreeze = sentenceFreeze;

    ActionPlayerKey action = new ActionPlayerKey(mtPlayer1.getPlayer().getUniqueId(), "freezeStaff");

    McTools.getService(GuiManager.class).open(mtPlayer1.getPlayer(), FreezeGui.class);

    McTools.getService(TimerManager.class).setTimer(action, (a, tick) -> {
      if (tick % 20 == 0) mtPlayer1.getPlayer().sendMessage(McTools.getCodex().getBroadcastprefix()+ getSentenceFreeze());
    });
  }


  public void unFreezePlayer(MTPlayer mtPlayer) {
    ActionPlayerKey action = new ActionPlayerKey(mtPlayer.getPlayer().getUniqueId(), "freezeStaff");
    McTools.getService(TimerManager.class).removeTimer(action);
    mtPlayer.setCanMove(true);
    setSentenceFreeze(null);
  }

}

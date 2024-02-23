package fr.dreamin.dreamintools.api.hud;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.bossBar.BarManager;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.scheduler.BukkitRunnable;

public class HudManager {

  private HudType hudType;
  private DTPlayer dtPlayer;
  private BarManager barManager;
  private BukkitRunnable task;
  private MessageProvider messageProvider;

  public HudManager(HudType hudType, DTPlayer dtPlayer, MessageProvider messageProvider) {
    this.hudType = hudType;
    this.dtPlayer = dtPlayer;
    this.messageProvider = messageProvider;

    if (hudType == HudType.ACTIONBAR) {
      dtPlayer.getPlayer().sendActionBar("§aHello World");
    }
    else if (hudType == HudType.BOSSBAR) {
      barManager = new BarManager(dtPlayer.getPlayer());
      barManager.setBarTitle("§aHello World");
      barManager.updateBar(0F);
      barManager.setColor(BarColor.GREEN);
      barManager.showBar();
    }

  }

  public HudType getHudType() {
    return hudType;
  }

  public DTPlayer getDtPlayer() {
    return dtPlayer;
  }

  public BarManager getBarManager() {
    return barManager;
  }

  public void startHudTask(long delay, long interval) {
    if (task != null && !task.isCancelled()) {
      task.cancel();
    }

    task = new BukkitRunnable() {
      @Override
      public void run() {
        sendHud(messageProvider.getMessage());
      }
    };

    task.runTaskTimer(McTools.getInstance(), delay, interval); // Démarrer immédiatement, répéter à l'intervalle spécifié
  }

  public void cancelHudTask() {
    if (task != null) {
      task.cancel();
      task = null;
    }
  }

  public void setMessageProvider(MessageProvider newMessageProvider) {
    this.messageProvider = newMessageProvider;
  }

  public void sendHud(String message) {
    switch (hudType) {
      case ACTIONBAR:
        dtPlayer.getPlayer().sendActionBar(message);
        break;
      case BOSSBAR:
        barManager.setBarTitle(message);
        break;
      case CHAT:
        dtPlayer.getPlayer().sendMessage(message);
        break;
      case TITLE:
        break;
    }
  }

}

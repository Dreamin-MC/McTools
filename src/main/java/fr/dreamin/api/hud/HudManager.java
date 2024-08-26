package fr.dreamin.api.hud;

import fr.dreamin.mctools.McTools;
import fr.dreamin.api.bossBar.BarManager;
import fr.dreamin.mctools.component.player.MTPlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.scheduler.BukkitRunnable;

public class HudManager {

  private HudType hudType;
  private MTPlayer MTPlayer;
  private BarManager barManager;
  private BukkitRunnable task;
  private MessageProvider messageProvider;

  public HudManager(HudType hudType, MTPlayer MTPlayer, MessageProvider messageProvider) {
    this.hudType = hudType;
    this.MTPlayer = MTPlayer;
    this.messageProvider = messageProvider;

    if (hudType == HudType.ACTIONBAR) MTPlayer.getPlayer().sendActionBar("§aHello World");
    else if (hudType == HudType.BOSSBAR) {
      barManager = new BarManager(MTPlayer.getPlayer());
      barManager.setBarTitle("§aHello World");
      barManager.updateBar(0F);
      barManager.setColor(BarColor.GREEN);
      barManager.showBar();
    }

  }

  public HudType getHudType() {
    return hudType;
  }

  public MTPlayer getDtPlayer() {
    return MTPlayer;
  }

  public BarManager getBarManager() {
    return barManager;
  }

  public void startHudTask(long delay, long interval) {
    if (task != null && !task.isCancelled()) task.cancel();

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
        MTPlayer.getPlayer().sendActionBar(message);
        break;
      case BOSSBAR:
        barManager.setBarTitle(message);
        break;
      case CHAT:
        MTPlayer.getPlayer().sendMessage(message);
        break;
      case TITLE:
        break;
    }
  }

}

package fr.dreamin.mctools.api.bossBar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BarManager {

  private Player player;
  private BossBar customBossBar;

  public BarManager(Player player) {
    this.customBossBar = Bukkit.createBossBar(" ", BarColor.PURPLE, BarStyle.SOLID);
    this.player = player;
  }

  public void showBar() {
    if (!customBossBar.getPlayers().contains(player))
      customBossBar.addPlayer(player);
  }

  public void hideBar() {
    if (customBossBar.getPlayers().contains(player))
      customBossBar.removePlayer(player);
  }

  public boolean isShow() {
    if (customBossBar.getPlayers().contains(player))
      return true;
    return false;
  }

  public void updateBar(float progress) {
    customBossBar.setProgress(progress);
  }

  public void setBarTitle(String title) {
    customBossBar.setTitle(title);
  }

  public void setColor(BarColor color) {
    customBossBar.setColor(color);
  }
}
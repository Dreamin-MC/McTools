package fr.dreamin.mctools.components.players.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.components.players.filter.tick.CustomFilterTick;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PlayerTickManager {

  private final MTPlayer MTPlayer;
  private BukkitTask tickTask;
  private boolean isPaused = false;
  private int actualTick = -1;
  private List<CustomFilterTick> customTicks = new ArrayList<>();

  public PlayerTickManager(MTPlayer MTPlayer) {
    this.MTPlayer = MTPlayer;

    startBukkitTask();
  }

  //------FUNCTION------//

  public void tick() {
    if (this.isPaused) return;

    for (CustomFilterTick customTick : customTicks) {
      customTick.actualTick(MTPlayer, actualTick);
    }

    actualTick++;
  }

  public void startBukkitTask() {
    if (this.tickTask != null) tickTask.cancel();
    this.tickTask = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), this::tick, 1, 1);
  }

  public void stopBukkitTask() {
    if (this.tickTask != null) tickTask.cancel();
  }

  //------GETTER------//
  public MTPlayer getDTPlayer() {
    return MTPlayer;
  }

  public int getActualTick() {
    return actualTick;
  }

  public BukkitTask getTickTask() {
    return tickTask;
  }

  public boolean isPaused() {
    return isPaused;
  }

  public List<CustomFilterTick> getCustomTicks() {
    return customTicks;
  }

  //------SETTER------//
  public void setActualTick(int actualTick) {
    this.actualTick = actualTick;
  }

  public void setPaused(boolean paused) {
    isPaused = paused;
  }

  //------ADDER------//

  public void addFilter(CustomFilterTick customFilterTick) {
    customTicks.add(customFilterTick);
  }

}

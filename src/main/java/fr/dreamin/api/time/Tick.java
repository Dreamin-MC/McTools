package fr.dreamin.api.time;

import fr.dreamin.mctools.McTools;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

@Getter @Setter
public class Tick {

  private int actualTick = -1;
  private BukkitTask task = null;
  private boolean paused = false;

  public Tick() {
    startTick();
  }

  public void tick() {
    if (this.paused) return;

    this.actualTick++;
  }

  public void stopTick() {
    this.actualTick = -1;
    this.task.cancel();
    this.task = null;
  }

  public void startTick() {
    if (this.task != null) stopTick();
    this.task = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), this::tick,1, 1);
  }
}

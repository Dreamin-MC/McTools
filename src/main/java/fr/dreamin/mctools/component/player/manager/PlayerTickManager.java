package fr.dreamin.mctools.component.player.manager;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.component.player.MTPlayer;
import fr.dreamin.mctools.component.player.filter.tick.CustomFilterTick;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PlayerTickManager {

  @Getter private final MTPlayer MTPlayer;
  @Getter @Setter private BukkitTask tickTask;
  @Getter @Setter private boolean isPaused = false;
  @Getter @Setter private int actualTick = -1;
  @Getter private List<CustomFilterTick> customTicks = new ArrayList<>();

  public PlayerTickManager(MTPlayer MTPlayer) {
    this.MTPlayer = MTPlayer;

    startBukkitTask();
  }

  //------FUNCTION------//

  public void tick() {
    if (this.isPaused) return;

    customTicks.forEach(customTick -> customTick.actualTick(actualTick));

    actualTick++;
  }

  public void startBukkitTask() {
    if (this.tickTask != null) tickTask.cancel();
    this.actualTick = -1;
    this.tickTask = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), this::tick, 1, 1);
  }

  public void stopBukkitTask() {
    if (this.tickTask != null) tickTask.cancel();
  }

  //------ADDER------//

  public void addFilter(CustomFilterTick customFilterTick) {
    customTicks.add(customFilterTick);
  }

}

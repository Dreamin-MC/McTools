package fr.dreamin.api.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Task {
  private int delay = 0;
  private int repeat = 0;
  private boolean async = false;
  private final Plugin plugin;
  private Runnable defaultRunnable;
  private BukkitTask task;
  private BukkitRunnable bukkitRunnable;

  public Task(Plugin plugin) {
    this.plugin = plugin;
  }

  public static Task create(Plugin plugin) {
    return new Task(plugin);
  }

  public Task execute(Runnable runnable) {
    this.defaultRunnable = runnable;
    return this;
  }

  public Task execute(BukkitRunnable runnable) {
    this.bukkitRunnable = runnable;
    return this;
  }

  public Task async() {
    this.async = true;
    return this;
  }

  public Task delay(int delay) {
    this.delay = delay;
    return this;
  }

  public Task repeat(int repeat) {
    this.repeat = repeat;
    return this;
  }

  public Task cancel() {
    this.task.cancel();
    return this;
  }

  public void cancelAndRunImmediately() {
    this.task.cancel();
    if (this.bukkitRunnable != null) this.bukkitRunnable.run();

    if (this.defaultRunnable != null) this.defaultRunnable.run();
  }

  public BukkitTask run() {
    if (this.bukkitRunnable != null) {
      if (this.async) {
        if (this.repeat > 0)
          this.task = this.bukkitRunnable.runTaskTimerAsynchronously(this.plugin, (long) this.delay, (long) this.repeat);
        else if (this.delay > 0)
          this.task = this.bukkitRunnable.runTaskLaterAsynchronously(this.plugin, (long) this.delay);
        else this.task = this.bukkitRunnable.runTaskAsynchronously(this.plugin);
      }
      else if (this.repeat > 0) this.task = this.bukkitRunnable.runTaskTimer(this.plugin, (long)this.delay, (long)this.repeat);
      else if (this.delay > 0) this.task = this.bukkitRunnable.runTaskLater(this.plugin, (long)this.delay);
      else this.task = this.bukkitRunnable.runTask(this.plugin);

    } else {
      if (this.defaultRunnable == null) throw new IllegalStateException("Both runnable types are null!");

      BukkitScheduler scheduler = Bukkit.getScheduler();
      if (this.async) {
        if (this.repeat > 0)
          this.task = scheduler.runTaskTimerAsynchronously(this.plugin, this.defaultRunnable, (long) this.delay, (long) this.repeat);
        else if (this.delay > 0)
          this.task = scheduler.runTaskLaterAsynchronously(this.plugin, this.defaultRunnable, (long) this.delay);
        else this.task = scheduler.runTaskAsynchronously(this.plugin, this.defaultRunnable);
      }
      else if (this.repeat > 0) this.task = scheduler.runTaskTimer(this.plugin, this.defaultRunnable, (long)this.delay, (long)this.repeat);
      else if (this.delay > 0) this.task = scheduler.runTaskLater(this.plugin, this.defaultRunnable, (long)this.delay);
      else this.task = scheduler.runTask(this.plugin, this.defaultRunnable);

    }
    return this.task;
  }
}


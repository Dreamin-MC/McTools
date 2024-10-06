package fr.dreamin.api.time;

import fr.dreamin.mctools.McTools;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Tick class manages a custom tick counter for tasks that need to run on every server tick.
 * It supports starting, stopping, pausing, and resetting ticks, with listeners for pause and resume events.
 */
@Getter @Setter
public class Tick {

  private int startDefaultTick = -1 /* Default starting value for the tick counter. */, actualTick = startDefaultTick /* Current value of the tick counter. */, i = 1, i1 = 1;

  private BukkitTask task = null;        // BukkitTask that manages the scheduled ticking.
  private boolean paused = false;        // Indicates if the ticking process is paused.

  /**
   * -- SETTER --
   *  Sets a listener that triggers when the ticking process is ticking.
   *
   */
  private Runnable onTickListener = null;

  /**
   * -- SETTER --
   *  Sets a listener that triggers when the ticking process is paused.
   *
   */
  private Runnable onPauseListener = null; // Optional listener that triggers when the ticking is paused.

  /**
   * -- SETTER --
   *  Sets a listener that triggers when the ticking process is resumed.
   *
   */
  private Runnable onResumeListener = null;   // Optional listener that triggers when the ticking is resumed.

  /**
   * -- SETTER --
   *  Sets a listener that triggers when the ticking is starting.
   *
   */
  private Runnable onStartListener = null; // Optional listener that triggers when


  public Tick() {
  }

  public Tick(Tick tick) {
    this.startDefaultTick = tick.getStartDefaultTick();
    this.actualTick = tick.getActualTick();
    this.task = tick.getTask();
    this.paused = tick.isPaused();
    this.i = tick.getI();
    this.i1 = tick.getI1();
    this.onTickListener = tick.getOnTickListener();
    this.onPauseListener = tick.getOnPauseListener();
    this.onResumeListener = tick.getOnResumeListener();
    this.onStartListener = tick.getOnStartListener();

    if (!this.paused) startTick();
  }

  public Tick(int startDefaultTick, int actualTick, BukkitTask task, boolean paused, int i, int i1, Runnable onTickListener, Runnable onPauseListener, Runnable onResumeListener, Runnable onStartListener) {
    this.startDefaultTick = startDefaultTick;
    this.actualTick = actualTick;
    this.task = task;
    this.paused = paused;
    this.i = i;
    this.i1 = i1;
    this.onTickListener = onTickListener;
    this.onPauseListener = onPauseListener;
    this.onResumeListener = onResumeListener;
    this.onStartListener = onStartListener;

    if (!this.paused) startTick();
  }

  /**
   * Increments the tick counter by 1 for each server tick, unless the process is paused.
   * This method is executed at every Minecraft tick (approximately every 50 ms).
   */
  public void tick() {
    if (this.paused) return;  // Do nothing if the ticking process is paused.
    this.actualTick++;  // Increment the tick counter by 1.

    if (this.onTickListener != null) this.onTickListener.run();
  }

  public void stop() {
  }

  public void start() {
  }

  public void pause() {
  }

  /**
   * Starts the ticking process by scheduling a task that runs every Minecraft tick.
   * If the tick process is already running, it will first stop the existing task before starting a new one.
   */
  public void startTick() {
    if (McTools.getInstance() == null) return;

    if (this.task != null) stopTick();
    // Stop the current task if one is already running.
    this.task = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), () -> {
      if (!isPaused()) this.tick();
    }, i, i1);  // Schedule a new tick task.

    if (this.onStartListener != null) this.onStartListener.run();

    start();
  }

  /**
   * Stops the ticking process and resets the tick counter to the default value.
   * Cancels the current task if it is running and clears the task reference.
   */
  public void stopTick() {
    if (this.task != null) {
      this.task.cancel();    // Cancel the scheduled task.
      this.task = null;      // Clear the reference to the task.
      stop();
    }
    this.actualTick = startDefaultTick;  // Reset the tick counter to its default value.
  }

  /**
   * Pauses the ticking process, preventing the tick counter from incrementing.
   * If a pause listener is defined, it is triggered.
   */
  public void pauseTick() {
    if (!paused) {
      this.paused = true;  // Set the paused flag to true.
      if (onPauseListener != null) onPauseListener.run(); // Trigger the pause listener if defined.
      pause();
    }
  }

  /**
   * Resumes the ticking process if it was previously paused.
   * If a resume listener is defined, it is triggered.
   */
  public void resumeTick() {
    if (paused) {
      this.paused = false;  // Set the paused flag to false, resuming the tick counter.
      if (onResumeListener != null) onResumeListener.run();  // Trigger the resume listener if defined.

    }
  }

  /**
   * Resets the current tick count to the default value while keeping the ticking process active.
   */
  public void resetTick() {
    this.actualTick = startDefaultTick;  // Reset the tick counter to its default value.
  }

  /**
   * Checks if the ticking process is currently running.
   * @return True if the task is running and not paused, false otherwise.
   */
  public boolean isRunning() {
    return this.task != null && !paused;  // Return true if the task is running and not paused.
  }

}
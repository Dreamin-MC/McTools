package fr.dreamin.api.time;

import fr.dreamin.mctools.McTools;
import fr.dreamin.api.listener.time.TimeEventAction;
import fr.dreamin.api.listener.time.timer.OnTimerEvent;
import fr.dreamin.api.player.ActionPlayerKey;
import fr.dreamin.api.time.event.TimerCallback;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {
  private final Map<ActionPlayerKey, Integer> actionTimerTasks = new HashMap<>();
  private final Map<String, Integer> stringTimerTasks = new HashMap<>();
  private final Map<Object, TimerCallback> timerCallbacks = new HashMap<>();
  private final Map<Object, Long> timerTicks = new HashMap<>();
  private final Map<Object, Boolean> timerPaused = new HashMap<>();

  // Définit un timer avec un callback à chaque tick
  public void setTimer(ActionPlayerKey key, TimerCallback callback) {
    setTimerInternal(key, callback);
  }

  public void setTimer(String key, TimerCallback callback) {
    setTimerInternal(key, callback);
  }

  private void setTimerInternal(Object key, TimerCallback callback) {

    // Supprime le timer actuel s'il existe
    if (isTimerInternal(key)) removeTimerInternal(key);

    // Initialiser le tick pour ce timer
    timerTicks.put(key, 0L);
    timerPaused.put(key, false);

    // Planifie une tâche qui s'exécute chaque tick
    int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(McTools.getInstance(), () -> {
      // Vérifier si le timer est en pause
      if (!timerPaused.get(key)) {
        // Incrémente le tick pour ce timer
        long currentTick = timerTicks.get(key);
        currentTick++;

        // Mettre à jour le tick pour ce timer
        timerTicks.put(key, currentTick);
      }

      // Appeler le callback
      if (callback != null) callback.onTick(key, timerTicks.get(key));

    }, 0L, 1L); // Exécute la tâche à chaque tick

    // Stocke le taskId et le callback
    timerCallbacks.put(key, callback);
    if (key instanceof ActionPlayerKey) actionTimerTasks.put((ActionPlayerKey) key, taskId);
    else if (key instanceof String) stringTimerTasks.put((String) key, taskId);

    OnTimerEvent.callEvent(key, TimeEventAction.CREATE);

  }

  // Vérifie si un timer est actif
  public boolean isTimerActive(ActionPlayerKey key) {
    return actionTimerTasks.containsKey(key);
  }

  public boolean isTimerActive(String key) {
    return stringTimerTasks.containsKey(key);
  }

  // Pause a timer
  public void pauseTimer(ActionPlayerKey key) {
    pauseTimerInternal(key);
  }

  public void pauseTimer(String key) {
    pauseTimerInternal(key);
  }

  private void pauseTimerInternal(Object key) {
    if (isTimerInternal(key)) {
      timerPaused.put(key, true);
      OnTimerEvent.callEvent(key, TimeEventAction.PAUSE);
    }
  }

  // Resume a timer
  public void resumeTimer(ActionPlayerKey key) {
    resumeTimerInternal(key);
  }

  public void resumeTimer(String key) {
    resumeTimerInternal(key);
  }

  private void resumeTimerInternal(Object key) {
    if (isTimerInternal(key)) {
      timerPaused.put(key, false);
      OnTimerEvent.callEvent(key, TimeEventAction.RESUME);
    }
  }

  // Supprime un timer actif
  public void removeTimer(ActionPlayerKey key) {
    removeTimerInternal(key);
  }

  public void removeTimer(String key) {
    removeTimerInternal(key);
  }

  private void removeTimerInternal(Object key) {
    Integer taskId;
    if (key instanceof ActionPlayerKey) taskId = actionTimerTasks.remove(key);
    else if (key instanceof String) taskId = stringTimerTasks.remove(key);
    else taskId = null;

    if (taskId != null) {
      Bukkit.getScheduler().cancelTask(taskId);
      timerCallbacks.remove(key);
      timerTicks.remove(key);
      timerPaused.remove(key);
      OnTimerEvent.callEvent(key, TimeEventAction.REMOVE);
    }
  }

  // Vérifie si un timer est actif pour une clé d'action de joueur
  public boolean isTimer(ActionPlayerKey key) {
    return isTimerInternal(key);
  }

  // Vérifie si un timer est actif pour une clé de chaîne
  public boolean isTimer(String key) {
    return isTimerInternal(key);
  }

  // Méthode interne pour vérifier si un timer est actif pour une clé donnée
  private boolean isTimerInternal(Object key) {
    if (key instanceof ActionPlayerKey) return actionTimerTasks.containsKey(key);
    else if (key instanceof String) return stringTimerTasks.containsKey(key);
    else return false;
  }
}
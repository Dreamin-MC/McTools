package fr.dreamin.mctools.api.time;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.player.ActionPlayerKey;
import fr.dreamin.mctools.api.service.Service;
import fr.dreamin.mctools.api.time.event.CooldownCallback;
import fr.dreamin.mctools.api.time.event.TimerCallback;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerManager extends Service {
  private final Map<ActionPlayerKey, Integer> actionTimerTasks = new HashMap<>();
  private final Map<String, Integer> stringTimerTasks = new HashMap<>();
  private final Map<Object, TimerCallback> timerCallbacks = new HashMap<>();
  private final Map<Object, Long> timerTicks = new HashMap<>();

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

    // Planifie une tâche qui s'exécute chaque tick
    int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(McTools.getInstance(), () -> {
      // Incrémente le tick pour ce timer
      long currentTick = timerTicks.get(key);
      currentTick++;

      // Mettre à jour le tick pour ce timer
      timerTicks.put(key, currentTick);

      // Appeler le callback
      if (callback != null) callback.onTick(key, currentTick);

    }, 0L, 1L); // Exécute la tâche à chaque tick

    // Stocke le taskId et le callback
    timerCallbacks.put(key, callback);
    if (key instanceof ActionPlayerKey) actionTimerTasks.put((ActionPlayerKey) key, taskId);
    else if (key instanceof String) stringTimerTasks.put((String) key, taskId);

  }

  // Vérifie si un timer est actif
  public boolean isTimerActive(ActionPlayerKey key) {
    return actionTimerTasks.containsKey(key);
  }

  public boolean isTimerActive(String key) {
    return stringTimerTasks.containsKey(key);
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
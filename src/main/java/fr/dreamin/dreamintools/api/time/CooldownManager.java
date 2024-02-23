package fr.dreamin.dreamintools.api.time;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.player.ActionPlayerKey;
import fr.dreamin.dreamintools.api.time.event.CooldownCallback;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {

  // Map pour stocker les temps de fin de cooldown pour chaque clé joueur-action
  private final Map<ActionPlayerKey, Long> actionCooldowns = new HashMap<>();
  private final Map<String, Long> keyCooldowns = new HashMap<>();
  private final Map<Object, Integer> cooldownTasks = new HashMap<>();
  private final Map<Object, CooldownCallback> cooldownCallbacks = new HashMap<>();


  // Définit un cooldown pour un joueur et une action
  public void setCooldown(ActionPlayerKey key, int cooldownTick, CooldownCallback callback) {
    setCooldownInternal(key, cooldownTick, callback);
  }
  public void setCooldown(String key, int cooldownTick, CooldownCallback callback) {
    setCooldownInternal(key, cooldownTick, callback);
  }
  private void setCooldownInternal(Object key, int cooldownTick, CooldownCallback callback) {
    // Convertir les ticks en millisecondes pour endTime
    long endTime = System.currentTimeMillis() + (cooldownTick * 50L);

    if (isCooldownInternal(key)) {
      removeCooldownInternal(key);
    }

    if (key instanceof ActionPlayerKey) {
      actionCooldowns.put((ActionPlayerKey) key, endTime);
    } else if (key instanceof String) {
      keyCooldowns.put((String) key, endTime);
    }

    int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(McTools.getInstance(), () -> {
      removeCooldownInternal(key);

      if (callback != null) {
        callback.onCooldownEnd(key);
      }
      removeCooldownInternal(key);

    }, cooldownTick);

    cooldownTasks.put(key, taskId);
    cooldownCallbacks.put(key, callback);
  }


  // Vérifie si un cooldown est actif pour un joueur et une action
  public boolean isCooldown(String key) {
    return isCooldownInternal(key);
  }
  public boolean isCooldown(ActionPlayerKey actionKey) {
    return isCooldownInternal(actionKey);
  }
  private boolean isCooldownInternal(Object key) {
    Long endTime;
    if (key instanceof ActionPlayerKey) {
      endTime = actionCooldowns.get(key);
    } else {
      endTime = keyCooldowns.get(key);
    }
    return endTime != null && System.currentTimeMillis() < endTime;
  }


  // Récupère le temps restant sur le cooldown sous forme de chaîne de caractères
  public String getRemainingTime(String key) {
    return getRemainingTimeInternal(key);
  }
  public String getRemainingTime(ActionPlayerKey actionKey) {
    return getRemainingTimeInternal(actionKey);
  }
  private String getRemainingTimeInternal(Object key) {
    Long endTime;
    if (key instanceof ActionPlayerKey) {
      endTime = actionCooldowns.get(key);
    } else {
      endTime = keyCooldowns.get(key);
    }

    if (endTime == null || System.currentTimeMillis() >= endTime) {
      return "Pas de cooldown";
    }

    long remainingTime = (endTime - System.currentTimeMillis()) / 1000;
    long minutes = remainingTime / 60;
    long seconds = remainingTime % 60;

    if (minutes > 0 && seconds > 0) {
      return String.format("Temps restants %d minute%s et %d seconde%s",
        minutes, minutes > 1 ? "s" : "",
        seconds, seconds > 1 ? "s" : "");
    } else if (minutes > 0) {
      return String.format("Temps restants %d minute%s", minutes, minutes > 1 ? "s" : "");
    } else {
      return String.format("Temps restant %d seconde%s", seconds, seconds > 1 ? "s" : "");
    }
  }


  // Renvoie le temps restant en secondes
  public int getRemainingSeconds(ActionPlayerKey actionKey) {
    return getRemainingSecondsInternal(actionKey);
  }
  public int getRemainingSeconds(String key) {
    return getRemainingSecondsInternal(key);
  }
  private int getRemainingSecondsInternal(Object key) {
    Long endTime;
    if (key instanceof ActionPlayerKey) {
      endTime = actionCooldowns.get(key);
    } else {
      endTime = keyCooldowns.get(key);
    }
    if (endTime == null || System.currentTimeMillis() >= endTime) {
      return 0; // Aucun cooldown ou cooldown terminé
    }

    long remainingTimeMillis = endTime - System.currentTimeMillis();
    return (int) (remainingTimeMillis / 1000); // Convertit le temps restant en secondes
  }


  // Supprime un cooldown
  public void removeCooldown(ActionPlayerKey actionKey) {
    removeCooldownInternal(actionKey);
  }
  public void removeCooldown(String key) {
    removeCooldownInternal(key);
  }
  private void removeCooldownInternal(Object key) {
    if (key instanceof ActionPlayerKey) {
      actionCooldowns.remove(key);
    } else {
      keyCooldowns.remove(key);
    }
    if (cooldownTasks.containsKey(key)) {
      Bukkit.getScheduler().cancelTask(cooldownTasks.get(key));
      cooldownTasks.remove(key);

    }
    cooldownCallbacks.remove(key);
  }

}


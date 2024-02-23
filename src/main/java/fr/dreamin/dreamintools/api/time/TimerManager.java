package fr.dreamin.dreamintools.api.time;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerManager {

  private static class ActionKey {
    private final UUID playerId;
    private final String action;

    public ActionKey(UUID playerId, String action) {
      this.playerId = playerId;
      this.action = action;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ActionKey that = (ActionKey) o;
      return playerId.equals(that.playerId) && action.equals(that.action);
    }

    @Override
    public int hashCode() {
      return 31 * playerId.hashCode() + action.hashCode();
    }
  }

  private final Map<ActionKey, Long> startTimes = new HashMap<>();

  public void startTimer(UUID playerId, String action) {
    long startTime = System.currentTimeMillis();
    startTimes.put(new ActionKey(playerId, action), startTime);
  }

  public String getElapsedTime(UUID playerId, String action) {
    Long startTime = startTimes.get(new ActionKey(playerId, action));
    if (startTime == null) {
      return "Timer non démarré";
    }

    long elapsedTimeMillis = System.currentTimeMillis() - startTime;
    long minutes = (elapsedTimeMillis / 1000) / 60;
    long seconds = (elapsedTimeMillis / 1000) % 60;

    return String.format("%d minute%s et %d seconde%s",
      minutes, minutes > 1 ? "s" : "",
      seconds, seconds > 1 ? "s" : "");
  }


  public int getElapsedSeconds(UUID playerId, String action) {
    Long startTime = startTimes.get(new ActionKey(playerId, action));
    if (startTime == null) {
      return 0; // Le timer n'a pas été démarré
    }

    long elapsedTimeMillis = System.currentTimeMillis() - startTime;
    return (int) (elapsedTimeMillis / 1000); // Convertit le temps écoulé en secondes
  }
  public void removeTimer(UUID playerId, String action) {
    startTimes.remove(new ActionKey(playerId, action));
  }
}
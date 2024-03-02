package fr.dreamin.mctools.api.time.event;

public interface TimerCallback {
  void onTick(Object key, long currentTick);
}

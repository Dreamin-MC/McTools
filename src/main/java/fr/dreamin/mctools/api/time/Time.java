package fr.dreamin.mctools.api.time;

import java.util.HashMap;

public class Time {
  private static final HashMap<String, Long> timings = new HashMap();
  public static final int HOURS_PER_DAY = 24;
  public static final int MINUTES_PER_HOUR = 60;
  public static final int SECONDS_PER_MINUTE = 60;
  public static final int SECONDS_PER_HOUR = 3600;
  public static final int SECONDS_PER_DAY = 86400;

  public Time() {
  }

  public static long toMillis(long secs) {
    return secs * 1000L;
  }

  public static void timerStart(String key) {
    timings.put(key, System.currentTimeMillis());
  }

  public static long timerEnd(String key) {
    long startTime = (Long)timings.get(key);
    timings.remove(key);
    return System.currentTimeMillis() - startTime;
  }
}

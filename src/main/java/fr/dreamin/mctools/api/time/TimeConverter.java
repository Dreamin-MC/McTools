package fr.dreamin.mctools.api.time;

import java.util.Date;

public class TimeConverter {

  public static int getTimeSeconds(long start, int cooldown) {
    long timeBetween = getTime(start, new Date().getTime());
    int seconds = (int) (timeBetween / 1000);
    return cooldown - seconds;
  }

  public static long getTime(long time1, long time2) {
    return java.lang.Math.abs(time1 - time2);
  }
  public static String getTimeSecMin(long start, int cooldown) {
    long timeBetween = getTime(start, new Date().getTime());
    int minutes = (int) (timeBetween / 1000 / 60);
    int seconds = (int) (timeBetween / 1000) % 60;

    // On vérifie si le temps restant est supérieur à 60 secondes
    if (cooldown - seconds > 60) {
      // Si oui, on déduit le temps en minutes et en secondes du temps restant
      minutes = ((cooldown - seconds) / 60) - minutes;
      seconds = (cooldown - seconds) % 60;

      if (seconds < 10) return minutes + " minute"+ ((minutes > 1) ? "s" : "") + " et 0" + seconds + " seconde" + ((seconds > 1) ? "s" : "");
      else return minutes + " minute"+ ((minutes > 1) ? "s" : "") + " et " + seconds + " seconde" + ((seconds > 1) ? "s" : "");
    } else {
      // Si non, on déduit le temps restant en secondes seulement
      seconds = (cooldown - seconds);
      return seconds + " seconde" + ((seconds > 1) ? "s" : "");
    }
  }

  public static String getTimeSecMin(int seconds) {
    if (seconds > 60) {
      int minutes = seconds / 60;
      seconds = seconds % 60;
      if (seconds < 10) return minutes + ":0" + seconds + "min";
      else return minutes + ":" + seconds + "min";
    }
    else return seconds + "sec";
  }

  public static String getTimeSecMinHour(int ticks) {
    int seconds = getTicksToSeconds(ticks);
    if (seconds > 60) {
      int minutes = seconds / 60;
      seconds = seconds % 60;
      if (minutes > 60) {
        int hours = minutes / 60;
        minutes = minutes % 60;
        seconds = seconds % 60;

        return hours + "h" + (minutes > 9 ? minutes + "m" :  "0" + minutes + "m") + (seconds > 9 ? seconds + "s" :  "0" + seconds + "s");
      }
      else return minutes + "m" + (seconds > 9 ? seconds + "s" :  "0" + seconds + "s");
    }
    else return seconds + "s";
  }

  public static int getTimeSeconds(long start) {
    long timeBetween = getTime(start, new Date().getTime());
    int seconds = (int) (timeBetween / 1000);
    return seconds;
  }

  public static int getTicksToSeconds(long ticks) {
    int seconds = (int) java.lang.Math.round((double) ticks / 20);
    return seconds;
  }

  public static int getSecondsToTicks(long seconds) {
    int ticks = (int) java.lang.Math.round((double) seconds * 20);
    return ticks;
  }
}

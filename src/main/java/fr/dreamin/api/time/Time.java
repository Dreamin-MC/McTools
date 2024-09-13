package fr.dreamin.api.time;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Time implements Comparable<Time> {

  private int nanos, second, minute, hour;

  public Time(int hour, int minute, int second, int nanos) {
    this.hour = hour;
    this.minute = minute;
    this.second = second;
    this.nanos = nanos;
    normalizeTime(); // Ensure time is normalized upon creation
  }

  public Time(int hour, int minute, int second) {
    this(hour, minute, second, 0);
  }

  // Normalize the time values to avoid overflows
  private void normalizeTime() {
    if (nanos >= 1000) {
      second += nanos / 1000;
      nanos %= 1000;
    }
    if (second >= 60) {
      minute += second / 60;
      second %= 60;
    }
    if (minute >= 60) {
      hour += minute / 60;
      minute %= 60;
    }
    hour %= 24; // Keep hours within 24-hour range
  }

  // Change return type to long to handle larger values
  public long toNanos() {
    return (long) hour * 3600000000L + (long) minute * 60000000L + (long) second * 1000000L + nanos;
  }

  public float toSeconds() {
    return hour * 3600 + minute * 60 + second + nanos / 1000f;
  }

  public float toMinutes() {
    return hour * 60 + minute + second / 60f + nanos / 60000f;
  }

  public float toHours() {
    return hour + minute / 60f + second / 3600f + nanos / 3600000f;
  }

  public boolean isBefore(Time other) {
    return this.toNanos() < other.toNanos();
  }

  public Time between(Time other) {
    long diffNanos = other.toNanos() - this.toNanos();
    return new Time(0, 0, 0, (int) diffNanos).normalizeReturn();
  }

  public Time plus(Time other) {
    long totalNanos = this.toNanos() + other.toNanos();
    this.hour = (int) (totalNanos / 3600000000L);
    totalNanos %= 3600000000L;
    this.minute = (int) (totalNanos / 60000000L);
    totalNanos %= 60000000L;
    this.second = (int) (totalNanos / 1000000L);
    this.nanos = (int) (totalNanos % 1000000L);

    normalizeTime(); // Assurez-vous que le temps est normalisé
    return this;
  }

  public void add(long nanosToAdd) {
    long totalNanos = this.toNanos() + nanosToAdd;
    this.hour = (int) (totalNanos / 3600000000L);
    totalNanos %= 3600000000L;
    this.minute = (int) (totalNanos / 60000000L);
    totalNanos %= 60000000L;
    this.second = (int) (totalNanos / 1000000L);
    this.nanos = (int) (totalNanos % 1000000L);
    normalizeTime();
  }

  // Returns a normalized CycleTime object
  private Time normalizeReturn() {
    normalizeTime();
    return this;
  }

  @Override
  public String toString() {
    return String.format("%02dh%02dm", hour, minute);
  }

  @Override
  public int compareTo(Time other) {
    return Long.compare(this.toNanos(), other.toNanos());
  }
}

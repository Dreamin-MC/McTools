package fr.dreamin.dreamintools.api.player;

public enum SoundType {

  ACTION("action"),
  AMBIENT("ambient"),
  EVENT("event");

  private final String soundType;

  SoundType(String soundType) {
    this.soundType = soundType;
  }

  public String getSoundType() {
    return soundType;
  }

}

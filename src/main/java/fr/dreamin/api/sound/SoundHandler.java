package fr.dreamin.api.sound;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.List;

public class SoundHandler {

  @Getter private final String label;
  @Getter @Setter private Float volume;
  @Getter @Setter private Float pitch;
  @Getter @Setter private SoundCategory category;

  public SoundHandler(String soundName, SoundCategory category,  float volume, float pitch) {
    this.label = soundName;
    this.volume = volume;
    this.pitch = pitch;
    this.category = category;
  }

  public SoundHandler(SoundHandler soundHandler) {
    this.label = soundHandler.getLabel();
    this.volume = soundHandler.getVolume();
    this.pitch = soundHandler.getPitch();
    this.category = soundHandler.getCategory();
  }

  public SoundHandler(Sound sound, SoundCategory category, float volume, float pitch) {
    this.label = sound.name();
    this.volume = volume;
    this.pitch = pitch;
    this.category = category;
  }

  public void play(Player player, Location location) {
    if (this.label == null) return;
    try {
      Sound sound = Sound.valueOf(this.label.toUpperCase());
      if (location != null) {if (player.getWorld().equals(location.getWorld())) player.playSound(location, sound, this.category, this.volume, this.pitch);}
      else player.playSound(player, sound, this.category, this.volume, this.pitch);
    } catch (IllegalArgumentException e) {
      if (location != null) {if (player.getWorld().equals(location.getWorld())) player.playSound(location, this.label, this.category, this.volume, this.pitch);}
      else player.playSound(player, this.label, this.category, this.volume, this.pitch);
    }
  }

  public void play(List<Player> playerList, Location location) {
    if (this.label == null) return;
    try {
      Sound sound = Sound.valueOf(this.label.toUpperCase());
      if (location != null) playerList.forEach(player -> {
        if (player.getWorld().equals(location.getWorld())) player.playSound(location, sound, this.category, this.volume, this.pitch);});
      else playerList.forEach(player -> {player.playSound(player, sound, this.category, this.volume, this.pitch);});
    } catch (IllegalArgumentException e) {
      if (location != null) playerList.forEach(player -> {
        if (player.getWorld().equals(location.getWorld())) player.playSound(location, this.label, this.category, this.volume, this.pitch);});
      else playerList.forEach(player -> {player.playSound(player, this.label, this.category, this.volume, this.pitch);});
    }
  }

  public void stop(Player player) {
    if (this.label == null) return;
    try {
      Sound sound = Sound.valueOf(this.label.toUpperCase());
      player.stopSound(sound);
    } catch (IllegalArgumentException e) {
      player.stopSound(this.label);
    }
  }

  public void stop(List<Player> playerList) {
    if (this.label == null) return;
    try {
      Sound sound = Sound.valueOf(this.label.toUpperCase());
      playerList.forEach(player -> {player.stopSound(sound);});
    } catch (IllegalArgumentException e) {
      playerList.forEach(player -> {player.stopSound(this.label);});
    }
  }
}

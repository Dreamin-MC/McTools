package fr.dreamin.api.sound;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Handles playing and stopping of sounds for players with specified volume, pitch, and category.
 */
@Getter @Setter
public class SoundHandler {

  private final String label; // Label of the sound (custom or Bukkit Sound)
  private Float volume;
  private Float pitch;
  private SoundCategory category;

  // Constructors

  public SoundHandler(final String soundName, final SoundCategory category, final float volume, final float pitch) {
    this.label = soundName;
    this.volume = volume;
    this.pitch = pitch;
    this.category = category;
  }

  public SoundHandler(final SoundHandler soundHandler) {
    this(soundHandler.getLabel(), soundHandler.getCategory(), soundHandler.getVolume(), soundHandler.getPitch());
  }

  public SoundHandler(final Sound sound, final SoundCategory category, final float volume, final float pitch) {
    this(sound.name(), category, volume, pitch);
  }

  // Sound Playing

  public void play(final Player player, final Location location, final double distance) {
    if (label == null) return;

    Sound sound = getSoundFromLabel();

    if (sound != null) playSound(player, sound, location, distance);
    else playCustomSound(player, location, distance);
  }

  public void play(final Player player, final Location location) {
    play(player, location, -1);
  }

  public void play(final List<Player> playerList, final Location location, final double distance) {
    playerList.forEach(player -> play(player, location, distance));
  }

  public void play(final List<Player> playerList, final Location location) {
    play(playerList, location, -1);
  }

  // Sound Stopping

  public void stop(final Player player) {
    if (label == null) return;

    Sound sound = getSoundFromLabel();

    if (sound != null) player.stopSound(sound);
    else player.stopSound(label);
  }

  public void stop(final List<Player> playerList) {
    playerList.forEach(this::stop);
  }

  // Private Helper Methods

  private Sound getSoundFromLabel() {
    try {
      return Sound.valueOf(label.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  private void playSound(Player player, Sound sound, Location location, double distance) {
    if (location != null) {
      if (isWithinDistance(player, location, distance)) player.playSound(location, sound, category, volume, pitch);
    } else {
      player.playSound(player.getLocation(), sound, category, volume, pitch);
    }
  }

  private void playCustomSound(Player player, Location location, double distance) {
    if (location != null) {
      if (isWithinDistance(player, location, distance)) player.playSound(location, label, category, volume, pitch);
    } else {
      player.playSound(player.getLocation(), label, category, volume, pitch);
    }
  }

  private boolean isWithinDistance(Player player, Location location, double distance) {
    return player.getWorld().equals(location.getWorld()) && (distance < 0 || player.getLocation().distance(location) <= distance);
  }
}

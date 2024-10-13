package fr.dreamin.api.sound;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Handles the playing and stopping of sounds for players with specified volume, pitch, and category.
 */
@Getter @Setter
public class SoundHandler {

  private final String label; // The label of the sound (either custom or a Bukkit Sound).
  private Float volume;
  private Float pitch;
  private SoundCategory category;

  //-----------------CONSTRUCTORS-----------------

  /**
   * Constructs a SoundHandler with the specified sound name, category, volume, and pitch.
   *
   * @param soundName The name of the sound as a string.
   * @param category The sound category (e.g., MUSIC, WEATHER, etc.).
   * @param volume The volume of the sound.
   * @param pitch The pitch of the sound.
   */
  public SoundHandler(final String soundName, final SoundCategory category, final float volume, final float pitch) {
    this.label = soundName;
    this.volume = volume;
    this.pitch = pitch;
    this.category = category;
  }

  /**
   * Constructs a SoundHandler by copying another SoundHandler instance.
   *
   * @param soundHandler The SoundHandler to copy.
   */
  public SoundHandler(final SoundHandler soundHandler) {
    this(soundHandler.getLabel(), soundHandler.getCategory(), soundHandler.getVolume(), soundHandler.getPitch());
  }

  /**
   * Constructs a SoundHandler using a Bukkit Sound enum.
   *
   * @param sound The Bukkit Sound.
   * @param category The sound category.
   * @param volume The volume of the sound.
   * @param pitch The pitch of the sound.
   */
  public SoundHandler(final Sound sound, final SoundCategory category, final float volume, final float pitch) {
    this.label = sound.name();
    this.volume = volume;
    this.pitch = pitch;
    this.category = category;
  }

  //-----------------SOUND PLAYING-----------------

  /**
   * Plays the sound for a single player at the specified location.
   *
   * @param player The player to play the sound for.
   * @param location The location where the sound should be played (optional).
   */
  public void play(final Player player, final Location location, final double distance) {
    if (this.label == null) return;

    try {
      Sound sound = Sound.valueOf(this.label.toUpperCase());
      if (location != null && player.getWorld().equals(location.getWorld()) && (distance < 0 || player.getLocation().distance(location) <= distance)) {
        player.playSound(location, sound, this.category, this.volume, this.pitch);
      } else {
        player.playSound(player, sound, this.category, this.volume, this.pitch);
      }
    } catch (IllegalArgumentException e) {
      playCustomSound(player, location);
    }
  }
  public void play(final Player player, final Location location) {
    play(player, location, -1);
  }

  /**
   * Plays the sound for a list of players at the specified location.
   *
   * @param playerList The list of players to play the sound for.
   * @param location The location where the sound should be played (optional).
   */
  public void play(final List<Player> playerList, final Location location, final double distance) {
    if (this.label == null) return;
    playerList.forEach(player -> this.play(player, location, distance));
  }
  public void play(final List<Player> playerList, final Location location) {
    play(playerList, location);
  }

  //-----------------SOUND STOPPING-----------------

  /**
   * Stops the sound for a single player.
   *
   * @param player The player to stop the sound for.
   */
  public void stop(final Player player) {
    if (this.label == null) return;

    try {
      Sound sound = Sound.valueOf(this.label.toUpperCase());
      player.stopSound(sound);
    } catch (IllegalArgumentException e) {
      player.stopSound(this.label);
    }
  }

  /**
   * Stops the sound for a list of players.
   *
   * @param playerList The list of players to stop the sound for.
   */
  public void stop(final List<Player> playerList) {
    if (this.label == null) return;

    try {
      Sound sound = Sound.valueOf(this.label.toUpperCase());
      playerList.forEach(player -> player.stopSound(sound));
    } catch (IllegalArgumentException e) {
      playerList.forEach(player -> player.stopSound(this.label));
    }
  }

  //-----------------PRIVATE HELPER METHODS-----------------

  /**
   * Plays a custom sound by label for a player at a specific location.
   *
   * @param player The player to play the sound for.
   * @param location The location to play the sound (optional).
   */
  private void playCustomSound(final Player player, final Location location) {
    if (location != null && player.getWorld().equals(location.getWorld())) {
      player.playSound(location, this.label, this.category, this.volume, this.pitch);
    } else {
      player.playSound(player, this.label, this.category, this.volume, this.pitch);
    }
  }
}
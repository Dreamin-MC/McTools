package fr.dreamin.api.sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.List;

public class SoundManager {

  public static void playSoundToAll(List<Player> playerList, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch) {

    if (location != null) {
      for (Player player: playerList) {
        if (player.getWorld().equals(location.getWorld()))
          if (player.getLocation().distance(location) <= 16) player.playSound(location, sound, soundCategory, volume, pitch);
      }
    }
    else {
      for (Player player: playerList) {
        player.playSound(player, sound, soundCategory, volume, pitch);
      }
    }
  }

  public static void playCustomSoundToAll(List<Player> playerList, Location location, String soundName, SoundCategory soundCategory, Float volume, Float pitch) {
    if (location != null) {
      for (Player player: playerList) {
        if (player.getWorld().equals(location.getWorld()))
          if (player.getLocation().distance(location) <= 16) player.playSound(location, soundName, soundCategory, volume, pitch);
      }
    }
    else {
      for (Player player: playerList) {
        player.playSound(player, soundName, soundCategory, volume, pitch);
      }
    }
  }

  public static void playCustomSound(Player player, Location location, String soundName, SoundCategory soundCategory, Float volume, Float pitch) {

    if (location != null)
      if (player.getWorld().equals(location.getWorld()))
        if (player.getLocation().distance(location) <= 16) player.playSound(location, soundName, soundCategory, volume, pitch);
    else player.playSound(player.getLocation(), soundName, soundCategory, volume, pitch);
  }

  public static void playSound(Player player, Location location, Sound sound, SoundCategory soundCategory, float volume, float pitch) {

    if (location != null)
      if (player.getWorld().equals(location.getWorld()))
        if (player.getLocation().distance(location) <= 16) player.playSound(location, sound, soundCategory, volume, pitch);
    else player.playSound(player.getLocation(), sound, soundCategory, volume, pitch);
  }

  public static void playSound(Player player, Location location, String soundName, SoundCategory soundCategory, float volume, float pitch) {

    if (location != null)
      if (player.getWorld().equals(location.getWorld()))
        if (player.getLocation().distance(location) <= 16) player.playSound(location, soundName, soundCategory, volume, pitch);
        else player.playSound(player.getLocation(), soundName, soundCategory, volume, pitch);
  }

}

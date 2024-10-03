package fr.dreamin.api.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Utility class for sending particles to players within a certain range.
 */
public class ParticleUtils {

  /**
   * Sends a particle effect to all players in the provided list who are within a certain distance of the location.
   *
   * @param playerList The list of players to send the particles to.
   * @param particle The type of particle to spawn.
   * @param location The location where the particles should appear.
   * @param amount The number of particles to spawn.
   * @param speed The speed of the particles.
   * @param offsetX The offset for the particles along the X axis.
   * @param offsetY The offset for the particles along the Y axis.
   * @param offsetZ The offset for the particles along the Z axis.
   */
  public static void sendParticleToAll(final List<Player> playerList, final Particle particle, final Location location, final int amount, final double speed, final double offsetX, final double offsetY, final double offsetZ) {
    playerList.forEach(player -> {
      if (isPlayerInRange(player, location)) {
        player.spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ, speed);
      }
    });
  }

  /**
   * Checks if a player is within the same world as the particle location and within a distance of 20 blocks.
   *
   * @param player The player to check.
   * @param location The location of the particle.
   * @return true if the player is in the same world and within 20 blocks of the location, false otherwise.
   */
  private static boolean isPlayerInRange(final Player player, final Location location) {
    return player.getWorld().equals(location.getWorld()) && player.getLocation().distance(location) <= 20;
  }
}
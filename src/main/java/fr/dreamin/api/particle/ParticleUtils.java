package fr.dreamin.api.particle;

import fr.dreamin.mctools.McTools;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
  public static void sendParticleToAll(final List<Player> playerList, final Particle particle, final Location location, double distance, final int amount, final double speed, final double offsetX, final double offsetY, final double offsetZ) {
    playerList.forEach(player -> {
      if (isPlayerInRange(player, location, distance)) {
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
  private static boolean isPlayerInRange(final Player player, final Location location, double distance) {
    return player.getWorld().equals(location.getWorld()) && player.getLocation().distance(location) <= distance;
  }

  /**
   * Starts a circular particle wave effect around the player.
   *
   * @param player               The player around whom the particles will be generated.
   * @param durationInMicroSeconds Duration of the particle effect.
   * @param showAll              If true, the particles will be visible to all players.
   */
  public static void startParticleWave(Player player, double durationInMicroSeconds, boolean showAll) {
    new BukkitRunnable() {
      private double radius = 0.0;
      private double elapsedMicroSeconds = 0.0;
      private final double radiusIncrement = 1.5;

      @Override
      public void run() {
        if (elapsedMicroSeconds >= durationInMicroSeconds) {
          this.cancel();
          return;
        }

        for (int degree = 0; degree < 360; degree += 15) {
          double angle = degree * Math.PI / 180;
          double x = radius * Math.cos(angle);
          double z = radius * Math.sin(angle);

          if (showAll) {
            player.getWorld().spawnParticle(Particle.DUST, player.getLocation().add(x, 0, z), 0, new Particle.DustOptions(Color.RED, 1));
          } else {
            player.spawnParticle(Particle.DUST, player.getLocation().add(x, 0, z), 0, new Particle.DustOptions(Color.RED, 1));
          }
        }
        radius += radiusIncrement;
        elapsedMicroSeconds += 0.1;
      }
    }.runTaskTimer(McTools.getInstance(), 0L, 1L);
  }

  /**
   * Starts a reverse particle wave effect, where the circle shrinks.
   *
   * @param player               The player around whom the particles will shrink.
   * @param durationInMicroSeconds Duration of the particle effect.
   * @param showAll              If true, the particles will be visible to all players.
   */
  public static void startReverseParticleWave(Player player, double durationInMicroSeconds, boolean showAll) {
    if (McTools.getInstance() == null) return;

    new BukkitRunnable() {
      private double radius = 7.5;
      private double elapsedMicroSeconds = 0.0;
      private final double radiusIncrement = -1.5;

      @Override
      public void run() {
        if (elapsedMicroSeconds >= durationInMicroSeconds) {
          this.cancel();
          return;
        }

        for (int degree = 0; degree < 360; degree += 15) {
          double angle = degree * Math.PI / 180;
          double x = radius * Math.cos(angle);
          double z = radius * Math.sin(angle);

          if (showAll) {
            player.getWorld().spawnParticle(Particle.DUST, player.getLocation().add(x, 0, z), 0, new Particle.DustOptions(Color.GREEN, 1));
          } else {
            player.spawnParticle(Particle.DUST, player.getLocation().add(x, 0, z), 0, new Particle.DustOptions(Color.GREEN, 1));
          }
        }
        radius += radiusIncrement;
        elapsedMicroSeconds += 0.1;
      }
    }.runTaskTimer(McTools.getInstance(), 0L, 1L);
  }
}
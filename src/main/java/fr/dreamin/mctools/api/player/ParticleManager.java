package fr.dreamin.mctools.api.player;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.List;

public class ParticleManager {
  public static void sendParticleToAll(List<Player> playerList, Particle particle, Location location, int amount, double speed, double offset1, double offset2, double offset3) {
    for (Player player : playerList) {
      if (player.getWorld().equals(location.getWorld()))
        if (player.getLocation().distance(location) <= 20)
          player.spawnParticle(particle, location, amount, speed, offset1, offset2, offset3);
    }
  }

}

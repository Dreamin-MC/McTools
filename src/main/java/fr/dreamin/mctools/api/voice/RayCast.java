package fr.dreamin.mctools.api.voice;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class RayCast {

  public static boolean hasLineOfSight(Player player, Player target) {
    // Définir des points de raycast à différentes hauteurs
    List<Location> playerPoints = getRaycastPoints(player);
    List<Location> targetPoints = getRaycastPoints(target);

    for (Location playerPoint : playerPoints) {
      for (Location targetPoint : targetPoints) {
        if (raycast(playerPoint, targetPoint, player, target)) return true;
      }
    }

    return false;
  }

  private static List<Location> getRaycastPoints(Player player) {
    Location base = player.getLocation();
    return Arrays.asList(
      base.clone().add(0, 1.65, 0), // Yeux
      base.clone().add(0, 1, 0),    // Milieu du corps
      base                                    // Pieds
    );
  }

  private static boolean raycast(Location from, Location to, Player player, Player target) {
    Vector direction = to.toVector().subtract(from.toVector());
    RayTraceResult result = from.getWorld().rayTrace(from, direction, direction.length(), FluidCollisionMode.NEVER, true, 0.1,
      entity -> entity instanceof Player && entity.getUniqueId().equals(target.getUniqueId()));

    return result != null && result.getHitEntity() instanceof Player;
  }

}

package fr.dreamin.api.interpolation;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Interpolation {

  /**
   * Génère une liste de locations correspondant à une interpolation (linéaire ou non)
   * entre deux locations sur un nombre de ticks donné.
   *
   * @param startLocation  La location de départ.
   * @param endLocation    La location d'arrivée.
   * @param ticks          Le nombre de ticks d'intervalle entre les deux locations.
   * @param interpolationType Le type d'interpolation (linéaire, ease-in, ease-out, etc.).
   * @return Une liste de locations.
   */
  public static List<Location> generateInterpolatedLocations(Location startLocation, Location endLocation, int ticks, InterpolationType interpolationType) {
    List<Location> locations = new ArrayList<>();

    // Coordonnées de départ
    double startX = startLocation.getX();
    double startY = startLocation.getY();
    double startZ = startLocation.getZ();
    float startYaw = startLocation.getYaw();
    float startPitch = startLocation.getPitch();

    // Coordonnées d'arrivée
    double endX = endLocation.getX();
    double endY = endLocation.getY();
    double endZ = endLocation.getZ();
    float endYaw = endLocation.getYaw();
    float endPitch = endLocation.getPitch();

    // Générer les locations pour chaque tick
    for (int i = 0; i <= ticks; i++) {
      double t = (double) i / ticks; // Temps normalisé (0.0 à 1.0)

      // Appliquer le type d'interpolation sélectionné
      switch (interpolationType) {
        case LINEAR:
          // Pas de modification pour la linéarité, t reste inchangé
          break;
        case EASE_OUT:
          t = easeOut(t);
          break;
        case EASE_IN_OUT:
          t = easeInOut(t);
          break;
        case EASE_IN:
          t = easeIn(t);
          break;
      }

      // Calcul des valeurs interpolées pour chaque axe
      double currentX = interpolate(startX, endX, t);
      double currentY = interpolate(startY, endY, t);
      double currentZ = interpolate(startZ, endZ, t);
      float currentYaw = (float) interpolate(startYaw, endYaw, t);
      float currentPitch = (float) interpolate(startPitch, endPitch, t);

      // Ajouter la location interpolée à la liste
      locations.add(new Location(startLocation.getWorld(), currentX, currentY, currentZ, currentYaw, currentPitch));
    }

    return locations;
  }

  // Fonction d'interpolation linéaire
  private static double interpolate(double start, double end, double t) {
    return start + (end - start) * t;
  }

  // Fonction d'easing-out quadratique (rapide au début, puis ralentit)
  private static double easeOut(double t) {
    return t * (2 - t);
  }

  // Fonction d'easing-in-out cubique (lente au début et à la fin, rapide au milieu)
  private static double easeInOut(double t) {
    return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
  }

  // Fonction d'easing-in quadratique (lent au début, puis accélère)
  private static double easeIn(double t) {
    return t * t;
  }

}

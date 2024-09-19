package fr.dreamin.api.interpolation;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;

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

  /**
   * Génère une liste d'ArmorStandPose contenant la position et la pose (angles) des membres
   * d'un ArmorStand entre deux positions et poses sur un nombre de ticks donné.
   *
   * @param startLocation      La position de départ.
   * @param endLocation        La position d'arrivée.
   * @param startPose          Les angles des membres à la position de départ.
   * @param endPose            Les angles des membres à la position d'arrivée.
   * @param ticks              Le nombre de ticks d'intervalle entre les deux positions/poses.
   * @param interpolationType  Le type d'interpolation (linéaire, ease-in, ease-out, etc.).
   * @return Une liste d'ArmorStandPose contenant la position et les angles des membres.
   */
  public static List<ArmorStandPose> generateInterpolatedPosesWithLocation(Location startLocation, Location endLocation, ArmorStandPose startPose, ArmorStandPose endPose, int ticks, InterpolationType interpolationType) {
    List<ArmorStandPose> poses = new ArrayList<>();

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

    for (int i = 0; i <= ticks; i++) {
      double t = (double) i / ticks; // Temps normalisé (0.0 à 1.0)

      // Appliquer l'interpolation
      switch (interpolationType) {
        case LINEAR:
          break; // t reste inchangé pour l'interpolation linéaire
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

      // Interpolation des positions
      double currentX = interpolate(startX, endX, t);
      double currentY = interpolate(startY, endY, t);
      double currentZ = interpolate(startZ, endZ, t);
      float currentYaw = (float) interpolate(startYaw, endYaw, t);
      float currentPitch = (float) interpolate(startPitch, endPitch, t);

      // Créer la nouvelle location interpolée
      Location interpolatedLocation = new Location(startLocation.getWorld(), currentX, currentY, currentZ, currentYaw, currentPitch);

      // Interpolation des angles des membres
      EulerAngle interpolatedRightArm = interpolateEulerAngle(startPose.getRightArmPose(), endPose.getRightArmPose(), t);
      EulerAngle interpolatedLeftArm = interpolateEulerAngle(startPose.getLeftArmPose(), endPose.getLeftArmPose(), t);
      EulerAngle interpolatedRightLeg = interpolateEulerAngle(startPose.getRightLegPose(), endPose.getRightLegPose(), t);
      EulerAngle interpolatedLeftLeg = interpolateEulerAngle(startPose.getLeftLegPose(), endPose.getLeftLegPose(), t);

      // Ajouter la pose (location + angles) à la liste
      poses.add(new ArmorStandPose(interpolatedLocation, interpolatedRightArm, interpolatedLeftArm, interpolatedRightLeg, interpolatedLeftLeg));
    }

    return poses;
  }

  // Fonction d'interpolation linéaire
  private static double interpolate(double start, double end, double t) {
    return start + (end - start) * t;
  }

  // Fonction d'interpolation pour les angles Euler
  private static EulerAngle interpolateEulerAngle(EulerAngle start, EulerAngle end, double t) {
    double x = interpolate(start.getX(), end.getX(), t);
    double y = interpolate(start.getY(), end.getY(), t);
    double z = interpolate(start.getZ(), end.getZ(), t);
    return new EulerAngle(x, y, z);
  }

  // Easing-out quadratique (rapide au début, puis ralentit)
  private static double easeOut(double t) {
    return t * (2 - t);
  }

  // Easing-in-out cubique (lent au début et à la fin, rapide au milieu)
  private static double easeInOut(double t) {
    return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
  }

  // Easing-in quadratique (lent au début, puis accélère)
  private static double easeIn(double t) {
    return t * t;
  }

  @Getter @Setter
  public static class ArmorStandPose {
    private Location location;
    private EulerAngle rightArmPose, leftArmPose, rightLegPose, leftLegPose;

    public ArmorStandPose(Location location, EulerAngle rightArmPose, EulerAngle leftArmPose, EulerAngle rightLegPose, EulerAngle leftLegPose) {
      this.location = location;
      this.rightArmPose = rightArmPose;
      this.leftArmPose = leftArmPose;
      this.rightLegPose = rightLegPose;
      this.leftLegPose = leftLegPose;
    }
  }
}

package fr.dreamin.api.interpolation;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class Interpolation {

  /**
   * Generates a list of locations corresponding to an interpolation (linear or not)
   * between two locations over a given number of ticks.
   *
   * @param startLocation    The starting location.
   * @param endLocation      The destination location.
   * @param ticks            The number of ticks between the two locations.
   * @param interpolationType The type of interpolation (linear, ease-in, ease-out, etc.).
   * @return A list of locations.
   */
  public static List<Location> generateInterpolatedLocations(Location startLocation, Location endLocation, int ticks, InterpolationType interpolationType) {
    List<Location> locations = new ArrayList<>();

    // Starting coordinates
    double startX = startLocation.getX();
    double startY = startLocation.getY();
    double startZ = startLocation.getZ();
    float startYaw = startLocation.getYaw();
    float startPitch = startLocation.getPitch();

    // Ending coordinates
    double endX = endLocation.getX();
    double endY = endLocation.getY();
    double endZ = endLocation.getZ();
    float endYaw = endLocation.getYaw();
    float endPitch = endLocation.getPitch();

    // Generate locations for each tick
    for (int i = 0; i <= ticks; i++) {
      double t = (double) i / ticks; // Normalized time (0.0 to 1.0)

      // Apply the selected interpolation type
      switch (interpolationType) {
        case LINEAR:
          // No change for linearity, t remains unchanged
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

      // Calculate interpolated values for each axis
      double currentX = interpolate(startX, endX, t);
      double currentY = interpolate(startY, endY, t);
      double currentZ = interpolate(startZ, endZ, t);
      float currentYaw = (float) interpolate(startYaw, endYaw, t);
      float currentPitch = (float) interpolate(startPitch, endPitch, t);

      // Add the interpolated location to the list
      locations.add(new Location(startLocation.getWorld(), currentX, currentY, currentZ, currentYaw, currentPitch));
    }

    return locations;
  }

  /**
   * Generates a list of ArmorStandPose containing the position and pose (angles) of the limbs
   * of an ArmorStand between two positions and poses over a given number of ticks.
   *
   * @param startLocation    The starting position.
   * @param endLocation      The destination position.
   * @param startPose        The limb angles at the starting position.
   * @param endPose          The limb angles at the destination position.
   * @param ticks            The number of ticks between the two positions/poses.
   * @param interpolationType The type of interpolation (linear, ease-in, ease-out, etc.).
   * @return A list of ArmorStandPose containing the position and limb angles.
   */
  public static List<ArmorStandPose> generateInterpolatedPosesWithLocation(Location startLocation, Location endLocation, ArmorStandPose startPose, ArmorStandPose endPose, int ticks, InterpolationType interpolationType) {
    List<ArmorStandPose> poses = new ArrayList<>();

    // Starting coordinates
    double startX = startLocation.getX();
    double startY = startLocation.getY();
    double startZ = startLocation.getZ();
    float startYaw = startLocation.getYaw();
    float startPitch = startLocation.getPitch();

    // Ending coordinates
    double endX = endLocation.getX();
    double endY = endLocation.getY();
    double endZ = endLocation.getZ();
    float endYaw = endLocation.getYaw();
    float endPitch = endLocation.getPitch();

    for (int i = 0; i <= ticks; i++) {
      double t = (double) i / ticks; // Normalized time (0.0 to 1.0)

      // Apply interpolation
      switch (interpolationType) {
        case LINEAR:
          break; // t remains unchanged for linear interpolation
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

      // Interpolation of positions
      double currentX = interpolate(startX, endX, t);
      double currentY = interpolate(startY, endY, t);
      double currentZ = interpolate(startZ, endZ, t);
      float currentYaw = (float) interpolate(startYaw, endYaw, t);
      float currentPitch = (float) interpolate(startPitch, endPitch, t);

      // Create the new interpolated location
      Location interpolatedLocation = new Location(startLocation.getWorld(), currentX, currentY, currentZ, currentYaw, currentPitch);

      // Interpolation of limb angles
      EulerAngle interpolatedRightArm = interpolateEulerAngle(startPose.getRightArmPose(), endPose.getRightArmPose(), t);
      EulerAngle interpolatedLeftArm = interpolateEulerAngle(startPose.getLeftArmPose(), endPose.getLeftArmPose(), t);
      EulerAngle interpolatedRightLeg = interpolateEulerAngle(startPose.getRightLegPose(), endPose.getRightLegPose(), t);
      EulerAngle interpolatedLeftLeg = interpolateEulerAngle(startPose.getLeftLegPose(), endPose.getLeftLegPose(), t);

      // Add the pose (location + angles) to the list
      poses.add(new ArmorStandPose(interpolatedLocation, interpolatedRightArm, interpolatedLeftArm, interpolatedRightLeg, interpolatedLeftLeg));
    }

    return poses;
  }

  // Linear interpolation function
  private static double interpolate(double start, double end, double t) {
    return start + (end - start) * t;
  }

  // Interpolation function for Euler angles
  private static EulerAngle interpolateEulerAngle(EulerAngle start, EulerAngle end, double t) {
    double x = interpolate(start.getX(), end.getX(), t);
    double y = interpolate(start.getY(), end.getY(), t);
    double z = interpolate(start.getZ(), end.getZ(), t);
    return new EulerAngle(x, y, z);
  }

  // Quadratic ease-out (fast at the beginning, then slows down)
  private static double easeOut(double t) {
    return t * (2 - t);
  }

  // Cubic ease-in-out (slow at the beginning and end, fast in the middle)
  private static double easeInOut(double t) {
    return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
  }

  // Quadratic ease-in (slow at the beginning, then speeds up)
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
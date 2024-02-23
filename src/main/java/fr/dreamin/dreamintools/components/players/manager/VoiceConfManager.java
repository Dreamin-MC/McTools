package fr.dreamin.dreamintools.components.players.manager;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class VoiceConfManager {


  private boolean isVoice = false;
  private int radius = 5;
  private double y = 0.5;

  private void drawCircle(Location center, double radius, Particle particle, Particle.DustOptions options) {
    int points = 50; // Nombre de points dans le cercle
    double increment = (2 * Math.PI) / points;

    for(int i = 0; i < points; i++) {
      double angle = i * increment;
      double x = center.getX() + (radius * Math.cos(angle));
      double z = center.getZ() + (radius * Math.sin(angle));
      Location point = new Location(center.getWorld(), x, center.getY() + getY(), z);

      if (particle == Particle.REDSTONE) {
        center.getWorld().spawnParticle(particle, point, 1, options);
      } else {
        center.getWorld().spawnParticle(particle, point, 1);
      }
    }
  }

  public void drawParticleCircles(Player player) {
    Location center = player.getLocation();
    double greenRadius = getRadius() / 2;
    double orangeRadius = getRadius() * 0.75;
    double redRadius = getRadius();

    Particle.DustOptions greenOptions = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1); // Vert
    Particle.DustOptions orangeOptions = new Particle.DustOptions(Color.fromRGB(255, 165, 0), 1); // Orange
    Particle.DustOptions redOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1); // Rouge

    drawCircle(center, greenRadius, Particle.REDSTONE, greenOptions);
    drawCircle(center, orangeRadius, Particle.REDSTONE, orangeOptions);
    drawCircle(center, redRadius, Particle.REDSTONE, redOptions);
  }

  public boolean isVoice() {
    return isVoice;
  }

  public void setVoice(boolean isVoice) {
    this.isVoice = isVoice;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

}

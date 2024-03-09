package fr.dreamin.mctools.components.players.manager;

import fr.dreamin.mctools.components.players.MTPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class VoiceConfPlayerManager {


  @Getter @Setter private boolean isVoice = false;
  @Getter @Setter private int radius = 5;
  @Getter @Setter private double y = 0.5;
  @Getter private MTPlayer mtPlayer;

  public VoiceConfPlayerManager(MTPlayer mtPlayer) {
    this.mtPlayer = mtPlayer;

    mtPlayer.getPlayerTickManager().addFilter(tick -> {
      if (isVoice()) drawParticleCircles(mtPlayer.getPlayer());
    });
  }

  private void drawCircle(Location center, double radius, Particle particle, Particle.DustOptions options) {
    int points = 50; // Nombre de points dans le cercle
    double increment = (2 * Math.PI) / points;

    for(int i = 0; i < points; i++) {
      double angle = i * increment;
      double x = center.getX() + (radius * Math.cos(angle));
      double z = center.getZ() + (radius * Math.sin(angle));
      Location point = new Location(center.getWorld(), x, center.getY() + getY(), z);

      if (particle == Particle.REDSTONE) center.getWorld().spawnParticle(particle, point, 1, options);
      else center.getWorld().spawnParticle(particle, point, 1);
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

}

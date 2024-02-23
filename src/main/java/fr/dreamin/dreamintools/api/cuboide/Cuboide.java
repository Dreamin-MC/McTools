package fr.dreamin.dreamintools.api.cuboide;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Cuboide {

  private int id;
  private Location locA;
  private Location locB;
  private List<SavedBlock> savedBlocks;
  private boolean saveBlocks = false;

  public Cuboide(Location locA, Location locB, boolean saveBlocks) {
    this.locA = locA;
    this.locB = locB;
    if (saveBlocks)
      saveBlocks();
  }


  public Cuboide(Location locA, Location locB) {
    this.id = id;
    this.locA = locA;
    this.locB = locB;
  }

  private void saveBlocks() {
    savedBlocks = new ArrayList<>();

    for (int x = Math.min(getLocA().getBlockX(), getLocB().getBlockX()); x <= Math.max(getLocA().getBlockX(), getLocB().getBlockX()); x++) {
      for (int y = Math.min(getLocA().getBlockY(), getLocB().getBlockY()); y <= Math.max(getLocA().getBlockY(), getLocB().getBlockY()); y++) {
        for (int z = Math.min(getLocA().getBlockZ(), getLocB().getBlockZ()); z <= Math.max(getLocA().getBlockZ(), getLocB().getBlockZ()); z++) {
          Block block = locA.getWorld().getBlockAt(x, y, z);
          BlockState state = block.getState();
          BlockData data = state.getBlockData();
          savedBlocks.add(new SavedBlock(block.getLocation(), data));
        }
      }
    }
  }

  public void setType(Material material) {
    for (SavedBlock savedBlock : savedBlocks) {
      locA.getWorld().getBlockAt(savedBlock.getLocation()).setType(material);
    }
  }

  public void replaceType(Material mat1, Material mat2) {
    for (SavedBlock savedBlock : savedBlocks) {
      if (locA.getWorld().getBlockAt(savedBlock.getLocation()).getType().equals(mat1)) locA.getWorld().getBlockAt(savedBlock.getLocation()).setType(mat2);
    }
  }

  public void setTypeEverySecond(final Material material, Plugin plugin, long tick) {
    final int minY = Math.min(getLocA().getBlockY(), getLocB().getBlockY());
    final int maxY = Math.max(getLocA().getBlockY(), getLocB().getBlockY());

    // Créer une nouvelle tâche
    new BukkitRunnable() {
      private int currentY = maxY;

      @Override
      public void run() {
        if (currentY < minY) {
          // Arrêter la tâche si nous avons atteint le bas
          this.cancel();
        } else {
          // Changer le type de bloc à l'altitude actuelle
          for (SavedBlock savedBlock : savedBlocks) {
            if (savedBlock.getLocation().getBlockY() == currentY) {
              locA.getWorld().getBlockAt(savedBlock.getLocation()).setType(material);
            }
          }

          // Descendre d'un bloc pour le prochain tour
          currentY--;
        }
      }
    }.runTaskTimer(plugin, 0L, tick); // 20 ticks = 1 seconde
  }

  public void removeBlocksEverySecond(Plugin plugin, long tick) {
    final int minY = Math.min(getLocA().getBlockY(), getLocB().getBlockY());
    final int maxY = Math.max(getLocA().getBlockY(), getLocB().getBlockY());

    new BukkitRunnable() {
      private int currentY = minY;

      @Override
      public void run() {
        if (currentY > maxY) {
          // Arrêtez la tâche si nous avons atteint le sommet
          this.cancel();
        } else {
          // Changez le type de bloc à l'altitude actuelle en AIR pour le supprimer
          for (SavedBlock savedBlock : savedBlocks) {
            if (savedBlock.getLocation().getBlockY() == currentY) {
              Location loc = savedBlock.getLocation();
              Block block = locA.getWorld().getBlockAt(loc);
              block.setBlockData(savedBlock.getBlockData(), false);
            }
          }

          // Monte d'un bloc pour le prochain tour
          currentY++;
        }
      }
    }.runTaskTimer(plugin, 0L, tick); // 20 ticks = 1 seconde
  }

  public void restoreBlocks() {

    if (!saveBlocks)
      return;

    for (SavedBlock savedBlock : savedBlocks) {
      Location loc = savedBlock.getLocation();
      Block block = locA.getWorld().getBlockAt(loc);
      block.setBlockData(savedBlock.getBlockData(), false);
    }
  }


  public Location getLocA() {
    return locA;
  }

  public Location getLocB() {
    return locB;
  }

  public int getId() {
    return id;
  }

  public Location getCenter() {
    return new Location(locA.getWorld(), (locA.getX() + locB.getX())/2, Math.min(locA.getY(), locB.getY()) +1, (locA.getZ() + locB.getZ())/2);
  }

  public boolean isLocationIn(Location location) {
    return location.getX() >= Math.min(getLocA().getX(), getLocB().getX()) && location.getX() <= Math.max(getLocA().getX(), getLocB().getX()) +1
      && location.getY() >= Math.min(getLocA().getY(), getLocB().getY()) && location.getY() <= Math.max(getLocA().getY(), getLocB().getY()) + 1
      && location.getZ() >= Math.min(getLocA().getZ(), getLocB().getZ()) && location.getZ() <= Math.max(getLocA().getZ(), getLocB().getZ()) + 1;
  }


  private static class SavedBlock {
    private final Location location;
    private final BlockData blockData;

    public SavedBlock(Location location, BlockData blockData) {
      this.location = location;
      this.blockData = blockData;
    }

    public Location getLocation() {
      return location;
    }

    public BlockData getBlockData() {
      return blockData;
    }
  }
}
package fr.dreamin.api.cuboid;

import fr.dreamin.mctools.McTools;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter @Setter
public class Cuboid {

  private Location locA, locB;

  public Cuboid(Location locA, Location locB) {
    this.locA = locA;
    this.locB = locB;
  }

  public Location getCenter() {
    if (locA == null || locB == null || (!locA.getWorld().equals(locB.getWorld()))) return null;
    return new Location(this.locA.getWorld(), (this.locA.getX() + this.locB.getX())/2, Math.min(this.locA.getY(), this.locB.getY()) +1, (this.locA.getZ() + this.locB.getZ())/2);
  }

  public boolean isLocationIn(Location location) {
    return location.getX() >= Math.min(getLocA().getX(), getLocB().getX()) && location.getX() <= Math.max(getLocA().getX(), getLocB().getX()) +1
      && location.getY() >= Math.min(getLocA().getY(), getLocB().getY()) && location.getY() <= Math.max(getLocA().getY(), getLocB().getY()) + 1
      && location.getZ() >= Math.min(getLocA().getZ(), getLocB().getZ()) && location.getZ() <= Math.max(getLocA().getZ(), getLocB().getZ()) + 1;
  }

  public void setMaterial(Material material) {
    if (locA == null || locB == null || !locA.getWorld().equals(locB.getWorld())) return;

    World world = locA.getWorld();

    int minX = Math.min(locA.getBlockX(), locB.getBlockX());
    int maxX = Math.max(locA.getBlockX(), locB.getBlockX());
    int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    int maxY = Math.max(locA.getBlockY(), locB.getBlockY());
    int minZ = Math.min(locA.getBlockZ(), locB.getBlockZ());
    int maxZ = Math.max(locA.getBlockZ(), locB.getBlockZ());

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          Block block = world.getBlockAt(x, y, z);
          block.setType(material, false);
        }
      }
    }
  }

  public void setBlockData(BlockData blockData) {
    if (locA == null || locB == null || !locA.getWorld().equals(locB.getWorld())) return;

    World world = locA.getWorld();

    int minX = Math.min(locA.getBlockX(), locB.getBlockX());
    int maxX = Math.max(locA.getBlockX(), locB.getBlockX());
    int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    int maxY = Math.max(locA.getBlockY(), locB.getBlockY());
    int minZ = Math.min(locA.getBlockZ(), locB.getBlockZ());
    int maxZ = Math.max(locA.getBlockZ(), locB.getBlockZ());

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          Block block = world.getBlockAt(x, y, z);
          block.setBlockData(blockData, false);
        }
      }
    }
  }

  public void replaceMaterial(Material replaced, Material newMaterial) {
    if (locA == null || locB == null || !locA.getWorld().equals(locB.getWorld())) return;

    World world = locA.getWorld();

    int minX = Math.min(locA.getBlockX(), locB.getBlockX());
    int maxX = Math.max(locA.getBlockX(), locB.getBlockX());
    int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    int maxY = Math.max(locA.getBlockY(), locB.getBlockY());
    int minZ = Math.min(locA.getBlockZ(), locB.getBlockZ());
    int maxZ = Math.max(locA.getBlockZ(), locB.getBlockZ());

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          Block block = world.getBlockAt(x, y, z);
          if (block.getType() == replaced) {
            block.setType(newMaterial, false);
          }
        }
      }
    }
  }

  public void replaceBlockData(BlockData replaced, BlockData newBlockData) {
    if (locA == null || locB == null || !locA.getWorld().equals(locB.getWorld())) return;

    World world = locA.getWorld();

    int minX = Math.min(locA.getBlockX(), locB.getBlockX());
    int maxX = Math.max(locA.getBlockX(), locB.getBlockX());
    int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    int maxY = Math.max(locA.getBlockY(), locB.getBlockY());
    int minZ = Math.min(locA.getBlockZ(), locB.getBlockZ());
    int maxZ = Math.max(locA.getBlockZ(), locB.getBlockZ());

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          Block block = world.getBlockAt(x, y, z);
          if (block.getBlockData() == replaced) {
            block.setBlockData(newBlockData, false);
          }
        }
      }
    }
  }

  public void setMaterialEveryTick(final Material material, long tick, boolean topToBottom) {
    final int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    final int maxY = Math.max(locA.getBlockY(), locB.getBlockY());

    new BukkitRunnable() {
      private int currentY = topToBottom ? maxY : minY;

      @Override
      public void run() {
        if (topToBottom ? currentY < minY : currentY > maxY) {
          this.cancel();
          return;
        }

        for (int x = Math.min(locA.getBlockX(), locB.getBlockX()); x <= Math.max(locA.getBlockX(), locB.getBlockX()); x++) {
          for (int z = Math.min(locA.getBlockZ(), locB.getBlockZ()); z <= Math.max(locA.getBlockZ(), locB.getBlockZ()); z++) {
            Block block = locA.getWorld().getBlockAt(x, currentY, z);
            block.setType(material, false);
          }
        }

        if (topToBottom) currentY--;
        else currentY++;
      }
    }.runTaskTimerAsynchronously(McTools.getInstance(), 0L, tick);
  }

  public void setBlockDataEveryTick(final BlockData blockData, long tick, boolean topToBottom) {
    final int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    final int maxY = Math.max(locA.getBlockY(), locB.getBlockY());

    new BukkitRunnable() {
      private int currentY = topToBottom ? maxY : minY;

      @Override
      public void run() {
        if (topToBottom ? currentY < minY : currentY > maxY) {
          this.cancel();
          return;
        }

        for (int x = Math.min(locA.getBlockX(), locB.getBlockX()); x <= Math.max(locA.getBlockX(), locB.getBlockX()); x++) {
          for (int z = Math.min(locA.getBlockZ(), locB.getBlockZ()); z <= Math.max(locA.getBlockZ(), locB.getBlockZ()); z++) {
            Block block = locA.getWorld().getBlockAt(x, currentY, z);
            block.setBlockData(blockData, false);
          }
        }

        if (topToBottom) currentY--;
        else currentY++;
      }
    }.runTaskTimerAsynchronously(McTools.getInstance(), 0L, tick);
  }

  public void replaceMaterialEveryTick(final Material replaced, final Material newMaterial, long tick, boolean topToBottom) {
    final int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    final int maxY = Math.max(locA.getBlockY(), locB.getBlockY());

    new BukkitRunnable() {
      private int currentY = topToBottom ? maxY : minY;

      @Override
      public void run() {
        if (topToBottom ? currentY < minY : currentY > maxY) {
          this.cancel();
          return;
        }

        for (int x = Math.min(locA.getBlockX(), locB.getBlockX()); x <= Math.max(locA.getBlockX(), locB.getBlockX()); x++) {
          for (int z = Math.min(locA.getBlockZ(), locB.getBlockZ()); z <= Math.max(locA.getBlockZ(), locB.getBlockZ()); z++) {
            Block block = locA.getWorld().getBlockAt(x, currentY, z);
            if (block.getType() == replaced) {
              block.setType(newMaterial, false); // Remplace uniquement les blocs avec le matériau spécifique
            }
          }
        }

        if (topToBottom) currentY--;
        else currentY++;
      }
    }.runTaskTimerAsynchronously(McTools.getInstance(), 0L, tick);
  }

  public void replaceBlockDataEveryTick(final BlockData replaced, final BlockData newBlockData, long tick, boolean topToBottom) {
    final int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    final int maxY = Math.max(locA.getBlockY(), locB.getBlockY());

    new BukkitRunnable() {
      private int currentY = topToBottom ? maxY : minY;

      @Override
      public void run() {
        if (topToBottom ? currentY < minY : currentY > maxY) {
          this.cancel();
          return;
        }

        for (int x = Math.min(locA.getBlockX(), locB.getBlockX()); x <= Math.max(locA.getBlockX(), locB.getBlockX()); x++) {
          for (int z = Math.min(locA.getBlockZ(), locB.getBlockZ()); z <= Math.max(locA.getBlockZ(), locB.getBlockZ()); z++) {
            Block block = locA.getWorld().getBlockAt(x, currentY, z);
            if (block.getBlockData().matches(replaced)) {
              block.setBlockData(newBlockData, false); // Remplace uniquement les blocs avec le BlockData spécifique
            }
          }
        }

        if (topToBottom) currentY--;
        else currentY++;
      }
    }.runTaskTimerAsynchronously(McTools.getInstance(), 0L, tick);
  }

  public int countBlocksOfMaterial(Material material) {
    if (locA == null || locB == null || !locA.getWorld().equals(locB.getWorld())) return 0;

    World world = locA.getWorld();
    int count = 0;

    int minX = Math.min(locA.getBlockX(), locB.getBlockX());
    int maxX = Math.max(locA.getBlockX(), locB.getBlockX());
    int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    int maxY = Math.max(locA.getBlockY(), locB.getBlockY());
    int minZ = Math.min(locA.getBlockZ(), locB.getBlockZ());
    int maxZ = Math.max(locA.getBlockZ(), locB.getBlockZ());

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          Block block = world.getBlockAt(x, y, z);
          if (block.getType() == material) {
            count++;
          }
        }
      }
    }

    return count;
  }

  public int countBlocksOfBlockData(BlockData blockData) {
    if (locA == null || locB == null || !locA.getWorld().equals(locB.getWorld())) return 0;

    World world = locA.getWorld();
    int count = 0;

    int minX = Math.min(locA.getBlockX(), locB.getBlockX());
    int maxX = Math.max(locA.getBlockX(), locB.getBlockX());
    int minY = Math.min(locA.getBlockY(), locB.getBlockY());
    int maxY = Math.max(locA.getBlockY(), locB.getBlockY());
    int minZ = Math.min(locA.getBlockZ(), locB.getBlockZ());
    int maxZ = Math.max(locA.getBlockZ(), locB.getBlockZ());

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          Block block = world.getBlockAt(x, y, z);
          if (block.getBlockData().matches(blockData)) {
            count++;
          }
        }
      }
    }

    return count;
  }
}

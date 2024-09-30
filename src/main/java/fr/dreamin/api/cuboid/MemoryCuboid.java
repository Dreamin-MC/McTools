package fr.dreamin.api.cuboid;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MemoryCuboid extends Cuboid {

  private List<SavedBlockData> savedBlocks = new ArrayList<>();
  private List<SavedEntityData> savedEntities = new ArrayList<>();

  public MemoryCuboid(Location locA, Location locB) {
    super(locA, locB);
    saveAll();
  }

  // Méthode pour sauvegarder uniquement les blocs
  public void saveBlocks() {
    savedBlocks.clear();
    for (int x = Math.min(getLocA().getBlockX(), getLocB().getBlockX()); x <= Math.max(getLocA().getBlockX(), getLocB().getBlockX()); x++) {
      for (int y = Math.min(getLocA().getBlockY(), getLocB().getBlockY()); y <= Math.max(getLocA().getBlockY(), getLocB().getBlockY()); y++) {
        for (int z = Math.min(getLocA().getBlockZ(), getLocB().getBlockZ()); z <= Math.max(getLocA().getBlockZ(), getLocB().getBlockZ()); z++) {
          Block block = getLocA().getWorld().getBlockAt(x, y, z);
          savedBlocks.add(new SavedBlockData(block.getLocation(), block.getState().getBlockData()));
        }
      }
    }
  }

  // Méthode pour restaurer uniquement les blocs
  public void restoreBlocks() {
    savedBlocks.forEach(data -> {
      data.getLocation().getWorld().getBlockAt(data.getLocation()).setBlockData(data.getBlockData(), false);
    });
  }

  // Méthode pour sauvegarder uniquement les entités
  public void saveEntities() {
    savedEntities.clear();
    World world = getLocA().getWorld();
    for (Entity entity : world.getEntities()) {
      Location loc = entity.getLocation();
      if (isLocationIn(loc)) {
        savedEntities.add(new SavedEntityData(loc, entity.getType()));
      }
    }
  }

  // Méthode pour restaurer uniquement les entités
  public void restoreEntities() {
    removeAllEntities();
    World world = getLocA().getWorld();
    savedEntities.forEach(data -> {
      Entity entity = world.spawnEntity(data.getLocation(), data.getEntityType());
    });
  }

  public void saveAll() {
    saveBlocks();
    saveEntities();
  }

  public void restoreAll() {
    restoreBlocks();
    restoreEntities();
  }

  public void removeAllEntities() {
    getLocA().getWorld().getEntities().stream()
      .filter(entity -> isLocationIn(entity.getLocation())) // Filter entities inside the cuboid
      .forEach(Entity::remove);
  }

  @Getter @Setter
  private static class SavedBlockData {
    private final Location location;
    private final BlockData blockData;

    public SavedBlockData(Location location, BlockData blockData) {
      this.location = location;
      this.blockData = blockData;
    }
  }

  @Getter @Setter
  private static class SavedEntityData {
    private final Location location;
    private final EntityType entityType;


    public SavedEntityData(Location location, EntityType entityType) {
      this.location = location;
      this.entityType = entityType;
    }
  }
}

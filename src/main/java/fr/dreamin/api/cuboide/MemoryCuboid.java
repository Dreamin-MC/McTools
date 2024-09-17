package fr.dreamin.api.cuboide;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MemoryCuboid extends Cuboid{

  private List<SavedBlockData> savedBlocks = new ArrayList<>();

  public MemoryCuboid(Location locA, Location locB) {
    super(locA, locB);
    saveBlocks();
  }

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

  public void restoreBlocks() {
    this.savedBlocks.forEach(data -> {
      data.getLocation().getWorld().getBlockAt(data.getLocation()).setBlockData(data.getBlockData(), false);
    });
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

}

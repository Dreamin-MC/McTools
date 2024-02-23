package fr.dreamin.dreamintools.api.hologram;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.mysql.fetcher.displayFetcher.DisplayFetcher;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;

public class BlockDisplayManager {

  private BlockDisplay blockDisplay;

  public BlockDisplayManager(Location location, boolean save) {
    blockDisplay = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
    if (save) DisplayFetcher.addDisplay(McTools.getGson().toJson(blockDisplay), location);
  }

  public BlockDisplayManager(Location location, BlockData blockData, boolean save) {
    blockDisplay = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
    blockDisplay.setBlock(blockData);
    if (save) DisplayFetcher.addDisplay(McTools.getGson().toJson(blockDisplay), location);
  }

  public BlockDisplay getBlockDisplay() {
    return blockDisplay;
  }

}

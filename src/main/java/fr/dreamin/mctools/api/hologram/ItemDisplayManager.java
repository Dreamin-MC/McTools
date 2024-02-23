package fr.dreamin.mctools.api.hologram;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.mysql.fetcher.displayFetcher.DisplayFetcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class ItemDisplayManager {

  private ItemDisplay itemDisplay;

  public ItemDisplayManager(Location location, boolean save) {
    itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
    if (save) DisplayFetcher.addDisplay(McTools.getGson().toJson(itemDisplay), location);
  }

  public ItemDisplayManager(Location location, ItemStack itemStack, boolean save) {
    itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
    itemDisplay.setItemStack(itemStack);
    if (save) DisplayFetcher.addDisplay(McTools.getGson().toJson(itemDisplay), location);
  }

  public ItemDisplay getItemDisplay() {
    return itemDisplay;
  }
}

package fr.dreamin.mctools.api.hologram;

import com.google.gson.Gson;
import fr.dreamin.mctools.database.fetcher.displayFetcher.DisplayFetcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class ItemDisplayManager {

  private ItemDisplay itemDisplay;
  private Gson gson = new Gson();

  public ItemDisplayManager(Location location, boolean save) {
    itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
    if (save) DisplayFetcher.addDisplay(gson.toJson(itemDisplay), location);
  }

  public ItemDisplayManager(Location location, ItemStack itemStack, boolean save) {
    itemDisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
    itemDisplay.setItemStack(itemStack);
    if (save) DisplayFetcher.addDisplay(gson.toJson(itemDisplay), location);
  }

  public ItemDisplay getItemDisplay() {
    return itemDisplay;
  }
}

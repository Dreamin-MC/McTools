package fr.dreamin.api.minecraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MinecraftUtils {

  /**
   * Clears a specific material type from a player's inventory.
   *
   * @param player   The player whose inventory will be modified.
   * @param material The type of material to remove.
   */
  public static void clearPlayerItem(Player player, Material material) {
    for (ItemStack item : player.getInventory().getContents().clone()) {
      if (item != null && item.getType().equals(material)) {
        player.getInventory().remove(item);
      }
    }
  }

  /**
   * Checks if any location in the list is within a given radius from the center location.
   *
   * @param locList   List of locations to check.
   * @param centerLoc The center location to compare.
   * @param radius    The radius within which to check.
   * @return true if a location is within the radius, false otherwise.
   */
  public static boolean hasLocationInRayon(List<Location> locList, Location centerLoc, double radius) {
    for (Location location : locList) {
      if (centerLoc.distance(location) <= radius) return true;
    }
    return false;
  }

  /**
   * Compares two ItemStacks to see if they are identical in type and meta data.
   *
   * @param itemA The first ItemStack.
   * @param itemB The second ItemStack.
   * @return true if the items are identical, false otherwise.
   */
  public static boolean compareItem(ItemStack itemA, ItemStack itemB) {
    return itemA != null && itemB != null &&
      ((itemA.getItemMeta() == null && itemB.getItemMeta() == null) ||
        (itemA.getItemMeta() != null && itemB.getItemMeta() != null &&
          itemA.getItemMeta().getDisplayName().equals(itemB.getItemMeta().getDisplayName()))) &&
      itemA.getType().equals(itemB.getType());
  }

  /**
   * Drops all items from an inventory at a specific location, optionally clearing the inventory afterward.
   *
   * @param inventory The inventory to drop items from.
   * @param location  The location where items will be dropped.
   * @param clearInv  Whether to clear the inventory after dropping.
   */
  public static void dropInventory(Inventory inventory, Location location, boolean clearInv) {
    for (ItemStack item : inventory.getContents()) {
      if (item != null) location.getWorld().dropItem(location, item);
    }
    if (clearInv) inventory.clear();
  }

  /**
   * Copies the contents of one inventory to another. If the target inventory is full,
   * remaining items are dropped at the specified location.
   *
   * @param invOrigin   The origin inventory.
   * @param invGoal     The target inventory.
   * @param outLocation The location where excess items will be dropped.
   */
  public static void copyInventory(Inventory invOrigin, Inventory invGoal, Location outLocation) {
    ItemStack[] items = invOrigin.getContents();
    for (ItemStack item : items) {
      if (item != null) {
        if (invGoal.firstEmpty() == -1) outLocation.getWorld().dropItemNaturally(outLocation, item);
        else invGoal.addItem(item);
      }
    }
  }

  /**
   * Adjusts the player's location to make sure they are standing on solid ground,
   * considering special block types like stairs and slabs.
   *
   * @param loc The player's current location.
   * @return The adjusted location at ground level.
   */
  public static Location getLocationToGround(Location loc) {
    Location location = loc.getBlock().getLocation().clone();
    boolean isOnGround = false;

    while (!isOnGround) {
      location.add(0, -1, 0);
      Block block = location.getBlock();
      Material blockType = block.getType();

      if (blockType.isSolid()) {
        isOnGround = true;
        location.setX((double) Math.round(loc.getX() * 10000.0) / 10000.0);
        location.setZ((double) Math.round(loc.getZ() * 10000.0) / 10000.0);
        adjustLocationForSpecialBlocks(location, loc);
        location.setPitch(loc.getPitch());
        location.setYaw(loc.getYaw());
        return location;
      }
    }
    return null;
  }

  /**
   * Adjusts the location based on specific blocks like slabs, stairs, etc.
   *
   * @param location       The current location to adjust.
   * @param playerLocation The player's current location.
   */
  private static void adjustLocationForSpecialBlocks(Location location, Location playerLocation) {
    Block block = location.getBlock();
    Material blockType = block.getType();

    if (blockType == Material.SNOW) {
      Snow snow = (Snow) block.getBlockData();
      double height = snow.getLayers() / 8.0;
      location.add(0, height, 0);
    }
    else if (blockType.name().contains("FENCE") || blockType.name().contains("WALL")) location.add(0, 1.5, 0);
    else location.add(0, 1, 0);

  }

  // Add more relevant utility methods for Minecraft functionality.
}
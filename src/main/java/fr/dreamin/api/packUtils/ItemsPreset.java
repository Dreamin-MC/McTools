package fr.dreamin.api.packUtils;

import fr.dreamin.api.colors.CustomChatColor;
import fr.dreamin.api.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemsPreset {

  itemConfig(new ItemBuilder(Material.NAME_TAG).setName(CustomChatColor.YELLOW.getColorWithText("Configuration")).setCustomModelData(2).toItemStack()),
  itemDash(new ItemBuilder(Material.FEATHER).setName(CustomChatColor.YELLOW.getColorWithText("Dash")).setCustomModelData(2).toItemStack()),

  //Arrow
  arrowDown(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Down")).setCustomModelData(1).toItemStack()),
  arrowUp(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Up")).setCustomModelData(2).toItemStack()),
  arrowLeft(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Left")).setCustomModelData(3).toItemStack()),
  arrowRight(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Right")).setCustomModelData(4).toItemStack()),
  arrowPrevious(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Previous")).setCustomModelData(5).toItemStack()),
  arrowNext(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Next")).setCustomModelData(6).toItemStack()),
  arrowBackWard(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Backward")).setCustomModelData(7).toItemStack()),
  arrowForWard(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Forward")).setCustomModelData(8).toItemStack());

  private ItemStack item;

  ItemsPreset(ItemStack item) {
    this.item = item;
  }

  public ItemStack getItem() {
    return item;
  }

}

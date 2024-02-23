package fr.dreamin.mctools.api.packUtils;

import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemsPreset {

  itemConfig(new ItemBuilder(Material.NAME_TAG).setName(CustomChatColor.YELLOW.getColorWithText("Configuration")).setCustomMeta(2).toItemStack()),
  itemDash(new ItemBuilder(Material.FEATHER).setName(CustomChatColor.YELLOW.getColorWithText("Dash")).setCustomMeta(2).toItemStack()),

  //Arrow
  arrowDown(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Down")).setCustomMeta(1).toItemStack()),
  arrowUp(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Up")).setCustomMeta(2).toItemStack()),
  arrowLeft(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Left")).setCustomMeta(3).toItemStack()),
  arrowRight(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Right")).setCustomMeta(4).toItemStack()),
  arrowPrevious(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Previous")).setCustomMeta(5).toItemStack()),
  arrowNext(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Next")).setCustomMeta(6).toItemStack()),
  arrowBackWard(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Backward")).setCustomMeta(7).toItemStack()),
  arrowForWard(new ItemBuilder(Material.ARROW).setName(CustomChatColor.YELLOW.getColorWithText("Forward")).setCustomMeta(8).toItemStack());

  private ItemStack item;

  ItemsPreset(ItemStack item) {
    this.item = item;
  }

  public ItemStack getItem() {
    return item;
  }

}

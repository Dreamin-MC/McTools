package fr.dreamin.api.items;


import fr.dreamin.mctools.McTools;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;
import xyz.xenondevs.invui.item.Item;

import java.util.*;
import java.util.function.Consumer;

public class ItemBuilder {

  private ItemStack is;

  /**
   * Constructs an ItemBuilder with a specified Material and a default amount of 1.
   *
   * @param m The material for the item.
   */
  public ItemBuilder(Material m) {
    this(m, 1);
  }

  /**
   * Constructs an ItemBuilder with a specified Material and amount.
   *
   * @param m The material for the item.
   * @param amount The amount of the item in the stack.
   */
  public ItemBuilder(Material m, int amount) {
    this.is = new ItemStack(m, amount);
  }

  /**
   * Constructs an ItemBuilder from an existing ItemStack.
   * The item meta is cloned to ensure it doesn't modify the original ItemStack.
   *
   * @param is The existing ItemStack to copy.
   */
  public ItemBuilder(ItemStack is) {
    this.is = is.clone(); // Ensures we are working with a copy.
    ItemMeta meta = is.getItemMeta();
    if (meta != null && is.hasItemMeta()) {
      this.is.setItemMeta(meta.clone()); // Clone the item metadata.
    }
  }

  /**
   * Sets the durability (damage) of the item.
   *
   * @param durability The durability value (number of uses left).
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setDurability(int durability) {
    if (is.getItemMeta() instanceof Damageable damageable) {
      damageable.setDamage(durability);
      is.setItemMeta(damageable);
    }
    return this;
  }

  /**
   * Sets the number of items in the stack.
   *
   * @param amount The number of items in the stack.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setAmount(int amount) {
    is.setAmount(amount);
    return this;
  }

  /**
   * Sets the maximum stack size for this item.
   *
   * @param amount The maximum amount the stack can hold.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setMaxStack(Integer amount) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setMaxStackSize(amount);
    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Sets the rarity of the item.
   *
   * @param rarity The rarity of the item.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setRarity(ItemRarity rarity) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setRarity(rarity);
    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Configures the item as a food item with specified properties and a converted item after consumption.
   *
   * @param nutrition The nutritional value of the food.
   * @param saturation The saturation level when consumed.
   * @param eatSeconds The time it takes to eat.
   * @param canAlwaysEat Whether the player can always eat this item.
   * @param convertAfterEat The item that the food converts to after being eaten.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setFood(int nutrition, float saturation, float eatSeconds, boolean canAlwaysEat, ItemStack convertAfterEat) {
    ItemMeta itemMeta = is.getItemMeta();

    FoodComponent foodComponent = new ItemStack(Material.APPLE).getItemMeta().getFood();
    foodComponent.setNutrition(nutrition);
    foodComponent.setSaturation(saturation);
    foodComponent.setCanAlwaysEat(canAlwaysEat);
    foodComponent.setEatSeconds(eatSeconds);
    foodComponent.setUsingConvertsTo(convertAfterEat);
    itemMeta.setFood(foodComponent);

    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Configures the item as a food item with effects after consumption.
   *
   * @param nutrition The nutritional value of the food.
   * @param saturation The saturation level when consumed.
   * @param eatSeconds The time it takes to eat.
   * @param canAlwaysEat Whether the player can always eat this item.
   * @param foodEffects The list of effects applied when the food is consumed.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setFood(int nutrition, float saturation, float eatSeconds, boolean canAlwaysEat, List<FoodComponent.FoodEffect> foodEffects) {
    ItemMeta itemMeta = is.getItemMeta();

    FoodComponent foodComponent = new ItemStack(Material.APPLE).getItemMeta().getFood();
    foodComponent.setNutrition(nutrition);
    foodComponent.setSaturation(saturation);
    foodComponent.setCanAlwaysEat(canAlwaysEat);
    foodComponent.setEatSeconds(eatSeconds);
    foodComponent.setEffects(foodEffects);
    itemMeta.setFood(foodComponent);

    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Configures the item as a food item with effects and a conversion item after consumption.
   *
   * @param nutrition The nutritional value of the food.
   * @param saturation The saturation level when consumed.
   * @param eatSeconds The time it takes to eat.
   * @param canAlwaysEat Whether the player can always eat this item.
   * @param convertAfterEat The item that the food converts to after being eaten.
   * @param foodEffects The list of effects applied when the food is consumed.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setFood(int nutrition, float saturation, float eatSeconds, boolean canAlwaysEat, ItemStack convertAfterEat, List<FoodComponent.FoodEffect> foodEffects) {
    ItemMeta itemMeta = is.getItemMeta();

    FoodComponent foodComponent = new ItemStack(Material.APPLE).getItemMeta().getFood();
    foodComponent.setNutrition(nutrition);
    foodComponent.setSaturation(saturation);
    foodComponent.setCanAlwaysEat(canAlwaysEat);
    foodComponent.setEatSeconds(eatSeconds);
    foodComponent.setUsingConvertsTo(convertAfterEat);
    foodComponent.setEffects(foodEffects);
    itemMeta.setFood(foodComponent);

    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Enables or disables the enchantment glint (glowing effect) on the item.
   *
   * @param b True to enable the glint, false to disable it.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setEnchantGlint(boolean b) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setEnchantmentGlintOverride(b);
    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Hides or shows tooltips that describe the item.
   *
   * @param b True to hide the tooltips, false to show them.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setHideToolType(boolean b) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setHideTooltip(b);
    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Adds an item flag to hide specific information like enchantments or attributes.
   *
   * @param itemFlag The flag to hide certain attributes.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setItemFlag(ItemFlag itemFlag) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.addItemFlags(itemFlag);
    is.setItemMeta(itemMeta);
    return this;
  }

  /**
   * Sets the player head's skin based on the player's name.
   *
   * @param player The player's name.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setPlayerHFromName(String player) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithName(is, player);
    return this;
  }

  /**
   * Sets the player head's skin based on the player's UUID.
   *
   * @param uuid The player's UUID.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setPlayerHFromUuid(UUID uuid) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithUuid(is, uuid);
    return this;
  }

  /**
   * Sets the player head's skin using a URL to the skin texture.
   *
   * @param url The URL of the skin texture.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setPlayerHFromUrl(String url) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithUrl(is, url);
    return this;
  }

  /**
   * Sets the player head's skin using a Base64 encoded string representing the skin texture.
   *
   * @param base64 The Base64 encoded skin texture.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setPlayerHFromBase64(String base64) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithBase64(is, base64);
    return this;
  }

  /**
   * Makes the item unbreakable, meaning it will not lose durability when used.
   *
   * @param unbreakable True to make the item unbreakable, false otherwise.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setUnbreakable(boolean unbreakable) {
    ItemMeta meta = is.getItemMeta();
    if (meta != null) {
      meta.setUnbreakable(unbreakable);
      is.setItemMeta(meta);
    }
    return this;
  }

  /**
   * Adds a custom tag to the item's PersistentDataContainer.
   *
   * @param nameSpace The namespace of the tag (usually your plugin's name).
   * @param tagName The name of the tag.
   * @param type The type of the tag (e.g., STRING, INTEGER).
   * @param value The value of the tag to store.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setCustomTag(String nameSpace, String tagName, PersistentDataType type, Object value) {
    ItemMeta im = is.getItemMeta();
    NamespacedKey key = new NamespacedKey(nameSpace, tagName);
    im.getPersistentDataContainer().set(key, type, value);
    is.setItemMeta(im);
    return this;
  }

  /**
   * Adds an enchantment to the item with a specified level.
   *
   * @param ench The enchantment to add.
   * @param level The level of the enchantment.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setEnchant(Enchantment ench, int level) {
    ItemMeta im = is.getItemMeta();
    im.addEnchant(ench, level, true);
    is.setItemMeta(im);
    return this;
  }

  /**
   * Sets the display name of the item.
   *
   * @param name The display name to set (supports color codes).
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setName(String name) {
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(name);
    is.setItemMeta(im);
    return this;
  }

  /**
   * Sets the custom model data for this item.
   * Custom model data allows for custom textures in resource packs.
   *
   * @param data The custom model data value.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setCustomModelData(int data) {
    ItemMeta meta = is.getItemMeta();
    if (meta != null) {
      meta.setCustomModelData(data);
      is.setItemMeta(meta);
    }
    return this;
  }

  /**
   * Adds lore to the item, appending new lines instead of replacing the existing lore.
   *
   * @param lines The lines of lore to append.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder addLore(String... lines) {
    ItemMeta meta = is.getItemMeta();
    if (meta != null) {
      List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
      lore.addAll(Arrays.asList(lines));
      meta.setLore(lore);
      is.setItemMeta(meta);
    }
    return this;
  }

  /**
   * Returns the custom model data of the item, if present.
   *
   * @return An Optional containing the custom model data, or empty if not set.
   */
  public Optional<Integer> getCustomModelData() {
    ItemMeta meta = is.getItemMeta();
    return meta != null && meta.hasCustomModelData() ? Optional.of(meta.getCustomModelData()) : Optional.empty();
  }

  /**
   * Sets the wool color for an item made of wool (deprecated method).
   *
   * @param color The dye color to apply to the wool.
   * @return The ItemBuilder instance for method chaining.
   */
  @SuppressWarnings("deprecation")
  public ItemBuilder setWoolColor(DyeColor color) {
    if (!is.getType().equals(Material.WHITE_WOOL)) return this;
    this.is.setDurability(color.getWoolData());
    return this;
  }

  /**
   * Sets the color of leather armor.
   *
   * @param color The color to apply to the leather armor.
   * @return The ItemBuilder instance for method chaining.
   */
  public ItemBuilder setLeatherArmorColor(Color color) {
    try {
      LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
      im.setColor(color);
      is.setItemMeta(im);
    } catch (ClassCastException expected) {
      return this;
    }
    return this;
  }

  /**
   * Converts the ItemBuilder back into an ItemStack for use in Minecraft.
   *
   * @return The constructed ItemStack.
   */
  public ItemStack toItemStack() {
    return is.clone(); // Return a clone to prevent external modifications.
  }

  /**
   * Creates a deep clone of the ItemBuilder, applying modifications during the cloning process.
   *
   * @param modifier A Consumer that allows applying modifications to the cloned ItemBuilder.
   * @return The modified clone of the ItemBuilder.
   */
  public ItemBuilder deepCloneWithModifications(Consumer<ItemBuilder> modifier) {
    ItemBuilder clone = this.clone();
    modifier.accept(clone);
    return clone;
  }

  /**
   * Clones the ItemBuilder to create a duplicate instance with the same ItemStack.
   *
   * @return A new ItemBuilder with a cloned ItemStack.
   */
  public ItemBuilder clone() {
    return new ItemBuilder(this.is);
  }

  /**
   * Converts this ItemBuilder to an InvUI item, useful for GUIs.
   *
   * @return The GUI-compatible item from InvUI.
   */
  public xyz.xenondevs.invui.item.builder.ItemBuilder toGuiItem() {
    return new xyz.xenondevs.invui.item.builder.ItemBuilder(this.toItemStack());
  }
}
package fr.dreamin.api.items;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * A builder class for creating custom potions with various effects, colors, and use types.
 */
public class PotionBuilder {

  /**
   * Record representing a potion effect with its type, duration, amplifier, and ambient status.
   */
  public record PotionEffectClass(PotionEffectType potionEffectType, int duration, int amplifier, boolean ambient) {}

  private ItemStack potion;
  private PotionUseType potionUseType;

  //-----------------CONSTRUCTORS-----------------

  /**
   * Constructs a default potion of type SELF with no effects.
   */
  public PotionBuilder() {
    this.potionUseType = PotionUseType.SELF;
    this.potion = new ItemStack(Material.POTION);
  }

  /**
   * Constructs a potion of the specified type.
   *
   * @param potionUseType The type of the potion (SELF, SPLASH, or LINGERING).
   */
  public PotionBuilder(final PotionUseType potionUseType) {
    this.potionUseType = potionUseType;
    this.potion = new ItemStack(potionUseType.getMaterial());
  }

  /**
   * Constructs a potion with a single potion effect.
   *
   * @param potionEffectClass The potion effect to add.
   */
  public PotionBuilder(final PotionEffectClass potionEffectClass) {
    this.potionUseType = PotionUseType.SELF;
    this.potion = new ItemStack(Material.POTION);
    addPotionEffect(potionEffectClass);
  }

  /**
   * Constructs a potion with a list of potion effects.
   *
   * @param potionEffectClassList The list of potion effects to add.
   */
  public PotionBuilder(final List<PotionEffectClass> potionEffectClassList) {
    this.potionUseType = PotionUseType.SELF;
    this.potion = new ItemStack(Material.POTION);
    addPotionEffectList(potionEffectClassList);
  }

  /**
   * Copy constructor to clone an existing PotionBuilder.
   *
   * @param potionBuilder The existing PotionBuilder to copy.
   */
  public PotionBuilder(final PotionBuilder potionBuilder) {
    this.potionUseType = potionBuilder.getPotionUseType();
    this.potion = new ItemStack(potionBuilder.toItemStack());
  }

  //-----------------ADD POTION EFFECT-----------------

  /**
   * Adds a potion effect to the current potion.
   *
   * @param type The type of potion effect.
   * @param duration The duration of the effect.
   * @param amplifier The strength of the effect.
   * @param ambient Whether the effect is ambient.
   * @return The PotionBuilder instance for method chaining.
   */
  public PotionBuilder addPotionEffect(final PotionEffectType type, final int duration, final int amplifier, final boolean ambient) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(type, duration, amplifier, ambient), true);
    this.potion.setItemMeta(potionMeta);
    return this;
  }

  /**
   * Adds a potion effect using a PotionEffectClass record.
   *
   * @param potionEffectClass The potion effect class containing the effect details.
   * @return The PotionBuilder instance for method chaining.
   */
  public PotionBuilder addPotionEffect(final PotionEffectClass potionEffectClass) {
    return addPotionEffect(potionEffectClass.potionEffectType(), potionEffectClass.duration(), potionEffectClass.amplifier(), potionEffectClass.ambient());
  }

  /**
   * Adds a list of potion effects to the current potion.
   *
   * @param potionEffectClassList The list of potion effects to add.
   * @return The PotionBuilder instance for method chaining.
   */
  public PotionBuilder addPotionEffectList(final List<PotionEffectClass> potionEffectClassList) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionEffectClassList.forEach(potionEffectClass -> potionMeta.addCustomEffect(
      new PotionEffect(potionEffectClass.potionEffectType(), potionEffectClass.duration(), potionEffectClass.amplifier(), potionEffectClass.ambient()), true));
    this.potion.setItemMeta(potionMeta);
    return this;
  }

  /**
   * Removes a potion effect from the current potion.
   *
   * @param effectType The type of potion effect to remove.
   * @return The PotionBuilder instance for method chaining.
   */
  public PotionBuilder removePotionEffect(final PotionEffectType effectType) {
    PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
    potionMeta.removeCustomEffect(effectType);
    potion.setItemMeta(potionMeta);
    return this;
  }

  //-----------------SETTER-----------------

  /**
   * Sets the name of the potion.
   *
   * @param name The custom name to set for the potion.
   * @return The PotionBuilder instance for method chaining.
   */
  public PotionBuilder setName(final String name) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionMeta.setDisplayName(name);
    this.potion.setItemMeta(potionMeta);
    return this;
  }

  /**
   * Sets the color of the potion.
   *
   * @param color The custom color to set for the potion.
   * @return The PotionBuilder instance for method chaining.
   */
  public PotionBuilder setColor(final Color color) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionMeta.setColor(color);
    this.potion.setItemMeta(potionMeta);
    return this;
  }

  /**
   * Sets the type of the potion (SELF, SPLASH, LINGERING).
   *
   * @param type The potion use type to set.
   * @return The PotionBuilder instance for method chaining.
   */
  public PotionBuilder setPotionUseType(final PotionUseType type) {
    this.potionUseType = type;
    this.potion.setType(type.getMaterial());
    return this;
  }

  /**
   * Sets the custom model data for this item.
   * Custom model data allows for custom textures in resource packs.
   *
   * @param data The custom model data value.
   * @return The ItemBuilder instance for method chaining.
   */
  public PotionBuilder setCustomModelData(int data) {
    ItemMeta meta = this.potion.getItemMeta();
    if (meta != null) {
      meta.setCustomModelData(data);
      this.potion.setItemMeta(meta);
    }
    return this;
  }

  //-----------------GETTER-----------------

  /**
   * Returns the ItemStack representation of the current potion.
   *
   * @return The ItemStack of the current potion.
   */
  public ItemStack toItemStack() {
    return this.potion;
  }

  /**
   * Converts this ItemBuilder to an InvUI item, useful for GUIs.
   *
   * @return The GUI-compatible item from InvUI.
   */
  public xyz.xenondevs.invui.item.builder.ItemBuilder toGuiItem() {
    return new xyz.xenondevs.invui.item.builder.ItemBuilder(this.toItemStack());
  }

  /**
   * Returns the current potion use type (SELF, SPLASH, LINGERING).
   *
   * @return The PotionUseType of the current potion.
   */
  private PotionUseType getPotionUseType() {
    return potionUseType;
  }

  //-----------------FUNCTION-----------------

  /**
   * Creates a clone of the current PotionBuilder.
   *
   * @return A new PotionBuilder that is a copy of this instance.
   */
  @Override
  public PotionBuilder clone() {
    return new PotionBuilder(this);
  }
}
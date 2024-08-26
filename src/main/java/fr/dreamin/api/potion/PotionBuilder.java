package fr.dreamin.api.potion;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PotionBuilder {

  public record PotionEffectClass(PotionEffectType potionEffectType, int duration, int amplifier, boolean ambient) {}

  private ItemStack potion;
  private PotionUseType potionUseType;

  //-----------------CONSTRUCTORS-----------------
  public PotionBuilder() {
    this.potionUseType = PotionUseType.SELF;
    this.potion = new ItemStack(Material.POTION);
  }

  public PotionBuilder(PotionUseType potionUseType) {
    this.potionUseType = potionUseType;
    this.potion = new ItemStack(potionUseType.getMaterial());
  }

  public PotionBuilder(PotionEffectClass PotionEffectClass) {
    this.potionUseType = PotionUseType.SELF;
    this.potion = new ItemStack(Material.POTION);
    addPotionEffect(PotionEffectClass);
  }

  public PotionBuilder(List<PotionEffectClass> PotionEffectClassList) {
    this.potionUseType = PotionUseType.SELF;
    this.potion = new ItemStack(Material.POTION);
    addPotionEffectList(PotionEffectClassList);
  }

  public PotionBuilder(PotionBuilder potionBuilder) {
    this.potionUseType = potionBuilder.getPotionUseType();
    this.potion = new ItemStack(potionBuilder.toItemStack());
  }

  //-----------------ADD POTION EFFECT-----------------
  public PotionBuilder addPotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(type, duration, amplifier, ambient), true);
    this.potion.setItemMeta(potionMeta);
    return this;
  }
  public PotionBuilder addPotionEffect(PotionEffectClass potionEffectClass) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(potionEffectClass.potionEffectType(), potionEffectClass.duration(), potionEffectClass.amplifier(), potionEffectClass.ambient()), true);
    this.potion.setItemMeta(potionMeta);
    return this;
  }
  public PotionBuilder addPotionEffectList(List<PotionEffectClass> potionEffectClassList) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionEffectClassList.forEach(potionEffectClass -> potionMeta.addCustomEffect(new PotionEffect(potionEffectClass.potionEffectType(), potionEffectClass.duration(), potionEffectClass.amplifier(), potionEffectClass.ambient()), true));
    this.potion.setItemMeta(potionMeta);
    return this;
  }
  public PotionBuilder removePotionEffect(PotionEffectType effectType) {
    PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
    potionMeta.removeCustomEffect(effectType);
    potion.setItemMeta(potionMeta);
    return this;
  }

  //-----------------SETTER-----------------
  public PotionBuilder setName(String name) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionMeta.setDisplayName(name);
    this.potion.setItemMeta(potionMeta);
    return this;
  }

  public PotionBuilder setColor(Color color) {
    PotionMeta potionMeta = (PotionMeta) this.potion.getItemMeta();
    potionMeta.setColor(color);
    this.potion.setItemMeta(potionMeta);
    return this;
  }

  public PotionBuilder setPotionUseType(PotionUseType type) {
    this.potionUseType = type;
    this.potion.setType(type.getMaterial());
    return this;
  }

  //-----------------GETTER-----------------
  public ItemStack toItemStack() {
    return this.potion;
  }
  private PotionUseType getPotionUseType() {
    return potionUseType;
  }

  //-----------------FUNCTION-----------------
  public PotionBuilder clone() {
    return new PotionBuilder(this);
  }

  //-----------------POTIONUSETYPE CLASS-----------------
  public enum PotionUseType {
    SELF(Material.POTION),
    SPLASH(Material.SPLASH_POTION),
    LINGERING(Material.LINGERING_POTION);

    private Material material;

    PotionUseType(Material material) {
      this.material = material;
    }

    public Material getMaterial() {
      return material;
    }
  }
}
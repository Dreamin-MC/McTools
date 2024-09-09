package fr.dreamin.api.items;


import fr.dreamin.mctools.McTools;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
  private ItemStack is;

  public ItemBuilder(Material m) {
    this(m, 1);
  }

  public ItemBuilder(ItemStack is) {
    this.is = new ItemStack(is);

    if (is.getLore() != null && !is.getLore().isEmpty()) this.is.setLore(is.getLore());
  }

  public ItemBuilder(Material m, int amount) {
    is = new ItemStack(m, amount);
  }

  public ItemBuilder(Material m, int amount, short meta){
    is = new ItemStack(m, amount, meta);
  }

  public ItemBuilder clone() {
    return new ItemBuilder(is);
  }

  public ItemBuilder setDurability(short dur) {
    is.setDurability(dur);
    return this;
  }

  public ItemBuilder setAmount(Integer amount) {
    is.setAmount(amount);
    return this;
  }

  public ItemBuilder setMaxStack(Integer amount) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setMaxStackSize(amount);
    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setRarity(ItemRarity rarity) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setRarity(rarity);
    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setFood(int nutrition, float saturation, float eatsec, boolean canAlwaysEat) {
    ItemMeta itemMeta = is.getItemMeta();

    FoodComponent foodComponent = new ItemStack(Material.APPLE).getItemMeta().getFood();
    foodComponent.setNutrition(nutrition);
    foodComponent.setSaturation(saturation);
    foodComponent.setCanAlwaysEat(canAlwaysEat);
    foodComponent.setEatSeconds(eatsec);
    itemMeta.setFood(foodComponent);

    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setFood(int nutrition, float saturation, float eatsec, boolean canAlwaysEat, ItemStack convertAfterEat) {
    ItemMeta itemMeta = is.getItemMeta();

    FoodComponent foodComponent = new ItemStack(Material.APPLE).getItemMeta().getFood();
    foodComponent.setNutrition(nutrition);
    foodComponent.setSaturation(saturation);
    foodComponent.setCanAlwaysEat(canAlwaysEat);
    foodComponent.setEatSeconds(eatsec);
    foodComponent.setUsingConvertsTo(convertAfterEat);
    itemMeta.setFood(foodComponent);

    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setFood(int nutrition, float saturation, float eatsec, boolean canAlwaysEat, List<FoodComponent.FoodEffect> foodEffects) {
    ItemMeta itemMeta = is.getItemMeta();

    FoodComponent foodComponent = new ItemStack(Material.APPLE).getItemMeta().getFood();
    foodComponent.setNutrition(nutrition);
    foodComponent.setSaturation(saturation);
    foodComponent.setCanAlwaysEat(canAlwaysEat);
    foodComponent.setEatSeconds(eatsec);
    foodComponent.setEffects(foodEffects);
    itemMeta.setFood(foodComponent);

    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setFood(int nutrition, float saturation, float eatsec, boolean canAlwaysEat, ItemStack convertAfterEat, List<FoodComponent.FoodEffect> foodEffects) {
    ItemMeta itemMeta = is.getItemMeta();

    FoodComponent foodComponent = new ItemStack(Material.APPLE).getItemMeta().getFood();
    foodComponent.setNutrition(nutrition);
    foodComponent.setSaturation(saturation);
    foodComponent.setCanAlwaysEat(canAlwaysEat);
    foodComponent.setEatSeconds(eatsec);
    foodComponent.setUsingConvertsTo(convertAfterEat);
    foodComponent.setEffects(foodEffects);
    itemMeta.setFood(foodComponent);

    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setEnchantGlint(boolean b) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setEnchantmentGlintOverride(b);
    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setHideToolType(boolean b) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.setHideTooltip(b);
    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setItemFlag(ItemFlag itemFlag) {
    ItemMeta itemMeta = is.getItemMeta();
    itemMeta.addItemFlags(itemFlag);
    is.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setPlayerHFromName(String player) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithName(is, player);
    return this;
  }

  public ItemBuilder setPlayerHFromUuid(UUID uuid) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithUuid(is, uuid);
    return this;
  }

  public ItemBuilder setPlayerHFromUrl(String url) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithUrl(is, url);
    return this;
  }

  public ItemBuilder setPlayerHFromBase64(String base64) {
    is.setType(Material.PLAYER_HEAD);
    is = SkullCreator.itemWithBase64(is, base64);
    return this;
  }


  public ItemBuilder setUnbreakable(boolean b) {
    ItemMeta im = is.getItemMeta();
    im.setUnbreakable(b);
    return this;
  }

  public ItemBuilder setCustomTag(String tagName, PersistentDataType type, Object value) {
    ItemMeta im = is.getItemMeta();
    NamespacedKey key = new NamespacedKey(McTools.getInstance(), tagName);
    im.getPersistentDataContainer().set(key, type, value);
    is.setItemMeta(im);
    return this;
  }

  //setEnchantments
  public ItemBuilder setEnchant(Enchantment ench, int level) {
    ItemMeta im = is.getItemMeta();
    im.addEnchant(ench, level, true);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setName(String name) {
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(name);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setPotionColor(Color color) {
    PotionMeta meta = (PotionMeta) is.getItemMeta();
    meta.setColor(color);
    is.setItemMeta(meta);
    return this;
  }

  public ItemBuilder setCustomMeta(Integer idMeta) {
    ItemMeta im = is.getItemMeta();
    im.setCustomModelData(idMeta);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setType(Material material) {
    is.setType(material);
    return this;
  }

  public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
    is.addUnsafeEnchantment(ench, level);
    return this;
  }

  public ItemBuilder removeEnchantment(Enchantment ench) {
    is.removeEnchantment(ench);
    return this;
  }

  public ItemBuilder setSkullOwner(String owner) {
    try {
      SkullMeta im = (SkullMeta) is.getItemMeta();
      im.setOwner(owner);
      is.setItemMeta(im);
    } catch (ClassCastException expected) {
      return this;
    }
    return this;
  }

  public ItemBuilder addEnchant(Enchantment ench, int level) {
    ItemMeta im = is.getItemMeta();
    im.addEnchant(ench, level, true);
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setDurability(Short s) {
    ItemMeta im = is.getItemMeta();
    is.setDurability(s);
    return this;
  }

  public ItemBuilder setLore(String... lore) {
    ItemMeta im = is.getItemMeta();
    im.setLore(Arrays.asList(lore));
    is.setItemMeta(im);
    return this;
  }

  public ItemBuilder setLore(List<String> lore) {
    ItemMeta im = is.getItemMeta();
    im.setLore(lore);
    is.setItemMeta(im);
    return this;
  }

  @SuppressWarnings("deprecation")
  public ItemBuilder setWoolColor(DyeColor color) {
    if (!is.getType().equals(Material.WHITE_WOOL)) return this;
    this.is.setDurability(color.getWoolData());
    return this;
  }

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

  public ItemStack toItemStack() {
    return is;
  }
}

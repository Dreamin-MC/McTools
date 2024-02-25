package fr.dreamin.mctools.components.game.manager;

import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.build.Tag;
import fr.dreamin.mctools.components.build.TagCategory;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.mysql.fetcher.buildCategoryFetcher.BuildCategoryFetcher;
import fr.dreamin.mctools.mysql.fetcher.buildTagFetcher.BuildTagFetcher;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuildManager {

  private static List<Tag> tags = new ArrayList<>();
  private static List<TagCategory> tagCategorys = new ArrayList<>();

  public static List<Tag> getTags() {
    return tags;
  }

  public static List<TagCategory> getTagCategorys() {
    return tagCategorys;
  }

  public static void newTag(String key, String value, int category) {
    tags.add(new Tag(key, value, category));
    BuildTagFetcher.newTag(key, value, category);
  }

  public static void newTagCategory(String value) {
    BuildCategoryFetcher.newTagCategory(value);
    tagCategorys.add(BuildCategoryFetcher.getCategory(value));
  }



  public static TagCategory getTagCategory(int id) {
    for (TagCategory tagCategory : tagCategorys) {
      if (tagCategory.getId() == id) {
        return tagCategory;
      }
    }
    return null;
  }

  public static TagCategory getTagCategory(String value) {
    for (TagCategory tagCategory : tagCategorys) {
      if (tagCategory.getValue().equals(value)) {
        return tagCategory;
      }
    }
    return null;
  }

  public static List<ItemStack> getTagItemStacks(MTPlayer MTPlayer) {

    List<ItemStack> itemStacks = new ArrayList<>();

    for (Tag tag : getTagsByCategory(MTPlayer.getBuildManager().getTagCategory())) {
      itemStacks.add(new ItemBuilder(Material.PAPER).setName(tag.getValue()).toItemStack());
    }
    return itemStacks;
  }

  public static List<Tag> getTagsByCategory(int tagCategory) {

    List<Tag> tags = new ArrayList<>();

    for (Tag tag : BuildManager.getTags()) {
      if (tag.getTagCategory().getId() == tagCategory) {
        tags.add(tag);
      }
    }
    return tags;
  }

  public static List<Tag> getTagsByCategory(TagCategory tagCategory) {

    List<Tag> tags = new ArrayList<>();

    for (Tag tag : BuildManager.getTags()) {
      if (tag.getTagCategory().getId() == tagCategory.getId()) {
        tags.add(tag);
      }
    }
    return tags;
  }

  public static Tag getTag(String key, String value) {
    for (Tag tag : tags) {
      if (tag.getKey().equals(key) && tag.getValue().equals(value)) {
        return tag;
      }
    }
    return null;
  }

  public static List<ItemStack> getTagCategoryItemStacks() {

    List<ItemStack> itemStacks = new ArrayList<>();

    for (TagCategory tagCategory : tagCategorys) {
      itemStacks.add(new ItemBuilder(Material.NAME_TAG).setName(tagCategory.getValue()).toItemStack());
    }
    return itemStacks;
  }

}

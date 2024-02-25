package fr.dreamin.mctools.components.build;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.game.manager.BuildManager;
import org.bukkit.NamespacedKey;

public class Tag {

  private final String key;
  private final String value;
  private final TagCategory tagCategory;
  private NamespacedKey namespacedKey;

  public Tag(String key, String value, int categoryId) {
    this.key = key;
    this.value = value;
    this.tagCategory = BuildManager.getTagCategory(categoryId);
    namespacedKey = new NamespacedKey(McTools.getInstance(), key);
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public TagCategory getTagCategory() {
    return tagCategory;
  }

  public NamespacedKey getNamespacedKey() {
    return namespacedKey;
  }
}

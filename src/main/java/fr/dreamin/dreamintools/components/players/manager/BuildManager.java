package fr.dreamin.dreamintools.components.players.manager;

import fr.dreamin.dreamintools.components.build.Tag;
import fr.dreamin.dreamintools.components.build.TagCategory;

public class BuildManager {

  private TagCategory tagCategory = null;
  private Tag tag = null;

  public TagCategory getTagCategory() {
    return tagCategory;
  }

  public void setTagCategory(TagCategory tagCategory) {
    this.tagCategory = tagCategory;
  }

  public Tag getTag() {
    return tag;
  }

  public void setTag(Tag tag) {
    this.tag = tag;
  }

}

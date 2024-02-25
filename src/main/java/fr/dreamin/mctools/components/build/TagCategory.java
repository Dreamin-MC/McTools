package fr.dreamin.mctools.components.build;

public class TagCategory {

  private final int id;
  private final String value;

  public TagCategory(int id, String value) {
    this.id = id;
    this.value = value;
  }

  public int getId() {
    return id;
  }

  public String getValue() {
    return value;
  }

}

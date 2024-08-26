package fr.dreamin.api.packUtils;

public enum NumberTextures {

  GO("\uA000"),
  NUMBER1("\uA001"),
  NUMBER2("\uA002"),
  NUMBER3("\uA003"),
  NUMBER4("\uA004"),
  NUMBER5("\uA005"),
  NUMBER6("\uA006"),
  NUMBER7("\uA007"),
  NUMBER8("\uA008"),
  NUMBER9("\uA009"),
  NUMBER10("\uA010");

  private final String texture;

  NumberTextures(String texture) {
    this.texture = texture;
  }

  public String getTexture() {
    return texture;
  }

}

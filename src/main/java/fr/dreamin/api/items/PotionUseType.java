package fr.dreamin.api.items;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum PotionUseType {
  SELF(Material.POTION),
  SPLASH(Material.SPLASH_POTION),
  LINGERING(Material.LINGERING_POTION);

  private final Material material;

  PotionUseType(final Material material) {
      this.material = material;
    }
}
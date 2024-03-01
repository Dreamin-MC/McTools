package fr.dreamin.mctools.components.lang;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum Lang {

  fr_FR("Français"),
  en_US("English");

  @Getter
  private String name;

  Lang(String name) {
    this.name = name;
  }

  public static List<String> getLangs() {
    return Arrays.asList(Lang.values()).stream().map(lang -> lang.getName()).toList();
  }

  public static Lang getLangByName(String name) {
    return Arrays.asList(Lang.values()).stream().filter(lang -> lang.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
  }

  public static boolean isValidLanguage(String languageCode) {
    for (Lang lang : Lang.values()) {
      if (lang.name().equalsIgnoreCase(languageCode)) {
        return true;
      }
    }
    return false;
  }

}

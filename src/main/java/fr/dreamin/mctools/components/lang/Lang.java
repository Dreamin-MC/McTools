package fr.dreamin.mctools.components.lang;

import fr.dreamin.mctools.McTools;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lang {

  @Getter private String nameCode;
  @Getter private List<String> codes = new ArrayList<>();
  @Getter private String displayName;

  public Lang(String nameCode, String displayName, List<String> codes) {
    this.nameCode = nameCode;
    this.displayName = displayName;
    this.codes.addAll(codes);
  }


  public static boolean contains(List<Lang> langs, String nameCode) {
    for (Lang lang : langs) {
      if (lang.getNameCode().equalsIgnoreCase(nameCode)) return true;
      for (String code : lang.getCodes()) {
        if (code.equalsIgnoreCase(nameCode)) return true;
      }
    }
    return false;
  }

  public static boolean contains(String nameCode) {
    for (Lang lang : McTools.getCodex().getLangs()) {
      if (lang.getNameCode().equalsIgnoreCase(nameCode)) return true;
      for (String code : lang.getCodes()) {
        if (code.equalsIgnoreCase(nameCode)) return true;
      }
    }
    return false;
  }

  public static Lang getLang(List<Lang> langs, String nameCode) {
    for (Lang lang : langs) {
      if (lang.getNameCode().equalsIgnoreCase(nameCode)) return lang;
      for (String code : lang.getCodes()) {
        if (code.equalsIgnoreCase(nameCode)) return lang;
      }
    }
    return null;
  }

  public static Lang getLang(String nameCode) {
    for (Lang lang : McTools.getCodex().getLangs()) {
      if (lang.getNameCode().equalsIgnoreCase(nameCode)) return lang;
      for (String code : lang.getCodes()) {
        if (code.equalsIgnoreCase(nameCode)) return lang;
      }
    }

    return null;
  }

  public static Lang getLangByDisplayName(String displayName) {
    for (Lang lang : McTools.getCodex().getLangs()) {
      if (lang.getDisplayName().equalsIgnoreCase(displayName)) return lang;
    }

    return null;
  }

  public static Lang getLangByName(String nameCode) {
    for (Lang lang : McTools.getCodex().getLangs()) {
      if (lang.getNameCode().equalsIgnoreCase(nameCode)) return lang;
      for (String code : lang.getCodes()) {
        if (code.equalsIgnoreCase(nameCode)) return lang;
      }
    }

    return null;
  }

  public static boolean isValidLanguage(String nameCode) {
    return contains(nameCode);
  }

}

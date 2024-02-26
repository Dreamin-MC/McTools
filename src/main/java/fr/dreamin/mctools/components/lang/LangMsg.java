package fr.dreamin.mctools.components.lang;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LangMsg {

  //ERROR
  ERROR_CONSOLE("error.console.", "%BROADCAST% [color]RED[color] Vous ne pouvez pas exécuter cette commande dans la console :("),
  ERROR_PERM("error.perm.", "%BROADCAST% [color]RED[color]You don't have the permission to execute this command"),

  //player
  UPDATE_LANG("player.update.lang.", "%BROADCAST% [color]WHITE[color]Update your language to : [color]GOLD[color]%0%");

  @Getter @Setter
  private static YamlConfiguration config;
  private String path;
  private String dflValue;

  LangMsg(String path, String dflValue) {
    this.path = path;
    this.dflValue = dflValue;
  }

  private String getStr(String path, String dflValue) {
    return (config.getString(path, dflValue));
  }

  public String getMsg(Lang lang, String... args) {
    String msg = getStr(this.path + lang.name(), this.dflValue);
    StringBuilder result = new StringBuilder(msg);

    for (int i = 0; i < args.length; i++) {
      String placeholder = "%" + i + "%";
      int index = result.indexOf(placeholder);
      while (index != -1) {
        result.replace(index, index + placeholder.length(), args[i]);
        index = result.indexOf(placeholder, index + args[i].length());
      }
    }

    // Replace white prefix
    String prefixPlaceholder = "%PREFIX%";
    int prefixIndex = result.indexOf(prefixPlaceholder);
    if (prefixIndex != -1) result.replace(prefixIndex, prefixIndex + prefixPlaceholder.length(), McTools.getCodex().getPrefix());

    // Replace white broadcast
    String broadcastPlaceholder = "%BROADCAST%";
    int broadcastIndex = result.indexOf(broadcastPlaceholder);
    if (broadcastIndex != -1) result.replace(broadcastIndex, broadcastIndex + broadcastPlaceholder.length(), McTools.getCodex().getBroadcastprefix());

    // Compile patterns for color replacement
    Pattern namePattern = Pattern.compile("\\[color\\](\\w+)\\[color\\]");
    Pattern hexPattern = Pattern.compile("\\[color\\]#?([0-9A-Fa-f]{1,6})\\[color\\]");

    // Map of custom colors
    Map<String, ChatColor> customColors = new HashMap<>();
    for (CustomChatColor color : CustomChatColor.values()) {
      customColors.put(color.name().toUpperCase(), color.getColor());
    }

    // Replace colors
    Matcher nameMatcher = namePattern.matcher(result);
    while (nameMatcher.find()) {
      String colorName = nameMatcher.group(1);
      ChatColor color = customColors.getOrDefault(colorName.toUpperCase(), CustomChatColor.WHITE.getColor());
      result.replace(nameMatcher.start(), nameMatcher.end(), color.toString());
    }

    Matcher hexMatcher = hexPattern.matcher(result);
    while (hexMatcher.find()) {
      String hexColor = hexMatcher.group(1);
      if (!hexColor.startsWith("#"))
        hexColor = "#" + hexColor;
      ChatColor color = CustomChatColor.CUSTOM(hexColor);
      result.replace(hexMatcher.start(), hexMatcher.end(), color != null ? color.toString() : CustomChatColor.WHITE.getColor().toString());
    }

    return result.toString();
  }

}

package fr.dreamin.api.colors;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CustomChatColor {

  WHITE(ChatColor.of("#FFFFFF")),
  BLACK(ChatColor.of("#000000")),
  RED(ChatColor.of("#F9364B")),
  DARKRED(ChatColor.of("#B40B0B")),
  GRAY(ChatColor.of("#A9A6A6")),
  DARKGRAY(ChatColor.of("#555555")),
  GREEN(ChatColor.of("#37C95E")),
  DARKGREEN(ChatColor.of("#00AA00")),
  GOLD(ChatColor.of("#FFAA00")),
  AQUA(ChatColor.of("#55FFFF")),
  LIGHTPURPLE(ChatColor.of("#FF55FF")),
  PURPLE(ChatColor.of("#8A36fA")),
  DARKPURPLE(ChatColor.of("#AA00AA")),
  BLUE(ChatColor.of("#3f6CDF")),
  DARKBLUE(ChatColor.of("#0000AA")),
  YELLOW(ChatColor.of("#FFF700")),
  ORANGE(ChatColor.of("#DfA73F")),
  ITALIC(ChatColor.ITALIC),
  BOLD(ChatColor.BOLD),
  UNDERLINE(ChatColor.UNDERLINE),
  STRIKETHROUGH(ChatColor.STRIKETHROUGH),
  MAGIC(ChatColor.MAGIC),
  RESET(ChatColor.RESET),
  ERROR(ChatColor.of("#B82D17"));

  private final ChatColor color;

  CustomChatColor(ChatColor color) {
    this.color = color;
  }

  public ChatColor getColor() {
    return this.color;
  }

  public String getColorWithText(Object text) {
    return this.color + text.toString();
  }

  public int getRGB() {
    return this.color.getColor().getRGB();
  }

  // Utility method for custom colors
  public static ChatColor getCustomColor(String hexa) {
    return ChatColor.of(hexa);
  }

  // Utility method to replace color placeholders in messages
  public static String getMsg(String msg, String... args) {
    // Replace %n placeholders with args
    for (int i = 0; i < args.length; i++) {
      msg = msg.replace("%" + i, args[i]);
    }

    // Compile regex once for better performance
    final Pattern namePattern = Pattern.compile("\\[color\\](\\w+)\\[color\\]");
    final Pattern hexPattern = Pattern.compile("\\[color\\]#?([0-9A-Fa-f]{6})\\[color\\]");

    // Replace named colors
    Matcher nameMatcher = namePattern.matcher(msg);
    while (nameMatcher.find()) {
      String colorName = nameMatcher.group(1).toUpperCase();
      try {
        CustomChatColor customColor = CustomChatColor.valueOf(colorName);
        msg = msg.replaceFirst("\\[color\\]" + colorName + "\\[color\\]", customColor.getColor().toString());
      } catch (IllegalArgumentException e) {
        msg = msg.replaceFirst("\\[color\\]" + colorName + "\\[color\\]", CustomChatColor.ERROR.getColor().toString());
      }
    }

    // Replace hex colors
    Matcher hexMatcher = hexPattern.matcher(msg);
    while (hexMatcher.find()) {
      String hexColor = "#" + hexMatcher.group(1);
      ChatColor color = getCustomColor(hexColor);
      msg = msg.replaceFirst("\\[color\\]" + hexColor + "\\[color\\]", color != null ? color.toString() : CustomChatColor.ERROR.getColor().toString());
    }

    return msg;
  }
}
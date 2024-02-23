package fr.dreamin.mctools.api.colors;

import net.md_5.bungee.api.ChatColor;

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
  LIGTHPURPLE(ChatColor.of("#FF55FF")),
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
    return this.color + String.valueOf(text);
  }

  public static String BOLD() {
    return "§l";
  }

  public static ChatColor CUSTOM(String hexa) {
    return ChatColor.of(hexa);
  }

  public static String CUSTOM_WITHTEXT(Object text, String hexa) {
    return ChatColor.of(hexa) + String.valueOf(text);
  }

}

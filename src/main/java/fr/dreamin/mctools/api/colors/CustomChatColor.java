package fr.dreamin.mctools.api.colors;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.lang.Lang;
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

  public static String getMsg(String msg, String... args) {

    for (int i = 0; i < args.length; i++) {
      if (msg.contains("%" + i)) msg = msg.replace("%" + i, args[i]);
    }

    // Expression régulière pour détecter "[color]COULEUR[color]" avec une couleur en nom
    String nameRegex = "\\[color\\](\\w+)\\[color\\]";

    // Expression régulière pour détecter "[color]HEXA[color]" avec une couleur hexadécimale
    String hexRegex = "\\[color\\]#?([0-9A-Fa-f]{1,6})\\[color\\]";

    // Création des patterns
    Pattern namePattern = Pattern.compile(nameRegex);
    Pattern hexPattern = Pattern.compile(hexRegex);

    // Création des matchers
    Matcher nameMatcher = namePattern.matcher(msg);
    Matcher hexMatcher = hexPattern.matcher(msg);

    // Remplacement des couleurs en nom
    while (nameMatcher.find()) {
      // Récupération de la couleur en nom
      String colorName = nameMatcher.group(1);

      try {
        CustomChatColor customColor = CustomChatColor.valueOf(colorName.toUpperCase()); // Conversion du nom en enum
        msg = msg.replaceFirst("\\[color\\]" + colorName + "\\[color\\]", customColor.getColor().toString());
      } catch (IllegalArgumentException e) {
        // Si le nom de couleur n'existe pas dans l'enum, on le remplace par une couleur d'erreur
        msg = msg.replaceFirst("\\[color\\]" + colorName + "\\[color\\]", CustomChatColor.WHITE.getColor().toString());
      }

    }

    // Remplacement des couleurs hexadécimales
    while (hexMatcher.find()) {
      // Récupération de la couleur hexadécimale
      String hexColor = hexMatcher.group(1);

      // Vérification si le caractère '#' est manquant
      if (!hexColor.startsWith("#")) hexColor = "#" + hexColor;

      // Si la longueur de la chaîne hexadécimale est inférieure à 6 caractères, compléter avec des zéros
      if (hexColor.length() < 7) msg = msg.replaceFirst("\\[color\\]" + hexColor + "\\[color\\]",CustomChatColor.WHITE.getColor().toString());
        // Replace l'hexa par la couleur
      else {
        ChatColor color = CustomChatColor.CUSTOM(hexColor);
        msg = msg.replaceFirst("\\[color\\]" + hexColor + "\\[color\\]", color != null ? color.toString() : CustomChatColor.WHITE.getColor().toString());
      }

    }

    return msg;
  }

}

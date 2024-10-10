package fr.dreamin.api.colors;

import com.rexcantor64.triton.api.Triton;
import com.rexcantor64.triton.api.TritonAPI;
import com.rexcantor64.triton.api.language.Language;
import com.rexcantor64.triton.api.language.LanguageManager;
import com.rexcantor64.triton.api.players.LanguagePlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

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
    // Remplacement des arguments du message (%0, %1, etc.)
    for (int i = 0; i < args.length; ++i) {
      msg = msg.replace("%" + i, args[i]);
    }

    // Modèle pour les noms de couleurs (insensible à la casse)
    Pattern namePattern = Pattern.compile("\\[color\\](\\w+)\\[color\\]", Pattern.CASE_INSENSITIVE);
    // Modèle pour les couleurs hexadécimales (insensible à la casse)
    Pattern hexPattern = Pattern.compile("\\[color\\]#?([0-9A-Fa-f]{6})\\[color\\]", Pattern.CASE_INSENSITIVE);

    Matcher nameMatcher = namePattern.matcher(msg);

    // Remplacement des couleurs nommées
    while (nameMatcher.find()) {
      String colorName = nameMatcher.group(1).toUpperCase();

      try {
        CustomChatColor customColor = CustomChatColor.valueOf(colorName);
        msg = msg.replaceFirst("(?i)\\[color\\]" + colorName + "\\[color\\]", customColor.getColor().toString());
      } catch (IllegalArgumentException e) {
        // Si la couleur n'existe pas, remplace par la couleur d'erreur
        msg = msg.replaceFirst("(?i)\\[color\\]" + colorName + "\\[color\\]", CustomChatColor.ERROR.getColor().toString());
      }
    }

    // Remplacement des couleurs hexadécimales
    Matcher hexMatcher = hexPattern.matcher(msg);
    while (hexMatcher.find()) {
      String hexColor = "#" + hexMatcher.group(1);
      ChatColor color = getCustomColor(hexColor);
      msg = msg.replaceFirst("(?i)\\[color\\]" + hexColor + "\\[color\\]", color != null ? color.toString() : CustomChatColor.ERROR.getColor().toString());
    }

    return msg;
  }

  public static String getMsgTriton(Player player, String msgCode, String... args) {

    player.sendMessage("ttttt");

    if (TritonAPI.getInstance() == null) player.sendMessage("api null");

    Triton api = TritonAPI.getInstance();

    LanguagePlayer tPlayer = api.getPlayerManager().get(player.getUniqueId());

    if (tPlayer == null) player.sendMessage("tPlayer null");
    LanguageManager manager = api.getLanguageManager();

    if (manager == null) player.sendMessage("manager null");

    String message = manager.getText(tPlayer, msgCode);

    if (message == null) player.sendMessage("message null");

    return getMsg(message, args);
  }

}
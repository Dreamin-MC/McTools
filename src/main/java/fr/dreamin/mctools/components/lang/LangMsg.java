package fr.dreamin.mctools.components.lang;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LangMsg {

  //------GENERAL------//
  DISABLED("general.disabled.lang.", "[color]RED[color]Disabled"),
  ENABLED("general.enabled.lang.", "[color]GREEN[color]"),
  INFORMATION("general.information.lang.", "[color]WHITE[color]Information"),
  INFORMATIONS("general.informations.lang.", "[color]WHITE[color]Informations"),
  NO("general.no.lang.", "[color]RED[color]No"),
  YES("general.yes.lang.", "[color]GREEN[color]Yes"),
  MAINGUI_NOTSET("general.mainGui.notSet.lang.", "%BROADCAST%[color]RED[color]No main GUI is defined"),

  //------ERROR------//
  ERROR_CONSOLE("error.console.lang.", "%BROADCAST%[color]RED[color] Vous ne pouvez pas exécuter cette commande dans la console :("),
  ERROR_PERM("error.perm.lang.", "%BROADCAST%[color]RED[color]You don't have the permission to execute this command"),

  //------PLAYER------//
  UPDATE_LANG("player.update.lang.", "%BROADCAST%[color]WHITE[color]Update your language to : [color]GOLD[color]%0%"),

  //------GUI------//

  //configPlayer
  VOICE_CONFIGPLAYER_TITLE("gui.voice.configPlayer.title.lang.", "Modification of %0%"),
  VOICE_CONFIGPLAYER_FORCEDMUTE("gui.voice.configPlayer.forcedMute.lang.", "Forced Mute : %0%"),

  //listPlayer
  VOICE_LISTPLAYER_TITLE("gui.voice.listPlayer.title.lang.", "Modification of"),
  VOICE_LISTPLAYER_LORE_MODIFPLAYER("gui.voice.listPlayer.lore.modifPlayer.lang.", "\"[color]WHITE[color]Click to modify the parameters of : %0%"),
  VOICE_LISTPLAYER_LORE_CONNECTIONSTATUS("gui.voice.listPlayer.lore.connectionStatus.lore.", "[color]WHITE[color]Connection status : %0%"),
  VOICE_LISTPLAYER_LORE_CLIENTVOLUME("gui.voice.listPlayer.lore.clientVolume.lang.", "[color]WHITE[color]Client volume : %0%"),
  VOICE_LISTPLAYER_LORE_MICROSTATUS("gui.voice.listPlayer.lore.microStatus.lang.", "[color]WHITE[color]Microphone status : %0%"),
  VOICE_LISTPLAYER_LORE_FORCEDMUTESTATUS("gui.voice.listPlayer.lore.forcedMuteStatus.lang.", "[color]WHITE[color]Forced mute status : %0%"),
  VOICE_LISTPLAYER_MUTEALL("gui.voice.listPlayer.muteAll.lang.", "Mute everyone"),
  VOICE_LISTPLAYER_UNMUTEALL("gui.voice.listPlayer.unmuteAll.lang.", "Unmute everyone"),

  //listMap
  LISTMAP_TITLE("gui.listMap.title.lang.", "List of maps"),

  //weatherSettings
  WEATHERSETTINGS_TITLE("gui.weatherSettings.title.lang.", "Weather Settings"),
  WEATHERSETTINGS_WEATHER("gui.weatherSettings.weather.lang.", "[color]WHITE[color]Weather : [color]GOLD[color]%0%"),
  WEATHERSETTINGS_THUNDER("gui.weatherSettings.weather.thunder.lang.", "[color]YELLOW[color]Thunder"),
  WEATHERSETTINGS_RAIN("gui.weatherSettings.weather.rain.lang.", "[color]YELLOW[color]Rain"),

  WEATHERSETTINGS_DAY("gui.weatherSettings.day.lang.", "[color]WHITE[color]Day : [color]GOLD[color]%0%");


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

    for (int i = 0; i < args.length; i++) {
      if (msg.contains("%" + i + "%")) msg = msg.replace("%" + i + "%", args[i]);
    }

    //replace white prefix
    if (msg.contains("%PREFIX%")) msg = msg.replace("%PREFIX%", McTools.getCodex().getPrefix());
    //replace white broadcast
    if (msg.contains("%BROADCAST%")) msg = msg.replace("%BROADCAST%", McTools.getCodex().getBroadcastprefix());

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

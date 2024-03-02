package fr.dreamin.mctools.components.lang;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LangMsg {

  //------GENERAL------//
  GENERAL_DISABLED("general.disabled.lang.", "[color]RED[color]Disabled"),
  GENERAL_ENABLED("general.enabled.lang.", "[color]GREEN[color]"),
  GENERAL_INFORMATION("general.information.lang.", "[color]WHITE[color]Information"),
  GENERAL_INFORMATIONS("general.informations.lang.", "[color]WHITE[color]Informations"),
  GENERAL_NO("general.no.lang.", "[color]RED[color]No"),
  GENERAL_YES("general.yes.lang.", "[color]GREEN[color]Yes"),
  GENERAL_RETURNMENU("general.returnMenu.lang.", "[color]WHITE[color]Return to menu"),
  GENERAL_LEAVE("general.leave.lang.", "[color]WHITE[color]Quit"),
  GENERAL_GUIIN("general.guiIn.lang.", "[color]WHITE[color]GUI in"),
  GENERAL_VISIBLE("general.visible.lang.", "[color]WHITE[color]Visible"),
  GENERAL_INVISIBLE("general.invisible.lang.", "[color]WHITE[color]Invisible"),
  GENERAL_ACTIVATE("general.activate.lang.", "[color]WHITE[color]Activer"),
  GENERAL_LEFT("general.left.lang.", "[color]WHITE[color]Left"),
  GENERAL_RIGHT("general.right.lang.", "[color]WHITE[color]Right"),
  GENERAL_UP("general.up.lang.", "[color]WHITE[color]Up"),
  GENERAL_DOWN("general.down.lang.", "[color]WHITE[color]Down"),
  GENERAL_BACK("general.back.lang.", "[color]WHITE[color]Back"),
  GENERAL_PREVIOUS("general.previous.lang.", "[color]WHITE[color]Previous"),
  GENERAL_NEXT("general.next.lang.", "[color]WHITE[color]Next"),
  GENERAL_BACKWARD("general.backward.lang.", "[color]WHITE[color]Forward"),
  GENERAL_FORWARD("general.forward.lang.", "[color]WHITE[color]Forward"),
  GENERAL_ARM("general.arm.lang.", "[color]WHITE[color]Arm"),
  GENERAL_MOVE("general.move.lang.", "[color]WHITE[color]Move"),
  GENERAL_SIZE("general.size.lang.", "[color]WHITE[color]Size"),
  GENERAL_INVULNERABLE("general.invulnerable.lang.", "[color]WHITE[color]Invulnerable"),
  GENERAL_GLOWING("general.glowing.lang.", "[color]WHITE[color]Glowing"),
  GENERAL_REMOVEALL("general.removeAll.lang.", "[color]WHITE[color]Remove all"),
  GENERAL_ADDALL("general.addAll.lang.", "[color]WHITE[color]Add all"),
  GENERAL_ADDED("general.added.lang.", "[color]GREEN[color]added"),
  GENERAL_REMOVED("general.removed.lang.", "[color]RED[color]removed"),
  GENERAL_DESTROY("general.destroy.lang.", "[color]RED[color]destroy"),
  GENERAL_SELECTED("general.selected.lang.", "[color]GREEN[color]selected"),
  GENERAL_LOCKED("general.locked.lang.", "[color]WHITE[color]locked"),
  GENERAL_RADIUS("general.radius.lang.", "[color]WHITE[color]radius"),
  GENERAL_HAVEALLARMORSTAND("general.haveAllArmorStand.lang.", "[color]WHITE[color]You have %0 all %1 [color]WHITE[color]armor stand"),
  GENERAL_HAVEARMORSTAND("general.haveArmorStand.lang.", "[color]WHITE[color]You have %0 a %1 armor stand"),

  //------ERROR------//
  ERROR_CONSOLE("error.console.lang.", "%BROADCAST%[color]RED[color]You cannot execute this command in the console :("),
  ERROR_PERM("error.perm.lang.", "%BROADCAST%[color]RED[color]You don't have the permission to execute this command"),
  ERROR_MAINGUI_NOTSET("error.mainGui.notSet.lang.", "%BROADCAST%[color]RED[color]No main GUI is defined"),
  ERROR_OCCURRED("error.occurred.lang.", "%BROADCAST%[color]RED[color]An error occurred while executing this command"),
  ERROR_PUTVALUE("error.putValue.lang.", "%BROADCAST%[color]RED[color]Please put a value"),
  ERROR_VALIDPUTVALUE("error.validPutValue.lang.", "%BROADCAST%[color]RED[color]Please provide a valid argument"),
  ERROR_VALIDNUMBER("error.validNumber.lang.", "%BROADCAST%[color]RED[color]Please put a valid number"),
  ERROR_RANGEID("error.rangeId.lang.", "%BROADCAST%[color]RED[color]Please put a valid id range"),
  ERROR_ITEMHAND("error.itemHand.lang.", "%BROADCAST%[color]RED[color]Please hold an item in hand."),
  ERROR_ITEMID("error.itemId.lang.", "%BROADCAST%[color]RED[color]This item does not have a custom ID."),
  ERROR_EDITMODE_NOTENABLED("error.editModeNotEnalbe.lang.", "%BROADCAST%[color]RED[color]The edit mode is not enabled"),
  ERROR_DOUBLECOUNT("error.doubleCount.lang.", "[color]RED[color]Double counting is not allowed."),

  //------PLAYER------//
  PLAYER_UPDATE_LANG("player.update.lang.", "%BROADCAST%[color]WHITE[color]Update your language to : [color]GOLD[color]%0"),

  //armorStand
  PLAYER_ARMORSTAND_LORE_GENERAL_LEFTCLICKADD("player.armorStand.lore.general.leftClickAdd.lang.", "[color]WHITE[color]Left click to add to list"),
  PLAYER_ARMORSTAND_LORE_GENERAL_RIGHTCLICKREMOVE("player.armorStand.lore.general.rightClickRemove.lang.", "[color]WHITE[color]Right click to remove from the list"),
  PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTCLICKTP("player.armorStand.lore.general.shiftClickTp.lang.", "[color]WHITE[color]Shift click to teleport"),
  PLAYER_ARMORSTAND_LORE_GENERAL_CLICKWHEELMODEL("player.armorStand.lore.general.clickWheelModel.lang.", "[color]WHITE[color]Click the wheel to retrieve the model"),
  PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTLEFTCLICKTP("player.armorStand.lore.general.shiftLeftClickTp.lang." ,"[color]WHITE[color]Shift left click to teleport"),
  PLAYER_ARMORSTAND_LORE_GENERAL_SHIFTRIGHTCLICKSHOW("player.armorStand.lore.general.shiftRightClickShow.lang." ,"[color]WHITE[color]Shift right click to show"),

  //------GUI------//

  //configPlayer
  GUI_VOICE_CONFIGPLAYER_TITLE("gui.voice.configPlayer.title.lang.", "Modification of %0"),
  GUI_VOICE_CONFIGPLAYER_FORCEDMUTE("gui.voice.configPlayer.forcedMute.lang.", "Forced Mute : %0"),

  //listPlayer
  GUI_VOICE_LISTPLAYER_TITLE("gui.voice.listPlayer.title.lang.", "Modification of"),
  GUI_VOICE_LISTPLAYER_LORE_MODIFPLAYER("gui.voice.listPlayer.lore.modifPlayer.lang.", "\"[color]WHITE[color]Click to modify the parameters of : %0"),
  GUI_VOICE_LISTPLAYER_LORE_CONNECTIONSTATUS("gui.voice.listPlayer.lore.connectionStatus.lore.", "[color]WHITE[color]Connection status : %0"),
  GUI_VOICE_LISTPLAYER_LORE_CLIENTVOLUME("gui.voice.listPlayer.lore.clientVolume.lang.", "[color]WHITE[color]Client volume : %0"),
  GUI_VOICE_LISTPLAYER_LORE_MICROSTATUS("gui.voice.listPlayer.lore.microStatus.lang.", "[color]WHITE[color]Microphone status : %0"),
  GUI_VOICE_LISTPLAYER_LORE_FORCEDMUTESTATUS("gui.voice.listPlayer.lore.forcedMuteStatus.lang.", "[color]WHITE[color]Forced mute status : %0"),
  GUI_VOICE_LISTPLAYER_MUTEALL("gui.voice.listPlayer.muteAll.lang.", "Mute everyone"),
  GUI_VOICE_LISTPLAYER_UNMUTEALL("gui.voice.listPlayer.unmuteAll.lang.", "Unmute everyone"),

  //listMap
  GUI_LISTMAP_TITLE("gui.listMap.title.lang.", "List of maps"),

  //weatherSettings
  GUI_WEATHERSETTINGS_TITLE("gui.weatherSettings.title.lang.", "Weather Settings"),
  GUI_WEATHERSETTINGS_WEATHER("gui.weatherSettings.weather.lang.", "[color]WHITE[color]Weather : [color]GOLD[color]%0"),
  GUI_WEATHERSETTINGS_WEATHER_THUNDER("gui.weatherSettings.weather.thunder.lang.", "[color]YELLOW[color]Thunder"),
  GUI_WEATHERSETTINGS_WEATHER_RAIN("gui.weatherSettings.weather.rain.lang.", "[color]YELLOW[color]Rain"),
  GUI_WEATHERSETTINGS_WEATHER_CLEAR("gui.weatherSettings.weather.clear.lang.", "[color]GREEN[color]Clear"),
  GUI_WEATHERSETTINGS_DAY("gui.weatherSettings.day.lang.", "[color]WHITE[color]Day : [color]GOLD[color]%0%"),
  GUI_WEATHERSETTINGS_DAY_MIDNIGHT("gui.weatherSettings.day.midnight.lang.", "[color]DARKBLUE[color]Midnight"),
  GUI_WEATHERSETTINGS_DAY_MOON("gui.weatherSettings.day.night.lang.", "[color]DARKRED[color]Night"),
  GUI_WEATHERSETTINGS_DAY_SUN("gui.weatherSettings.day.sun.lang.", "[color]YELLOW[color]Day"),
  GUI_WEATHERSETTINGS_DAY_MORNING("gui.weatherSettings.day.morning.lang.", "[color]YELLOW[color]Morning"),

  //armorStand

  //general
  GUI_ARMORSTAND_GENERAL_ROTATION("gui.armorStand.general.rotation.lang.", "[color]WHITE[color]Rotation %0 %1"),
  GUI_ARMORSTAND_GENERAL_DESTROYARMORSTAND("gui.armorStand.general.destroyArmorStand.lang.", "[color]WHITE[color]Destroy armorStand"),
  GUI_ARMORSTAND_GENERAL_HAVEAARMORSTANDSELECTED("gui.armorStand.general.haveAArmorStandSelected.lang.", "[color]WHITE[color]You have %0 an armor holder to your selection"),
  GUI_ARMORSTAND_GENERAL_TELEPORTTOARMORSTAND("gui.armorStand.general.teleportToArmorStand.lang.", "[color]WHITE[color]You have been teleported to the armor stand."),
  GUI_ARMORSTAND_GENERAL_DELETELIST("gui.armorStand.general.deleteList.lang.", "[color]RED[color]Delete the list"),
  GUI_ARMORSTAND_GENERAL_TRAVELDISTANCE("gui.armorStand.general.travelDistance.lang.", "[color]WHITE[color]Travel distance %0 %1"),

  //error
  GUI_ARMORSTAND_ERROR_NOTITEMHEAD("gui.armorStand.error.notItemHead", "%BROADCAST%This armor stand does not have an item on its head."),

  //main GUI
  GUI_ARMORSTAND_MAIN_TITLE("gui.armorStand.main.title.lang.", "Armor stand menu"),
  GUI_ARMORSTAND_MAIN_BASICSETTINGS("gui.armorStand.main.basicSettings.lang.", "[color]WHITE[color]Base settings"),
  GUI_ARMORSTAND_MAIN_BODYSETTINGS("gui.armorStand.main.bodySettings.lang.", "[color]WHITE[color]Body settings"),
  GUI_ARMORSTAND_MAIN_MOVEANDROTATE("gui.armorStand.main.moveAndRotate.lang.", "[color]WHITE[color]Move and rotate"),
  GUI_ARMORSTAND_MAIN_SETPOSE("gui.armorStand.main.setPose.lang.", "[color]WHITE[color]Set position"),
  GUI_ARMORSTAND_MAIN_LISTARMORSTAND("gui.armorStand.main.listArmorStand.lang.", "[color]WHITE[color]List of armor stands"),
  GUI_ARMORSTAND_MAIN_TAG("gui.armorStand.main.tag.lang.", "[color]WHITE[color]Tags"),

  //move rotate
  GUI_ARMORSTAND_MOVEANDROTATE_TITLE("gui.armorStand.moveAndRotate.title.lang.", "[color]WHITE[color]Move and rotate"),
  GUI_ARMORSTAND_MOVEANDROTATE_ROTATELEFT("gui.armorStand.moveAndRotate.rotateLeft", "[color]WHITE[color]Rotate left"),
  GUI_ARMORSTAND_MOVEANDROTATE_ROTATERIGHT("gui.armorStand.moveAndRotate.rotateRight", "[color]WHITE[color]Rotate right"),

  //presetPoses
  GUI_ARMORSTAND_PRESETPOSES_TITLE("gui.armorStand.presetPoses.title.lang.", "List of poses"),


  //arms
  GUI_ARMORSTAND_ARMSSETTINGS_TITLE("gui.armorStand.armsSettings.title.lang.", "Arms Settings"),
  GUI_ARMORSTAND_ARMSETTINGS_ARM("gui.armorStand.armSettings.arm.lang.", "[color]WHITE[color]%0 : %1"),
  GUI_ARMORSTAND_ARMSETTINGS_MOVE("gui.armorStand.armsSettings.move.lang.", "[color]WHITE[color]Move the axis %0 : %1"),

  //basic settings

  GUI_ARMORSTAND_BASICSETTINGS_TITLE("gui.armorStand.basicSettings.title.lang.", "Basic Settings"),
  GUI_ARMORSTAND_BASICSETTINGS_INVISIBLEORVISIBLE("gui.armorStand.basicSettings.invisibleOrVisible.lang.", "[color]WHITE[color]Invisible or visible"),
  GUI_ARMORSTAND_BASICSETTINGS_GRAVITY("gui.armorStand.basicSettings.gravity.lang.", "[color]WHITE[color]Gravity : %0"),
  GUI_ARMORSTAND_BASICSETTINGS_BASEARMORSTAND( "gui.armorStand.basicSettings.baseArmorStand.lang." ,"[color]WHITE[color]Base of the armure %0"),

  //list Locked
  GUI_ARMORSTAND_LISTLOCKED_TITLE("gui.armorStand.listLocked.title.lang.", "List locked"),

  //list radius
  GUI_ARMORSTAND_LISTRADIUS_TITLE("gui.armorStand.listRadius.title.lang.", "List radius"),

  //list selected
  GUI_ARMORSTAND_LISTSELECTED_TITLE("gui.armorStand.listSelected.title.lang.", "List selected"),

  //list tag
  GUI_TAG_TITLE("gui.tag.title.lang.", "List of tags"),

  //tag category
  GUI_TAGCATEGORY_TITLE("gui.tagCategory.title.lang.", "List of tag categories"),

  //------CMD------//

  //mt
  CMD_MT_SETEDITMODE("cmd.mt.setEditMode.lang.", "%BROADCAST%[color]WHITE[color]You have %0 [color]WHITE[color]edit mode."),
  CMD_MT_ERROR_VALIDLANG("cmd.mt.error.validLang.lang.", "%BROADCAST%[color]RED[color]Please select a valid language."),
  CMD_MT_SETPACK("cmd.mt.setPack.lang.", "%BROADCAST%[color]WHITE[color]You have %0 [color]WHITE[color]the pack."),

  //tag
  CMD_TAG_ERROR_SELECTTAG("cmd.tag.error.selectTag.lang.", "%BROADCAST%[color]RED[color]Please select a tag."),
  CMD_TAG_ERROR_SETTAGANDVALUE("cmd.tag.error.setTagAndValue.lang.", "%BROADCAST%[color]RED[color]Please enter a tag name and value."),
  CMD_TAG_ERROR_TAGCHARLIMIT("cmd.tag.error.tagCharLimit", "%BROADCAST%[color]RED[color]Please enter a tag name or value shorter than 16 characters."),
  CMD_TAG_ERROR_PUTCATEGORYVALUE("cmd.tag.error.putCategoryValue.lang.", "%BROADCAST%[color]RED[color]Please enter a tag category value."),
  CMD_TAG_ERROR_CATEGORYCHARLIMIT("cmd.tag.error.categoryCharLimit.lang.", "%BROADCAST%[color]RED[color]Please enter a category name of less than 16 characters."),
  CMD_TAG_ERROR_TAGCATEGORY("cmd.tag.error.tagCategory.lang.", "%BROADCAST%[color]RED[color]Please select a tag category");

  @Getter @Setter
  private static YamlConfiguration config;
  @Getter
  private String path;
  @Getter
  private String dflValue;

  LangMsg(String path, String dflValue) {
    this.path = path;
    this.dflValue = dflValue;
  }

  private static String getStr(String path, String dflValue) {
    return (config.getString(path, dflValue));
  }

  public String getMsg(Lang lang, String... args) {

    String msg = getStr(this.path + lang.getNameCode(), this.dflValue);

    for (int i = 0; i < args.length; i++) {
      if (msg.contains("%" + i)) msg = msg.replace("%" + i, args[i]);
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

  public String getMsg(Lang lang, LangMsg... args) {

    String msg = getStr(this.path + lang.getNameCode(), this.dflValue);

    for (int i = 0; i < args.length; i++) {
      if (msg.contains("%" + i)) msg = msg.replace("%" + i, args[i].getMsg(lang, ""));
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

  public static String getMsg(String path, String dflValue, Lang lang, String... args) {

    String msg = getStr(path + lang.getNameCode(), dflValue);

    for (int i = 0; i < args.length; i++) {
      if (msg.contains("%" + i)) msg = msg.replace("%" + i, args[i]);
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

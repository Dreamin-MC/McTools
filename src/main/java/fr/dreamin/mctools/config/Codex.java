package fr.dreamin.mctools.config;

import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.database.DatabaseManager;
import fr.dreamin.mctools.database.DatabaseType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Codex {
  private FileConfiguration config;

  //  >>>>>>> GENERAL <<<<<<<

  @Getter @Setter private String pluginName, prefix, broadcastprefix, version, ipApiKey, resourcePackUrl;
  @Getter @Setter private boolean resourcePack, doubleCount;

  //  >>>>>>>> VOICE <<<<<<<<
  @Getter @Setter private boolean voiceMode, voiceWallMode;
  @Getter @Setter private int voiceDistanceMax;

  //  >>>>>>>> BUILD <<<<<<<<
  @Getter @Setter private boolean buildMode, buildArmorStand;

  // >>>>>>>> DOOR <<<<<<<<
  @Getter @Setter private boolean doorMode;
  @Getter @Setter private String doorAnimOpen, doorAnimClose;

  // >>>>>>>> INTERACT <<<<<<<<
  @Getter @Setter private boolean interactMode;
  @Getter @Setter private String interactAnimInteract;

  // >>>>>>> STAFF <<<<<<<

  @Getter @Setter private boolean staffMode, staffChatMode, staffFreeze;
  @Getter private String staffChatPrefix, staffBroadcastPrefix;

  // >>>>>>> LANG <<<<<<<
  @Getter @Setter private Lang defaultLang;
  @Getter @Setter private boolean langByIp = false;
  @Getter private List<Lang> langs = new ArrayList<>();

  //  >>>>>>>> DATABASE <<<<<<<<
  @Getter @Setter private DatabaseType databaseType;
  @Getter @Setter private String mysqlHost, mysqlDbName, mysqlUsername, mysqlPassword, mysqlDefaultPrefix;
  @Getter @Setter private int mysqlPort;
  @Getter @Setter private String sqlName;

  // >>>>>>>> API <<<<<<<<
  @Getter @Setter private boolean editMode = false, defaultGui = false, defaultItems = false;

  public Codex(FileConfiguration config) {
    this.config = config;
    pluginName = "McTools";
    initGlobal();
    initVoice();
    initBuild();
    initDoor();
    initInteract();
    initStaff();
    initLang();
    initSQLData();
  }

  public void loadConf() {
    Bukkit.broadcastMessage(pluginName + " >> §r§aReload de la configuration...");

    initGlobal();
    initVoice();
    initBuild();
    initDoor();
    initInteract();
    initStaff();
    initLang();
    initSQLData();

    Bukkit.broadcastMessage(pluginName + " >> §r§aConfiguration chargé");
  }


  private void initGlobal() {
    version = getStr("general.version", null);
    prefix = getStr("general.prefix", "§8» §f");
    broadcastprefix = getStr("general.broadcast-prefix", "[§c§lMcTools§r] ");
    ipApiKey = getStr("general.ip-api-key", null);
    resourcePack = getBool("general.resourcepack", false);
    resourcePackUrl = getStr("general.resourcepack-url", null);
    if (resourcePackUrl.isEmpty()) resourcePackUrl = null;
    doubleCount = getBool("general.double-count", true);
  }

  private void initVoice(){
    voiceMode = getBool("voice.enable", false);
    voiceWallMode = getBool("voice.wall-mode", false);
    voiceDistanceMax = getInt("voice.distance-max", 10);
  }

  private void initBuild() {
    buildMode = getBool("build.enable", false);
    buildArmorStand = getBool("build.armor-stand", false);
  }

  private void initDoor() {
    doorMode = getBool("door.enable", false);
    doorAnimOpen = getStr("door.anim-open", "anim_open");
    doorAnimClose = getStr("door.anim-close", "anim_close");
  }

  private void initInteract() {
    interactMode = getBool("interact.enable", false);
    interactAnimInteract = getStr("interact.anim-interact", "anim_interact");
  }

  private void initStaff() {
    staffMode = getBool("staff.enable", false);
    staffFreeze = getBool("staff.freeze", false);
    staffBroadcastPrefix = getStr("staff.broadcast-prefix", "[Staff] ");
    staffChatMode = getBool("staff.chat.enable", false);
    staffChatPrefix = getStr("staff.chat.prefix", "!");
  }

  private void initLang() {

    langByIp = getBool("lang-by-ip", false);

    if (config.contains("langs")) {
      ConfigurationSection langSection = config.getConfigurationSection("langs");

      for (String langKey : langSection.getKeys(false)) {
        ConfigurationSection langInfo = langSection.getConfigurationSection(langKey);
        String displayName = langInfo.getString("display-name", null);
        List<String> langCode = langInfo.getStringList("lang-code");

        if (displayName != null && langCode != null && !langCode.isEmpty()) langs.add(new Lang(langKey, displayName, langCode));
      }
    }
    else langs.add(new Lang("en_GB", "English", List.of("en_GB", "en_AU", "en_CA", "en_NZ", "en_PT", "en_UD", "en_US")));

    if (langs.isEmpty()) langs.add(new Lang("en_GB", "English", List.of("en_GB", "en_AU", "en_CA", "en_NZ", "en_PT", "en_UD", "en_US")));

    if (Lang.contains(langs, getStr("default-lang", "en_GB"))) defaultLang = Lang.getLang(langs, getStr("mcTools.defaultLang", "en_GB"));
    else defaultLang = Lang.getLang(langs,"en_GB");
  }

  private void initSQLData() {

    DatabaseType dbType =  DatabaseType.getByName(getStr("database.type", "SQLite"));
    if (dbType != null) databaseType = dbType;
    else databaseType = DatabaseType.SQLITE;

    sqlName = getStr("database.sqlLite.name", "database");
    mysqlHost = getStr("database.mysql.host", null);
    mysqlPort = getInt("database.mysql.port");
    mysqlDbName = getStr("database.mysql.dbName", null);
    mysqlUsername = getStr("database.mysql.username", null);
    mysqlPassword = getStr("database.mysql.password", null);
    mysqlDefaultPrefix = getStr("database.mysql.defaultPrefix", null);

    DatabaseManager.closeAllConnection();

    DatabaseManager.setConnection(this, databaseType);

  }

  private String getStr(String path) {
    return (config.getString(path));
  }
  private String getStr(String path, String dflValue) {
    return (config.getString(path, dflValue));
  }

  private int getInt(String path) {
    return (config.getInt(path));
  }
  private int getInt(String path, Integer dflValue) {
    return (config.getInt(path, dflValue));
  }

  private boolean getBool(String path) {return (config.getBoolean(path));}
  private boolean getBool(String path, Boolean dflValue) {return (config.getBoolean(path, dflValue));}

  public FileConfiguration getConfig() {
    return config;
  }

  public String getDefaultPrefix() {
    return (getDatabaseType().equals(DatabaseType.MYSQL)? mysqlDefaultPrefix :  "");
  }

}
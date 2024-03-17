package fr.dreamin.mctools.config;

import fr.dreamin.mctools.McTools;
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
  @Getter private FileConfiguration config;

  //  >>>>>>> GENERAL <<<<<<<

  @Getter @Setter private String pluginName, prefix, broadcastprefix, version, ipApiKey, resourcePackUrl;
  @Getter @Setter private boolean resourcePack, doubleCount, removePlayer;

  //  >>>>>>>> VOICE <<<<<<<<
  @Getter @Setter private boolean voiceMode, voiceWallMode;
  @Getter @Setter private int voiceDistanceMax;

  //  >>>>>>>> BUILD <<<<<<<<
  @Getter @Setter private boolean buildMode, buildArmorStand;

  // >>>>>>>> INTERFACE <<<<<<<<
  @Getter @Setter private boolean interactRaycast;

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

  public Codex() {
    this.config = McTools.getInstance().getConfig();
    pluginName = "McTools";
    initGlobal();
    initVoice();
    initBuild();
    initInterface();
    initDoor();
    initInteract();
    initStaff();
    initLang();
    initSQLData();
  }

  public void loadConf() {
    Bukkit.broadcastMessage(pluginName + " >> §r§aReload de la configuration...");

    this.config = McTools.getInstance().getConfig();
    initGlobal();
    initVoice();
    initBuild();
    initInterface();
    initDoor();
    initInteract();
    initStaff();
    initLang();
    initSQLData();

    Bukkit.broadcastMessage(pluginName + " >> §r§aConfiguration chargé");
  }


  private void initGlobal() {
    version = config.getString("general.version", null);
    prefix = config.getString("general.prefix", "§8» §f");
    broadcastprefix = config.getString("general.broadcast-prefix", "[§c§lMcTools§r] ");
    ipApiKey = config.getString("general.ip-api-key", null);
    resourcePack = config.getBoolean("general.resourcepack", false);
    resourcePackUrl = config.getString("general.resourcepack-url", null);
    if (resourcePackUrl.isEmpty()) resourcePackUrl = null;
    doubleCount = config.getBoolean("general.double-count", true);
    removePlayer = config.getBoolean("general.remove-player", true);
  }

  private void initVoice(){
    voiceMode = config.getBoolean("voice.enable", false);
    voiceWallMode = config.getBoolean("voice.wall-mode", false);
    voiceDistanceMax = config.getInt("voice.distance-max", 10);
  }

  private void initBuild() {
    buildMode = config.getBoolean("build.enable", false);
    buildArmorStand = config.getBoolean("build.armor-stand", false);
  }

  private void initInterface() {
    interactRaycast = config.getBoolean("interface.raycast", false);
  }

  private void initDoor() {
    doorMode = config.getBoolean("door.enable", false);
    doorAnimOpen = config.getString("door.anim-open", "anim_open");
    doorAnimClose = config.getString("door.anim-close", "anim_close");
  }

  private void initInteract() {
    interactMode = config.getBoolean("interact.enable", false);
    interactAnimInteract = config.getString("interact.anim-interact", "anim_interact");
  }

  private void initStaff() {
    staffMode = config.getBoolean("staff.enable", false);
    staffFreeze = config.getBoolean("staff.freeze", false);
    staffBroadcastPrefix = config.getString("staff.broadcast-prefix", "[Staff] ");
    staffChatMode = config.getBoolean("staff.chat.enable", false);
    staffChatPrefix = config.getString("staff.chat.prefix", "!");
  }

  private void initLang() {

    langByIp = config.getBoolean("lang-by-ip", false);

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

    if (Lang.contains(langs, config.getString("default-lang", "en_GB"))) defaultLang = Lang.getLang(langs, config.getString("default-lang", "en_GB"));
    else defaultLang = Lang.getLang(langs,"en_GB");
  }

  private void initSQLData() {

    DatabaseType dbType =  DatabaseType.getByName(config.getString("database.type", "SQLite"));
    if (dbType != null) databaseType = dbType;
    else databaseType = DatabaseType.SQLITE;

    sqlName = config.getString("database.sqlLite.name", "database");
    mysqlHost = config.getString("database.mysql.host", null);
    mysqlPort = config.getInt("database.mysql.port");
    mysqlDbName = config.getString("database.mysql.dbName", null);
    mysqlUsername = config.getString("database.mysql.username", null);
    mysqlPassword = config.getString("database.mysql.password", null);
    mysqlDefaultPrefix = config.getString("database.mysql.defaultPrefix", null);

    DatabaseManager.closeAllConnection();

    DatabaseManager.setConnection(this, databaseType);

  }

  public String getDefaultPrefix() {
    return (getDatabaseType().equals(DatabaseType.MYSQL)? mysqlDefaultPrefix :  "");
  }

}
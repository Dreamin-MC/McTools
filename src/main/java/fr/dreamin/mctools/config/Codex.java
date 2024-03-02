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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Codex {
  private FileConfiguration config;

  //  >>>>>>> PLUGIN <<<<<<<
  @Getter @Setter
  private String pluginName, prefix, broadcastprefix;

  @Getter @Setter private Lang defaultLang;

  @Getter private List<Lang> langs = new ArrayList<>();

  @Getter @Setter private boolean doubleCount;

  //  >>>>>> MESSAGE <<<<<<
  @Getter @Setter
  private String errorConsole, errorCommand;

  //  >>>>>>>> GUI <<<<<<<<
  @Getter @Setter
  private String prefixGUIName = "";

  //  >>>>>>>> DATABASE <<<<<<<<
  @Getter @Setter
  private DatabaseType databaseType;
  @Getter @Setter
  private String host, dbName, username, password, defaultPrefix;
  @Getter @Setter
  private int port;
  @Getter @Setter
  private String sqlName;

  // >>>>>>>> API <<<<<<<<
  @Getter @Setter
  private boolean editMode = false, defaultGui = false, defaultItems = false, buildMode = false, pack = false;
  @Getter @Setter
  private String ipApiKey;

  //  >>>>>>>> VOICE <<<<<<<<
  @Getter @Setter
  private boolean voiceMode, voiceWallMode;
  @Getter @Setter
  private int voiceDistanceMax;



  public Codex(FileConfiguration config) {
    this.config = config;
    pluginName = "McTools";

    initGlobal();
    initSQLData();
    initApi();
    initVoice();
  }

  public void loadConf() {
    Bukkit.broadcastMessage(pluginName + " >> §r§aReload de la configuration...");

    initGlobal();
    initSQLData();
    initApi();
    initVoice();

    Bukkit.broadcastMessage(pluginName + " >> §r§aConfiguration chargé");
  }


  private void initGlobal(){
    prefix = getStr("mcTools.prefix", "§8» §f");
    broadcastprefix = getStr("mcTools.broadcastprefix", "[§c§lMcTools§r] ");

    doubleCount = getBool("mcTools.doubleCount", true);

    if (config.contains("lang")) {
      ConfigurationSection langSection = config.getConfigurationSection("lang");

      for (String langKey : langSection.getKeys(false)) {
        ConfigurationSection langInfo = langSection.getConfigurationSection(langKey);
        String displayName = langInfo.getString("display-name", null);
        List<String> langCode = langInfo.getStringList("langCode");

        if (displayName != null && langCode != null && !langCode.isEmpty()) langs.add(new Lang(langKey, displayName, langCode));
      }
    }
    else langs.add(new Lang("en_GB", "English", List.of("en_GB", "en_AU", "en_CA", "en_NZ", "en_PT", "en_UD", "en_US")));

    if (langs.isEmpty()) langs.add(new Lang("en_GB", "English", List.of("en_GB", "en_AU", "en_CA", "en_NZ", "en_PT", "en_UD", "en_US")));

    if (Lang.contains(langs, getStr("mcTools.defaultLang", "en_GB"))) defaultLang = Lang.getLang(langs, getStr("mcTools.defaultLang", "en_GB"));
    else defaultLang = Lang.getLang(langs,"en_GB");
  }

  private void initSQLData() {

    DatabaseType dbType =  DatabaseType.getByName(getStr("databaseType", "SQLite"));
    if (dbType != null) databaseType = dbType;
    else databaseType = DatabaseType.SQLITE;

    sqlName = getStr("sqlLite.name", "database");
    host = getStr("mysql.host");
    port = getInt("mysql.port");
    dbName = getStr("mysql.dbName");
    username = getStr("mysql.username");
    password = getStr("mysql.password");
    defaultPrefix = getStr("mysql.defaultPrefix");

    DatabaseManager.closeAllConnection();

    DatabaseManager.setConnection(this, databaseType);

  }

  private void initApi() {
    editMode = getBool("mcTools.editMode", false);
    defaultGui = getBool("mcTools.defaultGui", false);
    defaultItems = getBool("mcTools.defaultItems", false);
    buildMode = getBool("mcTools.buildMode", false);
    pack = getBool("mcTools.ressourcepack", false);
    ipApiKey = getStr("mcTools.ipApiKey", null);
  }

  private void initVoice(){
    voiceMode = getBool("mcTools.voiceMode", false);
    voiceWallMode = getBool("mcTools.voiceWallMode", false);
    voiceDistanceMax = getInt("mcTools.voiceDistanceMax", 10);
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
    return (getDatabaseType().equals(DatabaseType.MYSQL)? defaultPrefix :  "");
  }

}
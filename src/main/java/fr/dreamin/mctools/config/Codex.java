package fr.dreamin.mctools.config;

import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.database.DatabaseManager;
import fr.dreamin.mctools.database.DatabaseType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Codex {
  private FileConfiguration config;

  //  >>>>>>> PLUGIN <<<<<<<
  @Getter @Setter
  private String pluginName, prefix, broadcastprefix;

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
  private Lang defaultLang;
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
    defaultLang = Lang.valueOf(getStr("mcTools.defaultLang", "en_US"));
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
    return (getDatabaseType().equals(DatabaseType.MYSQL)? getDefaultPrefix() :  "");
  }

}
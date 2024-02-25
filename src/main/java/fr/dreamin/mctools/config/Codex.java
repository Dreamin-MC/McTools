package fr.dreamin.mctools.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Codex {
  private FileConfiguration config;
  //  >>>>>>> PLUGIN <<<<<<<
  private String pluginName, prefix, broadcastprefix;

  //  >>>>>> MESSAGE <<<<<<
  private String errorConsole, errorCommand;

  //  >>>>>>>> GUI <<<<<<<<
  private String prefixGUIName = "";

  //  >>>>>>>> SQL <<<<<<<<
  private String host, dbName, username, password, defaultPrefix;
  private int port;

  // >>>>>>>> API <<<<<<<<
  private boolean editMode = false, defaultGui = false, defaultItems = false, buildMode = false, ressourcepack = false;

  //  >>>>>>>> VOICE <<<<<<<<
  private boolean voiceMode, voiceWallMode;
  private int voiceDistanceMax;

  public Codex(FileConfiguration config) {
    this.config = config;
    pluginName = "DreamTools";
    initMessage();
    initGlobal();
    initSQLData();
    initApi();
    initVoice();
  }

  public void loadConf() {
    Bukkit.broadcastMessage(pluginName + " >> §r§aReload de la configuration...");

    initMessage();
    initGlobal();
    initSQLData();
    initApi();
    initVoice();

    Bukkit.broadcastMessage(pluginName + " >> §r§aConfiguration chargé");
  }


  private void initGlobal(){
    prefix = getStr("mcTools.prefix");
    broadcastprefix = getStr("mcTools.broadcastprefix");


  }
  private void initMessage(){
    errorConsole = getStr("mcTools.message.errorconsole");
    errorCommand = getStr("mcTools.message.errorcmd");
  }

  private void initSQLData() {
    host = getStr("sql.host");
    port = getInt("sql.port");
    dbName = getStr("sql.dbName");
    username = getStr("sql.username");
    password = getStr("sql.password");
    defaultPrefix = getStr("sql.defaultPrefix");
  }

  private void initApi() {
    editMode = getBool("mcTools.editMode");
    defaultGui = getBool("mcTools.defaultGui");
    defaultItems = getBool("mcTools.defaultItems");
    buildMode = getBool("mcTools.buildMode");
    ressourcepack = getBool("mcTools.ressourcepack");
  }

  private void initVoice(){
    voiceMode = getBool("mcTools.voiceMode");
    voiceWallMode = getBool("mcTools.voiceWallMode");
    voiceDistanceMax = getInt("mcTools.voiceDistanceMax");
  }


  private String getStr(String path) {
    return (config.getString(path));
  }
  private int getInt(String path) {
    return (config.getInt(path));
  }

  private boolean getBool(String path) {return (config.getBoolean(path));}

  public FileConfiguration getConfig() {
    return config;
  }

  //  >>>>>>> PLUGIN <<<<<<<
  public String getPluginName() {
    return pluginName;
  }
  public String getPrefix() {
    return prefix;
  }
  public String getBroadcastprefix() {
    return broadcastprefix;
  }

  //  >>>>>> MESSAGE <<<<<<
  public String getErrorConsole() {
    return errorConsole;
  }
  public String getErrorCommand() {
    return errorCommand;
  }

  //  >>>>>> PERMISSION <<<<<<
  public String getPermHote() {
    return permHote;
  }
  public String getPermDev() {
    return permDev;
  }

  //  >>>>>>>> GUI <<<<<<<<
  public String getPrefixGUIName() {
    return prefixGUIName;
  }
  public String setPrefixGUIName(String prefixGUIName) {
    return this.prefixGUIName = prefixGUIName;
  }

  //  >>>>>>>> SQL <<<<<<<<
  public String getHost() {
    return host;
  }
  public String getDbName() {
    return dbName;
  }
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public String getDefaultPrefix() {
    return defaultPrefix;
  }
  public int getPort() {
    return port;
  }

  //  >>>>>>>> API <<<<<<<<

  public boolean isEditMode() {
    return editMode;
  }
  public boolean isDefaultGui() {
    return defaultGui;
  }
  public boolean isDefaultItems() {
    return defaultItems;
  }
  public boolean isBuildMode() {
    return buildMode;
  }
  public boolean isRessourcepack() {
    return ressourcepack;
  }


  //  >>>>>>>> VOICE <<<<<<<<
  public boolean isVoiceWallMode() {
    return voiceWallMode;
  }
  public boolean isVoiceMode() {
    return voiceMode;
  }
  public int getVoiceDistanceMax() {
    return voiceDistanceMax;
  }

}
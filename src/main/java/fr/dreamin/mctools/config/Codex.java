package fr.dreamin.mctools.config;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.lang.Lang;
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

  //  >>>>>>>> SQL <<<<<<<<
  @Getter @Setter
  private String host, dbName, username, password, defaultPrefix;
  @Getter @Setter
  private int port;
  @Getter @Setter
  private Lang defaultLang;

  // >>>>>>>> API <<<<<<<<
  @Getter @Setter
  private boolean editMode = false, defaultGui = false, defaultItems = false, buildMode = false, pack = false;

  //  >>>>>>>> VOICE <<<<<<<<
  @Getter @Setter
  private boolean voiceMode, voiceWallMode;
  @Getter @Setter
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
    pack = getBool("mcTools.ressourcepack");
    defaultLang = Lang.valueOf(getStr("mcTools.defaultLang"));
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
}
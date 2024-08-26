package fr.dreamin.mctools.config;

import fr.dreamin.mctools.McTools;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Codex {
  @Getter private FileConfiguration config;

  //  >>>>>>> GENERAL <<<<<<<

  @Getter @Setter private String pluginName, prefix, broadcastprefix, resourcePackUrl;
  @Getter @Setter private boolean resourcePack;

  public Codex() {
    this.config = McTools.getInstance().getConfig();
    pluginName = "McTools";
    initGlobal();
  }

  public void loadConf() {
    Bukkit.broadcastMessage(pluginName + " >> §r§aReload de la configuration...");

    this.config = McTools.getInstance().getConfig();
    initGlobal();

    Bukkit.broadcastMessage(pluginName + " >> §r§aConfiguration chargé");
  }


  private void initGlobal() {
    prefix = config.getString("general.prefix", "§8» §f");
    broadcastprefix = config.getString("general.broadcast-prefix", "[§c§lMcTools§r] ");
    resourcePack = config.getBoolean("general.resourcepack", false);
    resourcePackUrl = config.getString("general.resourcepack-url", null);
    if (resourcePackUrl.isEmpty()) resourcePackUrl = null;
  }

}
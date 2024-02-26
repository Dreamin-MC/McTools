package fr.dreamin.mctools.config;

import fr.dreamin.mctools.components.lang.LangMsg;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class LangManager {
  private File langFile;

  @Getter
  private YamlConfiguration langConfig;

  public LangManager(JavaPlugin plugin) {
    // Chargement du fichier lang.yml à partir des ressources
    InputStream inputStream = plugin.getResource("lang.yml");

    // Création d'une copie temporaire du fichier lang.yml
    File langFile = new File(plugin.getDataFolder(), "lang.yml");
    try {
      if (!langFile.exists()) {
        langFile.createNewFile();
        OutputStream outputStream = new FileOutputStream(langFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
          outputStream.write(buffer, 0, length);
        }
        outputStream.close();

        plugin.getLogger().info("Created lang.yml");

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Chargement de la configuration YAML depuis le fichier
    langConfig = YamlConfiguration.loadConfiguration(langFile);
    LangMsg.setConfig(langConfig);
  }
}
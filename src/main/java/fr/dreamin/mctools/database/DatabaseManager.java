package fr.dreamin.mctools.database;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.config.Codex;
import fr.dreamin.mctools.database.mysql.MysqlManager;
import fr.dreamin.mctools.database.sqlLite.SqlLiteManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;

public class DatabaseManager {

  @Getter
  private static Connection connection;

  public static void setConnection(Codex codex, DatabaseType type) {
    switch (type) {
      case MYSQL:

        if (codex.getHost() == null || codex.getUsername() == null || codex.getPassword() == null || codex.getDefaultPrefix() == null) {
          Bukkit.getConsoleSender().sendMessage(codex.getBroadcastprefix()+"§cErreur connection database.");
          Bukkit.getPluginManager().disablePlugin(McTools.getInstance());
        }

        new MysqlManager(codex);

        connection = MysqlManager.getConnection();
        break;
      case SQLITE:
        if (!McTools.getInstance().getDataFolder().exists()) McTools.getInstance().getDataFolder().mkdirs();
        new SqlLiteManager(codex);

        connection = SqlLiteManager.getConnection();
        break;
    }
  }


  public static void closeAllConnection() {
    MysqlManager.closeConnection();
    SqlLiteManager.closeConnection();
  }

}

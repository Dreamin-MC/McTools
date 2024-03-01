package fr.dreamin.mctools.database;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.config.Codex;
import fr.dreamin.mctools.database.mysql.MysqlManager;
import fr.dreamin.mctools.database.sqlLite.SqlLiteManager;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

public class DatabaseManager {

  @Getter
  private static Connection connection;

  public static void setConnection(Codex codex, DatabaseType type) {
    switch (type) {
      case MYSQL:
        new MysqlManager(codex.getHost(), codex.getPort(), codex.getDbName(), codex.getUsername(), codex.getPassword());

        connection = MysqlManager.getConnection();
        break;
      case SQLITE:
        if (!McTools.getInstance().getDataFolder().exists()) McTools.getInstance().getDataFolder().mkdirs();
        new SqlLiteManager(codex, McTools.getInstance().getDataFolder().getAbsolutePath());

        connection = SqlLiteManager.getConnection();
        break;
    }
  }


  public static void closeAllConnection() {
    MysqlManager.closeConnection();
    SqlLiteManager.closeConnection();
  }

}

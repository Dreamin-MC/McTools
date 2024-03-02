package fr.dreamin.mctools.database.mysql;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.config.Codex;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlManager {
  private final String host;
  private final String userName;
  private final String database;
  private final Integer port;
  private final String password;
  @Getter
  private static Connection connection;

  public MysqlManager(Codex codex) {
    this.host = codex.getMysqlHost();
    this.port = codex.getMysqlPort();
    this.userName = codex.getMysqlUsername();
    this.password = codex.getMysqlPassword();
    this.database = codex.getMysqlDbName();

    if (!isOnline()) {
      try {
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" +this.database, this.userName, this.password);
        Bukkit.getConsoleSender().sendMessage(codex.getBroadcastprefix()+"§aDatabase : Ok");

        MysqlCodex.checkIfExist(codex);

      } catch (SQLException e) {
        e.printStackTrace();
        Bukkit.getConsoleSender().sendMessage(codex.getBroadcastprefix()+"§cErreur de connexion à la database, le plugin va s'éteindre.");
        Bukkit.getPluginManager().disablePlugins();
      }
    }

  }

  public static void closeConnection() {
    if (isOnline())
      try {
        connection.close();
        Bukkit.getConsoleSender().sendMessage("§6[§cMcTools§6] §cDatabase : Bye");
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }
  private static boolean isOnline() {
    try {
      return connection != null && !connection.isClosed();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}

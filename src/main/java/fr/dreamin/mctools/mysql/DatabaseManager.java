package fr.dreamin.mctools.mysql;

import fr.dreamin.mctools.McTools;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
  private final McTools main;
  private final String host;
  private final String userName;
  private final String database;
  private final Integer port;
  private final String password;
  private static Connection connection;

  public DatabaseManager(String host, Integer port, String database, String userName, String password) {
    this.main = McTools.getInstance();
    this.host = host;
    this.port = port;
    this.userName = userName;
    this.password = password;
    this.database = database;
  }
  public static Connection getConnection() {
    return connection;
  }

  public void connection() {
    if (!isOnline()) {
      try {
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" +this.database, this.userName, this.password);
        Bukkit.getConsoleSender().sendMessage(McTools.getCodex().getBroadcastprefix()+"§aDatabase : Ok");
      } catch (SQLException e) {
        e.printStackTrace();
        Bukkit.getConsoleSender().sendMessage(McTools.getCodex().getBroadcastprefix()+"§cErreur de connexion à la database, le plugin va s'éteindre.");
        Bukkit.getPluginManager().disablePlugins();
      }
    }
  }

  public void deconnexion() {
    if (isOnline())
      try {
        connection.close();
        Bukkit.getConsoleSender().sendMessage("§6[§cTnT-Tag§6] §cDatabase : Bye");
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }
  private boolean isOnline() {
    try {
      return connection != null && !connection.isClosed();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}

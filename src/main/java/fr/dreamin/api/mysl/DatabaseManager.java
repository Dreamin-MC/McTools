package fr.dreamin.api.mysl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

  @Getter @Setter String label;
  @Getter @Setter private String host;
  @Getter @Setter private String userName;
  @Getter @Setter private String database;
  @Getter @Setter private Integer port;
  @Getter @Setter private String password;
  @Getter private static Connection connection;

  public DatabaseManager(String label, String host, Integer port, String database, String userName, String password) {
    this.label = label;
    this.host = host;
    this.port = port;
    this.userName = userName;
    this.password = password;
    this.database = database;

    connection();
  }

  public void connection() {
    if (!isOnline()) {
      try {
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" +this.database, this.userName, this.password);
        Bukkit.getConsoleSender().sendMessage("[§d§l"+label+"§r] §aDatabase : ON " + this.host + ":" + this.port + "/" + this.database);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void deconnexion() {
    if (isOnline())
      try {
        connection.close();
        Bukkit.getConsoleSender().sendMessage("[§d§l+label+§r] §aDatabase : OFF");
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }

  public boolean isOnline() {
    try {
      if (connection == null || connection.isClosed())
        return false;
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void switchConnection(String host, Integer port, String database, String userName, String password) {
    this.host = host;
    this.port = port;
    this.userName = userName;
    this.password = password;
    this.database = database;

    reloadConnection();
  }

  public void reloadConnection() {
    if (isOnline()) deconnexion();
    connection();
  }

}


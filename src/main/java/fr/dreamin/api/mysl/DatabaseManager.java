package fr.dreamin.api.mysl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the connection to a MySQL database, allowing to connect, disconnect, and switch between databases.
 */
@Getter @Setter
public class DatabaseManager {

  private String label;
  private String host;
  private String userName;
  private String database;
  private Integer port;
  private String password;
  private Connection connection; // Made instance-specific instead of static

  /**
   * Constructs a DatabaseManager object and attempts to connect to the database immediately.
   *
   * @param label The label used to identify this connection.
   * @param host The database host.
   * @param port The port for the database connection.
   * @param database The database name.
   * @param userName The username for the database.
   * @param password The password for the database.
   */
  public DatabaseManager(final String label, final String host, final Integer port, final String database, final String userName, final String password) {
    this.label = label;
    this.host = host;
    this.port = port;
    this.userName = userName;
    this.password = password;
    this.database = database;

    connection();
  }

  /**
   * Establishes a connection to the database if not already connected.
   */
  public void connection() {
    if (!isOnline()) {
      try {
        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
        this.connection = DriverManager.getConnection(url, this.userName, this.password);
        Bukkit.getConsoleSender().sendMessage("[§d§l" + label + "§r] §aDatabase: ON " + this.host + ":" + this.port + "/" + this.database);
      } catch (SQLException e) {
        Bukkit.getConsoleSender().sendMessage("[§d§l" + label + "§r] §cFailed to connect to the database.");
        e.printStackTrace();
      }
    }
  }

  /**
   * Closes the connection to the database if it is currently online.
   */
  public void deconnexion() {
    if (isOnline()) {
      try {
        connection.close();
        connection = null; // Ensure the connection object is cleared
        Bukkit.getConsoleSender().sendMessage("[§d§l" + label + "§r] §aDatabase: OFF");
      } catch (SQLException e) {
        Bukkit.getConsoleSender().sendMessage("[§d§l" + label + "§r] §cFailed to close the database connection.");
        e.printStackTrace();
      }
    }
  }

  /**
   * Checks if the connection to the database is currently open and online.
   *
   * @return true if the connection is online, false otherwise.
   */
  public boolean isOnline() {
    try {
      return connection != null && !connection.isClosed();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Switches the connection to a different database using new connection parameters.
   *
   * @param host The new database host.
   * @param port The new port for the database connection.
   * @param database The new database name.
   * @param userName The new username for the database.
   * @param password The new password for the database.
   */
  public void switchConnection(final String host, final Integer port, final String database, final String userName, final String password) {
    this.host = host;
    this.port = port;
    this.userName = userName;
    this.password = password;
    this.database = database;

    reloadConnection();
  }

  /**
   * Reloads the connection by closing the current connection (if online) and reconnecting with the current parameters.
   */
  public void reloadConnection() {
    if (isOnline()) deconnexion();
    connection();
  }
}

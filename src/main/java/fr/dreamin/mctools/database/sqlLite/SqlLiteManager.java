package fr.dreamin.mctools.database.sqlLite;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.config.Codex;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlLiteManager {

  @Getter
  private static Connection connection;

  public SqlLiteManager(Codex codex, String path) {
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:" + path + "/" + codex.getSqlName() + ".db");
      SqlLiteCodex.checkIfExist();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to connect to the database ! " + e.getMessage());
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

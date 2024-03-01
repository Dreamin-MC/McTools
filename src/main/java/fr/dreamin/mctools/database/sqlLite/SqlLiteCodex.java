package fr.dreamin.mctools.database.sqlLite;

import java.sql.SQLException;
import java.sql.Statement;

public class SqlLiteCodex {

  public static void checkIfExist() {
    try(Statement statement = SqlLiteManager.getConnection().createStatement()){
      statement.execute("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid TEXT NOT NULL, lang TEXT NOT NULL, volAction INTEGER NOT NULL DEFAULT 80,  volAmbien INTEGER NOT NULL DEFAULT 80, volEvent INTEGER NOT NULL DEFAULT 80)");

      statement.execute("CREATE TABLE IF NOT EXISTS buildtag (id INTEGER PRIMARY KEY AUTOINCREMENT, keyName TEXT NOT NULL DEFAULT 16, value TEXT NOT NULL DEFAULT 16, categoryId INTEGER NOT NULL)");

      statement.execute("CREATE TABLE IF NOT EXISTS buildcategory (id INTEGER PRIMARY KEY AUTOINCREMENT, value TEXT NOT NULL DEFAULT 16)");

      statement.execute("CREATE TABLE IF NOT EXISTS door (id INTEGER PRIMARY KEY AUTOINCREMENT, modelName TEXT NOT NULL, worldName TEXT NOT NULL, location JSON NOT NULL, blockable BOOLEAN NOT NULL, cuboide JSON NOT NULL, lockable BOOLEAN NOT NULL, clickable BOOLEAN NOT NULL)");

      statement.execute("CREATE TABLE IF NOT EXISTS interact (id INTEGER PRIMARY KEY AUTOINCREMENT, modelName TEXT NOT NULL, worldName TEXT NOT NULL, location JSON NOT NULL, clickable BOOLEAN NOT NULL)");

    } catch (SQLException e) {
      System.out.println("Error get table sql : " + e.getMessage());
    }
  }

}

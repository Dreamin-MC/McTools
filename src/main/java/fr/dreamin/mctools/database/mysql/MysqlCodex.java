package fr.dreamin.mctools.database.mysql;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.config.Codex;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlCodex {


  public static void checkIfExist(Codex codex){
    try {
      PreparedStatement loc1 = MysqlManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + codex.getDefaultPrefix() + "user (id int(11) PRIMARY KEY AUTO_INCREMENT,uuid varchar(255) NOT NULL, ip text NOT NULL, lang varchar(5) NOT NULL, volAction int(11) NOT NULL DEFAULT 80, volAmbien int(11) NOT NULL DEFAULT 80, volEvent int(11) NOT NULL DEFAULT 80) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci");
      loc1.execute();
      loc1.close();

      PreparedStatement loc2 = MysqlManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + codex.getDefaultPrefix() + "buildtag (id int(11) PRIMARY KEY AUTO_INCREMENT, keyName varchar(255) NOT NULL, value varchar(255) NOT NULL, categoryId int(11) NOT NULL)");
      loc2.execute();
      loc2.close();

      PreparedStatement loc3 = MysqlManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + codex.getDefaultPrefix() + "buildcategory (id int(11) PRIMARY KEY AUTO_INCREMENT, value varchar(255) NOT NULL)");
      loc3.execute();
      loc3.close();

      PreparedStatement loc4 = MysqlManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + codex.getDefaultPrefix() + "door (id int(11) PRIMARY KEY AUTO_INCREMENT, modelName varchar(255) NOT NULL, worldName varchar(255) NOT NULL, location json NOT NULL, blockable boolean(1) NOT NULL, cuboide json NOT NULL, lockable boolean(1) NOT NULL, clickable boolean(1) NOT NULL)");
      loc4.execute();
      loc4.close();

      PreparedStatement loc5 = MysqlManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + codex.getDefaultPrefix() + "interact (id int(11) PRIMARY KEY AUTO_INCREMENT, modelName varchar(255) NOT NULL, worldName varchar(255) NOT NULL, location json NOT NULL, clickable boolean(1) NOT NULL)");
      loc5.execute();
      loc5.close();

    }catch (SQLException e){
      System.out.println("Error get table mysql : " + e.getMessage());
    }
  }
}
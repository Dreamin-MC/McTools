package fr.dreamin.dreamintools.mysql;

import fr.dreamin.dreamintools.McTools;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseCodex {
  public static void checkIfExist(){
    try {
      PreparedStatement loc1 = DatabaseManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + McTools.getCodex().getDefaultPrefix() + "user (id int(11) NOT NULL,uuid varchar(255) NOT NULL,action int(11) NOT NULL DEFAULT 80,ambient int(11) NOT NULL DEFAULT 80,event int(11) NOT NULL DEFAULT 80) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci");
      loc1.execute();
      loc1.close();

      PreparedStatement loc2 = DatabaseManager.getConnection().prepareStatement("ALTER TABLE " + McTools.getCodex().getDefaultPrefix() + "user ADD PRIMARY KEY (id);");
      loc2.execute();
      loc2.close();

      PreparedStatement loc3 = DatabaseManager.getConnection().prepareStatement("ALTER TABLE " + McTools.getCodex().getDefaultPrefix() + "user MODIFY id int(11) NOT NULL AUTO_INCREMENT; COMMIT;");
      loc3.execute();
      loc3.close();

    }catch (SQLException e){
      e.printStackTrace();
    }
  }
}


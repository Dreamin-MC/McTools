package fr.dreamin.dreamintools.mysql.fetcher.displayFetcher;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.mysql.DatabaseManager;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DisplayFetcher {

  public static void addDisplay(String display, Location location) {
    try  {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO  "+ McTools.getCodex().getDefaultPrefix() +"display (worldName, display) VALUES (?,?)");
      preparedStatement.setString(2, location.getWorld().getName());
      preparedStatement.setString(1, display);
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}

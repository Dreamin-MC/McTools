package fr.dreamin.mctools.database.fetcher.interactFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.json.JsonManager;
import fr.dreamin.mctools.components.game.manager.InteractManager;
import fr.dreamin.mctools.database.DatabaseManager;
import fr.dreamin.mctools.database.DatabaseType;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractFetcher {

  public static void addInteract(String modelName, Location location, boolean clickable) {
    try  {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO  "+ McTools.getCodex().getDefaultPrefix() +"interact (modelName,worldName, x, y ,z ,yaw ,pitch, clickable) VALUES (?,?,?,?,?,?,?, ?)");
      preparedStatement.setString(1, modelName);
      preparedStatement.setString(2, location.getWorld().getName());
      preparedStatement.setDouble(3, location.getX());
      preparedStatement.setDouble(4, location.getY());
      preparedStatement.setDouble(5, location.getZ());
      preparedStatement.setFloat(6, location.getYaw());
      preparedStatement.setFloat(7, location.getPitch());
      preparedStatement.setBoolean(8, clickable);
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void getAllInteractByWorld(World world) {
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"interact WHERE worldName = ?");
      preparedStatement.setString(1, world.getName());

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()) {
        InteractManager.newInteract(rs.getString("modelName"), JsonManager.convertJsonToLocationWithYaAndPitch(rs.getString("location"), world) , rs.getBoolean("clickable"), false);
      }

      preparedStatement.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}

package fr.dreamin.mctools.database.fetcher.doorFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.cuboide.Cuboide;
import fr.dreamin.mctools.api.json.JsonManager;
import fr.dreamin.mctools.components.game.manager.DoorManager;
import fr.dreamin.mctools.database.DatabaseManager;
import fr.dreamin.mctools.database.DatabaseType;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoorFetcher {

  public static void addDoor(String modelName, Location location, Cuboide cuboide, Boolean locked, Boolean blockable, Boolean clickable) {
    try  {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO  "+ McTools.getCodex().getDefaultPrefix() +"door (modelName,worldName, x, y ,z ,yaw ,pitch, clickable) VALUES (?,?,?,?,?,?,?, ?)");
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

  public static void getAllDoorByWorld(World world) {
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"door WHERE worldName = ?");
      preparedStatement.setString(1, world.getName());

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()) {

        Cuboide cuboide = null;

        if (rs.getBoolean("blockable")) cuboide = JsonManager.convertJsonToCuboid(world, rs.getString("cuboide"), true);

        DoorManager.newDoor(
          rs.getInt("id"),
          rs.getString("modelName"),
          JsonManager.convertJsonToLocationWithYaAndPitch(rs.getString("location"), world),
          cuboide,
          rs.getBoolean("lockable"),
          rs.getBoolean("blockable"),
          rs.getBoolean("clickable"),
          false
        );
      }

      preparedStatement.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}

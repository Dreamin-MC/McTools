package fr.dreamin.mctools.components.game.manager;

import com.ticxo.modelengine.api.model.ActiveModel;
import fr.dreamin.mctools.api.cuboide.Cuboide;
import fr.dreamin.mctools.api.door.Door;
import fr.dreamin.mctools.mysql.fetcher.doorFetcher.DoorFetcher;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class DoorManager {
  private static List<Door> doors = new ArrayList<>();

  public static List<Door> getDoors() {
    return doors;
  }

  public static void newDoor(int id, String modelName, Location location, Cuboide cuboide, Boolean locked, Boolean blockable, Boolean clickable, Boolean save) {
    doors.add(new Door(id, modelName, location, cuboide, locked, blockable, clickable));
    if (save) DoorFetcher.addDoor(modelName, location, cuboide, locked, blockable, clickable);
  }

  public static void removeDoor(Door door) {
    doors.remove(door);
  }

  public static Door getDoor(ActiveModel activeModel) {
    for (Door door : doors) {
      if (door.getActiveModel().equals(activeModel)) {
        return door;
      }
    }
    return null;
  }

}

package fr.dreamin.api.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.dreamin.api.cuboide.Cuboid;
import fr.dreamin.api.cuboide.MemoryCuboid;
import org.bukkit.Location;

import java.util.List;

public class JsonCreator {

  private static final Gson gson = new Gson();

  // ----------------------------------------------------------------
  // LOCATIONS
  // ----------------------------------------------------------------

  public static String createJsonFromLocations(List<Location> locations, boolean includeYawAndPitch) {
    JsonArray jsonArray = new JsonArray();
    locations.forEach(loc -> jsonArray.add(createJsonObjectFromLocation(loc, includeYawAndPitch)));
    return gson.toJson(jsonArray);
  }

  public static String createJsonFromLocation(Location location, boolean includeYawAndPitch) {
    JsonObject jsonObject = createJsonObjectFromLocation(location, includeYawAndPitch);
    return gson.toJson(jsonObject);
  }

  // ----------------------------------------------------------------
  // CUBOIDS
  // ----------------------------------------------------------------

  public static String createJsonFromCuboide(Cuboid cuboid) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.add("locA", createJsonObjectFromLocation(cuboid.getLocA(), false));
    jsonObject.add("locB", createJsonObjectFromLocation(cuboid.getLocB(), false));

    return gson.toJson(jsonObject);
  }

  // ----------------------------------------------------------------
  // UTILITY FUNCTIONS
  // ----------------------------------------------------------------

  private static JsonObject createJsonObjectFromLocation(Location loc, boolean includeYawAndPitch) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("x", loc.getX());
    jsonObject.addProperty("y", loc.getY());
    jsonObject.addProperty("z", loc.getZ());
    if (includeYawAndPitch) {
      jsonObject.addProperty("yaw", loc.getYaw());
      jsonObject.addProperty("pitch", loc.getPitch());
    }
    return jsonObject;
  }

}

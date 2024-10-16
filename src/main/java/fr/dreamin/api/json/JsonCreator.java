package fr.dreamin.api.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.dreamin.api.cuboid.Cuboid;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JsonCreator {

  private static final Gson gson = new Gson();

  // ----------------------------------------------------------------
  // LOCATIONS
  // ----------------------------------------------------------------

  public static JsonArray createJsonFromLocations(@NotNull List<Location> locations, @NotNull boolean includeYawAndPitch) {
    JsonArray jsonArray = new JsonArray();
    locations.forEach(loc -> jsonArray.add(createJsonFromLocation(loc, includeYawAndPitch)));
    return jsonArray;
  }

  public static JsonObject createJsonFromLocation(@NotNull Location location, @NotNull boolean includeYawAndPitch) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("x", location.getX());
    jsonObject.addProperty("y", location.getY());
    jsonObject.addProperty("z", location.getZ());

    if (includeYawAndPitch) {
      jsonObject.addProperty("yaw", location.getYaw());
      jsonObject.addProperty("pitch", location.getPitch());
    }

    return jsonObject;
  }

  // ----------------------------------------------------------------
  // CUBOIDS
  // ----------------------------------------------------------------

  public static JsonObject createJsonFromCuboid(@NotNull Cuboid cuboid) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.add("locA", createJsonFromLocation(cuboid.getLocA(), false));
    jsonObject.add("locB", createJsonFromLocation(cuboid.getLocB(), false));

    return jsonObject;
  }

}

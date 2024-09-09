package fr.dreamin.api.json;

import com.google.gson.*;
import fr.dreamin.api.cuboide.Cuboide;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class JsonGetter {

  // ----------------------------------------------------------------
  // LOCATIONS
  // ----------------------------------------------------------------

  public static Location getLocationFromJson(String json, String sectionName, World w) {
    try {
      JsonObject jsonObjectLocations = JsonParser.parseString(json).getAsJsonObject();
      JsonElement jsonElement = jsonObjectLocations.get(sectionName);

      if (jsonElement != null && jsonElement.isJsonObject()) {
        return parseLocation(jsonElement.getAsJsonObject(), w);
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Location getLocationFromJson(String json, World w) {
    try {
      JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
      return parseLocation(jsonObject, w);
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<Location> getLocationsFromJson(String json, World w) {
    List<Location> locations = new ArrayList<>();

    try {
      JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
      for (JsonElement element : jsonArray) {
        if (element.isJsonObject()) {
          Location loc = parseLocation(element.getAsJsonObject(), w);
          locations.add(loc);
        }
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return locations;
  }

  // ----------------------------------------------------------------
  // CUBOIDS
  // ----------------------------------------------------------------

  public static Cuboide getCuboideFromJson(World world, String json, boolean saveBlock) {
    try {
      JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

      Location locA = parseLocation(jsonObject.getAsJsonObject("locA"), world);
      Location locB = parseLocation(jsonObject.getAsJsonObject("locB"), world);

      return new Cuboide(locA, locB, saveBlock);
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  // ----------------------------------------------------------------
  // UTILITY FUNCTIONS
  // ----------------------------------------------------------------

  private static Location parseLocation(JsonObject locObject, World w) {
    double x = locObject.get("x").getAsDouble();
    double y = locObject.get("y").getAsDouble();
    double z = locObject.get("z").getAsDouble();
    float yaw = locObject.has("yaw") ? locObject.get("yaw").getAsFloat() : 0.0f;
    float pitch = locObject.has("pitch") ? locObject.get("pitch").getAsFloat() : 0.0f;
    return new Location(w, x, y, z, yaw, pitch);
  }
}

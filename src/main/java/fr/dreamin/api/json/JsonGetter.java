package fr.dreamin.api.json;

import com.google.gson.*;
import fr.dreamin.api.cuboide.Cuboid;
import fr.dreamin.api.cuboide.MemoryCuboid;
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
      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {
        return parseLocation(JsonParser.parseString(JsonParser.parseString(json).getAsJsonObject().get(sectionName).getAsString()).getAsJsonObject(), w);
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

  public static List<Location> getLocationsFromJson(String json, String sectionName, World w) {
    List<Location> locations = new ArrayList<>();
    try {

      JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
      JsonElement jsonElement = jsonObject.get(sectionName);


      if (jsonElement != null && jsonElement.isJsonArray()) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonArray) {
          if (element.isJsonObject()) {
            Location loc = parseLocation(element.getAsJsonObject(), w);
            locations.add(loc);
          } else if (element.isJsonPrimitive()) {

            JsonObject locObject = JsonParser.parseString(element.getAsString()).getAsJsonObject();
            Location loc = parseLocation(locObject, w);
            locations.add(loc);
          }
        }
      }

      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {

        String jsonString = jsonElement.getAsString();
        JsonElement parsedElement = JsonParser.parseString(jsonString);

        if (parsedElement.isJsonArray()) {
          JsonArray jsonArray = parsedElement.getAsJsonArray();
          for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
              Location loc = parseLocation(element.getAsJsonObject(), w);
              locations.add(loc);
            }
          }
        } else if (parsedElement.isJsonObject()) {
          Location loc = parseLocation(parsedElement.getAsJsonObject(), w);
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

  public static Cuboid getCuboidFromJson(World world, String json) {
    try {
      JsonElement jsonElement = JsonParser.parseString(json).getAsJsonObject();
      if (jsonElement != null && jsonElement.isJsonObject()) return new Cuboid(parseLocation(jsonElement.getAsJsonObject().getAsJsonObject("locA"), world), parseLocation(jsonElement.getAsJsonObject().getAsJsonObject("locB"), world));
      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {
        JsonObject jsonObject = JsonParser.parseString(JsonParser.parseString(json).getAsString()).getAsJsonObject();
        return new Cuboid(parseLocation(jsonObject.getAsJsonObject("locA"), world), parseLocation(jsonObject.getAsJsonObject("locB"), world));
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Cuboid getCuboidFromJson(World world, String json, String sectionName) {
    try {
      JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
      JsonElement jsonElement = jsonObject.get(sectionName);

      if (jsonElement != null && jsonElement.isJsonObject()) {
        JsonObject jsonObjectCuboid = jsonElement.getAsJsonObject();
        return new Cuboid(parseLocation(jsonObjectCuboid.getAsJsonObject("locA"), world), parseLocation(jsonObjectCuboid.getAsJsonObject("locB"), world));
      }
      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {
        JsonObject jsonObjectCuboid = JsonParser.parseString(JsonParser.parseString(json).getAsJsonObject().get(sectionName).getAsString()).getAsJsonObject();
        return new Cuboid(parseLocation(jsonObjectCuboid.getAsJsonObject("locA"), world), parseLocation(jsonObjectCuboid.getAsJsonObject("locB"), world));
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static MemoryCuboid getMemoryCuboidFromJson(World world, String json) {
    try {
      JsonElement jsonElement = JsonParser.parseString(json).getAsJsonObject();
      if (jsonElement != null && jsonElement.isJsonObject()) return new MemoryCuboid(parseLocation(jsonElement.getAsJsonObject().getAsJsonObject("locA"), world), parseLocation(jsonElement.getAsJsonObject().getAsJsonObject("locB"), world));
      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {
        JsonObject jsonObject = JsonParser.parseString(JsonParser.parseString(json).getAsJsonObject().getAsString()).getAsJsonObject();
        return new MemoryCuboid(parseLocation(jsonObject.getAsJsonObject("locA"), world), parseLocation(jsonObject.getAsJsonObject("locB"), world));
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static MemoryCuboid getMemoryCuboidFromJson(World world, String json, String sectionName) {
    try {
      JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
      JsonElement jsonElement = jsonObject.get(sectionName);

      if (jsonElement != null && jsonElement.isJsonObject()) {
        JsonObject jsonObjectCuboid = jsonElement.getAsJsonObject();
        return new MemoryCuboid(parseLocation(jsonObjectCuboid.getAsJsonObject("locA"), world), parseLocation(jsonObjectCuboid.getAsJsonObject("locB"), world));
      }
      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {
        JsonObject jsonObjectCuboid = JsonParser.parseString(JsonParser.parseString(json).getAsJsonObject().get(sectionName).getAsString()).getAsJsonObject();
        return new MemoryCuboid(parseLocation(jsonObjectCuboid.getAsJsonObject("locA"), world), parseLocation(jsonObjectCuboid.getAsJsonObject("locB"), world));
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<Cuboid> getCuboidsFromJson(String json, String sectionName, World w) {
    List<Cuboid> result = new ArrayList<>();
    try {

      JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
      JsonElement jsonElement = jsonObject.get(sectionName);


      if (jsonElement != null && jsonElement.isJsonArray()) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonArray) {
          if (element.isJsonObject()) {
            Cuboid cuboid = new Cuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("LocB"), w));
            result.add(cuboid);
          } else if (element.isJsonPrimitive()) {
            JsonObject locObject = JsonParser.parseString(element.getAsString()).getAsJsonObject();
            Cuboid cuboid = new Cuboid(parseLocation(locObject.getAsJsonObject("LocA"), w), parseLocation(locObject.getAsJsonObject("LocB"), w));
            result.add(cuboid);
          }
        }
      }

      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {

        String jsonString = jsonElement.getAsString();
        JsonElement parsedElement = JsonParser.parseString(jsonString);

        if (parsedElement.isJsonArray()) {
          JsonArray jsonArray = parsedElement.getAsJsonArray();
          for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
              Cuboid cuboid = new Cuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("LocB"), w));
              result.add(cuboid);
            }
          }
        } else if (parsedElement.isJsonObject()) {
          Cuboid cuboid = new Cuboid(parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("LocB"), w));
          result.add(cuboid);
        }
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static List<Cuboid> getCuboidsFromJson(String json, World w) {
    List<Cuboid> rs = new ArrayList<>();

    try {
      JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
      for (JsonElement element : jsonArray) {
        if (element.isJsonObject()) {
          Cuboid cuboid = new Cuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("LocB"), w));
          rs.add(cuboid);
        }
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }

    return rs;
  }

  public static List<MemoryCuboid> getMemoryCuboidsFromJson(String json, String sectionName, World w) {
    List<MemoryCuboid> result = new ArrayList<>();
    try {

      JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
      JsonElement jsonElement = jsonObject.get(sectionName);


      if (jsonElement != null && jsonElement.isJsonArray()) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonArray) {
          if (element.isJsonObject()) {
            MemoryCuboid cuboid = new MemoryCuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("LocB"), w));
            result.add(cuboid);
          } else if (element.isJsonPrimitive()) {
            JsonObject locObject = JsonParser.parseString(element.getAsString()).getAsJsonObject();
            MemoryCuboid cuboid = new MemoryCuboid(parseLocation(locObject.getAsJsonObject("LocA"), w), parseLocation(locObject.getAsJsonObject("LocB"), w));
            result.add(cuboid);
          }
        }
      }

      else if (jsonElement != null && jsonElement.isJsonPrimitive()) {

        String jsonString = jsonElement.getAsString();
        JsonElement parsedElement = JsonParser.parseString(jsonString);

        if (parsedElement.isJsonArray()) {
          JsonArray jsonArray = parsedElement.getAsJsonArray();
          for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
              MemoryCuboid cuboid = new MemoryCuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("LocB"), w));
              result.add(cuboid);
            }
          }
        } else if (parsedElement.isJsonObject()) {
          MemoryCuboid cuboid = new MemoryCuboid(parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("LocB"), w));
          result.add(cuboid);
        }
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static List<MemoryCuboid> getMemoryCuboidsFromJson(String json, World w) {
    List<MemoryCuboid> rs = new ArrayList<>();

    try {
      JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
      for (JsonElement element : jsonArray) {
        if (element.isJsonObject()) {
          MemoryCuboid cuboid = new MemoryCuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("LocA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("LocB"), w));
          rs.add(cuboid);
        }
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }

    return rs;
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

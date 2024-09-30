package fr.dreamin.api.json;

import com.google.gson.*;
import fr.dreamin.api.cuboid.Cuboid;
import fr.dreamin.api.cuboid.MemoryCuboid;
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
      JsonElement jsonElement = JsonParser.parseString(json);

      // Vérifie si l'élément JSON est bien un objet
      if (jsonElement != null && jsonElement.isJsonObject()) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        // Vérifie si "locA" et "locB" sont des objets JSON et les parse
        if (jsonObject.has("locA") && jsonObject.get("locA").isJsonObject() &&
          jsonObject.has("locB") && jsonObject.get("locB").isJsonObject()) {
          return new Cuboid(parseLocation(jsonObject.getAsJsonObject("locA"), world), parseLocation(jsonObject.getAsJsonObject("locB"), world));
        } else throw new IllegalArgumentException("Missing or invalid 'locA' or 'locB' as JsonObject in JSON");
      }

      // Si c'est un JsonPrimitive
      if (jsonElement != null && jsonElement.isJsonPrimitive()) {
        // On convertit le JsonPrimitive en JsonObject si nécessaire (selon le format attendu)
        String primitiveValue = jsonElement.getAsString(); // Par exemple, une chaîne JSON
        JsonObject jsonObject = JsonParser.parseString(primitiveValue).getAsJsonObject();
        if (jsonObject.has("locA") && jsonObject.get("locA").isJsonObject() &&
          jsonObject.has("locB") && jsonObject.get("locB").isJsonObject()) {
          return new Cuboid(parseLocation(jsonObject.getAsJsonObject("locA"), world), parseLocation(jsonObject.getAsJsonObject("locB"), world));
        } else throw new IllegalArgumentException("Missing or invalid 'locA' or 'locB' as JsonObject in Primitive JSON");
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
            Cuboid cuboid = new Cuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("locB"), w));
            result.add(cuboid);
          } else if (element.isJsonPrimitive()) {
            JsonObject locObject = JsonParser.parseString(element.getAsString()).getAsJsonObject();
            Cuboid cuboid = new Cuboid(parseLocation(locObject.getAsJsonObject("locA"), w), parseLocation(locObject.getAsJsonObject("locB"), w));
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
              Cuboid cuboid = new Cuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("locB"), w));
              result.add(cuboid);
            }
          }
        } else if (parsedElement.isJsonObject()) {
          Cuboid cuboid = new Cuboid(parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("locB"), w));
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
          Cuboid cuboid = new Cuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("locB"), w));
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
            MemoryCuboid cuboid = new MemoryCuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("locB"), w));
            result.add(cuboid);
          } else if (element.isJsonPrimitive()) {
            JsonObject locObject = JsonParser.parseString(element.getAsString()).getAsJsonObject();
            MemoryCuboid cuboid = new MemoryCuboid(parseLocation(locObject.getAsJsonObject("locA"), w), parseLocation(locObject.getAsJsonObject("locB"), w));
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
              MemoryCuboid cuboid = new MemoryCuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("locB"), w));
              result.add(cuboid);
            }
          }
        } else if (parsedElement.isJsonObject()) {
          MemoryCuboid cuboid = new MemoryCuboid(parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(parsedElement.getAsJsonObject().getAsJsonObject("locB"), w));
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
          MemoryCuboid cuboid = new MemoryCuboid(parseLocation(element.getAsJsonObject().getAsJsonObject("locA"), w), parseLocation(element.getAsJsonObject().getAsJsonObject("locB"), w));
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

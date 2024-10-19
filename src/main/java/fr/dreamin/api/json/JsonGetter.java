package fr.dreamin.api.json;

import com.google.gson.*;
import fr.dreamin.api.cuboid.Cuboid;
import fr.dreamin.api.cuboid.MemoryCuboid;
import fr.dreamin.api.entity.interaction.InteractionData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class JsonGetter {

  // ----------------------------------------------------------------
  // LOCATIONS
  // ----------------------------------------------------------------

  public static Location getLocationFromJson(@NotNull JsonObject object, @NotNull World w) {
    try {

      double x = object.get("x").getAsDouble();
      double y = object.get("y").getAsDouble();
      double z = object.get("z").getAsDouble();
      float yaw = object.has("yaw") ? object.get("yaw").getAsFloat() : 0.0f;
      float pitch = object.has("pitch") ? object.get("pitch").getAsFloat() : 0.0f;

      return new Location(w, x, y, z, yaw, pitch);

    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<Location> getLocationsFromJson(@NotNull JsonArray array, @NotNull World w) {
    List<Location> locations = new ArrayList<>();
    try {
      array.forEach(e -> {
        if (e.isJsonObject()) {
          Location loc = getLocationFromJson(e.getAsJsonObject(), w);
          locations.add(loc);
        }
      });
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return locations;
  }


  // ----------------------------------------------------------------
  // CUBOIDS
  // ----------------------------------------------------------------

  public static Cuboid getCuboidFromJson(@NotNull JsonObject object, @NotNull World w) {
    try {
      if (object.has("locA") && object.get("locA").isJsonObject() && object.has("locB") && object.get("locB").isJsonObject()) {
        Location locA = getLocationFromJson(object.get("locA").getAsJsonObject(), w);
        Location locB = getLocationFromJson(object.get("locB").getAsJsonObject(), w);

        if (locA != null && locB != null) return new Cuboid(locA, locB);
      }
    } catch(JsonSyntaxException | IllegalStateException | NullPointerException e){
      e.printStackTrace();
    }
    return null;
  }
  public static MemoryCuboid getMemoryCuboidFromJson(@NotNull JsonObject object, @NotNull World w) {
    try {
      if (object.has("locA") && object.get("locA").isJsonObject() && object.has("locB") && object.get("locB").isJsonObject()) {
        Location locA = getLocationFromJson(object.get("locA").getAsJsonObject(), w);
        Location locB = getLocationFromJson(object.get("locB").getAsJsonObject(), w);

        if (locA != null && locB != null) return new MemoryCuboid(locA, locB);
      }
    } catch(JsonSyntaxException | IllegalStateException | NullPointerException e){
      e.printStackTrace();
    }
    return null;
  }

  public static List<Cuboid> getCuboidsFromJson(@NotNull JsonArray array, @NotNull World w) {
    List<Cuboid> rs = new ArrayList<>();
    try {
      array.forEach(e -> {
        if (e.isJsonObject()) {
          Cuboid cuboid = getCuboidFromJson(e.getAsJsonObject(), w);
          if (cuboid!= null) rs.add(cuboid);
        }
      });
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return rs;
  }
  public static List<MemoryCuboid> getMemoryCuboidsFromJson(@NotNull JsonArray array, @NotNull World w) {
    List<MemoryCuboid> rs = new ArrayList<>();
    try {
      array.forEach(e -> {
        if (e.isJsonObject()) {
          MemoryCuboid memoryCuboid = getMemoryCuboidFromJson(e.getAsJsonObject(), w);
          if (memoryCuboid != null) rs.add(memoryCuboid);
        }
      });
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return rs;
  }

  // ----------------------------------------------------------------
  // VECTORS
  // ----------------------------------------------------------------

  public static Vector getVectorFromJson(@NotNull JsonObject object, @NotNull NumberType type) {
    if (object.isEmpty()) return new Vector();
    try {
      switch (type) {
        case INT -> {
          int x = object.has("x") ? object.get("x").getAsInt() : 0;
          int y = object.has("y") ? object.get("y").getAsInt() : 0;
          int z = object.has("z") ? object.get("z").getAsInt() : 0;
          return new Vector(x, y, z);
        }
        case DOUBLE -> {
          double x = object.has("x") ? object.get("x").getAsDouble() : 0.0;
          double y = object.has("y") ? object.get("y").getAsDouble() : 0.0;
          double z = object.has("z") ? object.get("z").getAsDouble() : 0.0;
          return new Vector(x, y, z);
        }
        case FLOAT -> {
          float x = object.has("x") ? object.get("x").getAsFloat() : 0.0F;
          float y = object.has("y") ? object.get("y").getAsFloat() : 0.0F;
          float z = object.has("z") ? object.get("z").getAsFloat() : 0.0F;
          return new Vector(x, y, z);
        }
      }
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return new Vector();
  }
  public static List<Vector> getVectorsFromJson(@NotNull JsonArray array, @NotNull NumberType type) {
    List<Vector> rs = new ArrayList<>();
    try {
      array.forEach(e -> {
        if (e.isJsonObject()) {
          Vector vector = getVectorFromJson(e.getAsJsonObject(), type);
          if (vector != null) rs.add(vector);
        }
      });
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return rs;
  }

  public static Vector3f getVector3fFromJson(@NotNull JsonObject object) {
    if (object.isEmpty()) return new Vector3f();
    try {
      float x = object.has("x") ? object.get("x").getAsFloat() : 0.0f;
      float y = object.has("y") ? object.get("y").getAsFloat() : 0.0f;
      float z = object.has("z") ? object.get("z").getAsFloat() : 0.0f;
      return new Vector3f(x, y, z);
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return new Vector3f();
  }
  public static List<Vector3f> getVector3fsFromJson(@NotNull JsonArray array) {
    List<Vector3f> rs = new ArrayList<>();
    try {
      array.forEach(e -> {
        if (e.isJsonObject()) {
          Vector3f vector = getVector3fFromJson(e.getAsJsonObject());
          if (vector != null) rs.add(vector);
        }
      });
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return rs;
  }

  // ----------------------------------------------------------------
  // INTERACTION DATA
  // ----------------------------------------------------------------

  public static InteractionData getInteractionDataFromJson(@NotNull JsonObject object, @NotNull World w) {
    if (object.isEmpty()) return null;

    try {

      double x = object.has("x") ? object.get("x").getAsDouble() : 0.0;
      double y = object.has("y") ? object.get("y").getAsDouble() : 0.0;
      double z = object.has("z") ? object.get("z").getAsDouble() : 0.0;
      float yaw = object.has("yaw") ? object.get("yaw").getAsFloat() : 0.0f;
      float pitch = object.has("pitch") ? object.get("pitch").getAsFloat() : 0.0f;

      float height = object.has("height") ? object.get("height").getAsFloat() : 1.0f;
      float width = object.has("width") ? object.get("width").getAsFloat() : 1.0f;

      return new InteractionData(new Location(w, x, y, z, yaw, pitch), height, width);
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }

    return null;
  }
  public static List<InteractionData> getInteractionsDataFromJson(@NotNull JsonArray array, @NotNull World w) {
    List<InteractionData> result = new ArrayList<>();
    try {
      array.forEach(e -> {
        if (e.isJsonObject()) {
          InteractionData data = getInteractionDataFromJson(e.getAsJsonObject(), w);
          result.add(data);
        }
      });
    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
      e.printStackTrace();
    }
    return result;
  }
}

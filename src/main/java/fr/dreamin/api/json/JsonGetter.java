package fr.dreamin.api.json;

import com.google.gson.*;
import fr.dreamin.api.cuboid.Cuboid;
import fr.dreamin.api.cuboid.MemoryCuboid;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

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
}

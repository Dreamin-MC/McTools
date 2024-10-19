package fr.dreamin.api.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.dreamin.api.cuboid.Cuboid;
import fr.dreamin.api.entity.interaction.InteractionData;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

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

  public static JsonArray createJsonFromCuboids(@NotNull List<Cuboid> cuboids) {
    JsonArray jsonArray = new JsonArray();
    cuboids.forEach(cuboid -> jsonArray.add(createJsonFromCuboid(cuboid)));
    return jsonArray;
  }

  public static JsonObject createJsonFromCuboid(@NotNull Cuboid cuboid) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.add("locA", createJsonFromLocation(cuboid.getLocA(), false));
    jsonObject.add("locB", createJsonFromLocation(cuboid.getLocB(), false));

    return jsonObject;
  }

  // ----------------------------------------------------------------
  // VECTORS
  // ----------------------------------------------------------------

  public static JsonObject createJsonFromVector(@NotNull Vector vector) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("x", vector.getX());
    jsonObject.addProperty("y", vector.getY());
    jsonObject.addProperty("z", vector.getZ());

    return jsonObject;
  }
  public static JsonArray createJsonFromVectors(@NotNull List<Vector> vectors) {
    JsonArray jsonArray = new JsonArray();
    vectors.forEach(vector -> jsonArray.add(createJsonFromVector(vector)));
    return jsonArray;
  }

  public static JsonObject createJsonFromVector3f(@NotNull Vector3f vector) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("x", vector.x);
    jsonObject.addProperty("y", vector.y);
    jsonObject.addProperty("z", vector.z);

    return jsonObject;
  }
  public static JsonArray createJsonFromVector3fs(@NotNull List<Vector3f> vector3fs) {
    JsonArray jsonArray = new JsonArray();
    vector3fs.forEach(vector3f -> jsonArray.add(createJsonFromVector3f(vector3f)));
    return jsonArray;
  }

  // ----------------------------------------------------------------
  // INTERACTION DATA
  // ----------------------------------------------------------------

  public static JsonObject createJsonFromInteractionData(@NotNull InteractionData data) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("x", data.getLocation().getX());
    jsonObject.addProperty("y", data.getLocation().getY());
    jsonObject.addProperty("z", data.getLocation().getZ());
    jsonObject.addProperty("yaw", data.getLocation().getYaw());
    jsonObject.addProperty("pitch", data.getLocation().getPitch());

    jsonObject.addProperty("height", data.getHeight());
    jsonObject.addProperty("width", data.getWidth());

    return jsonObject;
  }
  public static JsonArray createJsonFromInteractionsData(@NotNull List<InteractionData> interactionsData) {
    JsonArray jsonArray = new JsonArray();
    interactionsData.forEach(data -> jsonArray.add(createJsonFromInteractionData(data)));
    return jsonArray;
  }
}

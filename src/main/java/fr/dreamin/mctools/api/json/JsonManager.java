package fr.dreamin.mctools.api.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.cuboide.Cuboide;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {

  private static Gson gson = new Gson();

  public static List<Location> convertJsonToListLocationWithYawAndPitch(String location, World w) {
    List<Location> locationList = new ArrayList<>();

    JsonArray jsonArray = gson.fromJson(location, JsonArray.class);
    for (int i = 0; i < jsonArray.size(); i++) {
      JsonObject element = jsonArray.get(i).getAsJsonObject();
      locationList.add(new Location(w, element.get("x").getAsDouble(), element.get("y").getAsDouble(), element.get("z").getAsDouble(), element.get("yaw").getAsFloat(), element.get("pitch").getAsFloat()));
    }

    return locationList;
  }

  public static List<Location> convertJsonToListLocation(String location, World w) {
    List<Location> locationList = new ArrayList<>();

    JsonArray jsonArray = gson.fromJson(location, JsonArray.class);
    for (int i = 0; i < jsonArray.size(); i++) {
      JsonObject element = jsonArray.get(i).getAsJsonObject();
      locationList.add(new Location(w, element.get("x").getAsDouble(), element.get("y").getAsDouble(), element.get("z").getAsDouble()));
    }

    return locationList;
  }

  public static Location convertJsonToLocationWithYaAndPitch(String location, World w) {

    Location loc = null;

    JsonObject jsonObject = gson.fromJson(location, JsonObject.class);

    loc = new Location(w, jsonObject.get("x").getAsDouble(), jsonObject.get("y").getAsDouble(), jsonObject.get("z").getAsDouble(), jsonObject.get("yaw").getAsFloat(), jsonObject.get("pitch").getAsFloat());

    return loc;
  }


  public static String convertLocationsWithYawAndPitchToJson(List<Location> locations) {
    JsonArray jsonArray = new JsonArray();

    for (Location loc : locations) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("x", loc.getX());
      jsonObject.addProperty("y", loc.getY());
      jsonObject.addProperty("z", loc.getZ());
      jsonObject.addProperty("yaw", loc.getYaw());
      jsonObject.addProperty("pitch", loc.getPitch());

      jsonArray.add(jsonObject);
    }

    return gson.toJson(jsonArray);
  }
  public static String convertLocationsToJson(List<Location> locations) {
    JsonArray jsonArray = new JsonArray();

    for (Location loc : locations) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("x", loc.getX());
      jsonObject.addProperty("y", loc.getY());
      jsonObject.addProperty("z", loc.getZ());

      jsonArray.add(jsonObject);
    }

    return gson.toJson(jsonArray);
  }

  public static Cuboide convertJsonToCuboid(World world, String json, boolean saveBlock) {

    JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

    JsonObject locA = jsonObject.getAsJsonObject("locA");
    JsonObject locB = jsonObject.getAsJsonObject("locB");

    return new Cuboide(
      new Location(world, locA.get("x").getAsDouble(), locA.get("y").getAsDouble(), locA.get("z").getAsDouble()),
      new Location(world, locB.get("x").getAsDouble(), locB.get("y").getAsDouble(), locB.get("z").getAsDouble()),
      saveBlock
    );
  }

  public static String getLanguageFromIP(String ipAddress) {

    if (McTools.getCodex().getIpApiKey() != null) {

      String apiKey = McTools.getCodex().getIpApiKey();
      String apiUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=" + apiKey + "&ip=" + ipAddress;

      URL url = null;
      try {
        url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
          BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          String inputLine;
          StringBuilder response = new StringBuilder();

          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();

          Gson gson = new Gson();
          JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
          String language = jsonObject.get("languages").getAsString();

          String split = language.split(",")[0];

          return split.split("-")[0] + "_" + split.split("-")[1];
        } else if (responseCode == HttpURLConnection.HTTP_UNAVAILABLE) return McTools.getCodex().getDefaultLang().getNameCode();
        else return McTools.getCodex().getDefaultLang().getNameCode();
      } catch (ProtocolException e) {
        return McTools.getCodex().getDefaultLang().getNameCode();
      } catch (MalformedURLException e) {
        return McTools.getCodex().getDefaultLang().getNameCode();
      } catch (IOException e) {
        return McTools.getCodex().getDefaultLang().getNameCode();
      }
    }
    else return McTools.getCodex().getDefaultLang().getNameCode();

  }

}

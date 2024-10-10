package fr.dreamin.api.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MojangAPI {

  private static final String PROFILE_API_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";

  /**
   * Retrieves the player name from Mojang API based on their UUID.
   *
   * @param uuid The UUID of the player.
   * @return The player's name.
   * @throws Exception If there is an error while retrieving data from Mojang API.
   */
  public static String getPlayerName(String uuid) throws Exception {
    URL url = new URL(PROFILE_API_URL + uuid);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }
    reader.close();

    String response = builder.toString();
    if (response == null || response.isEmpty()) {
      throw new Exception("Response from server is empty.");
    }

    JsonObject json = new JsonParser().parse(response).getAsJsonObject();
    if (!json.has("name")) {
      throw new Exception("Invalid UUID.");
    }
    return json.get("name").getAsString();
  }

  /**
   * Retrieves the base64 encoded skin data for a player from their UUID.
   *
   * @param uuid The UUID of the player.
   * @return The base64 encoded skin data.
   * @throws Exception If there is an error while retrieving data from Mojang API.
   */
  public static String getSkinBase64(String uuid) throws Exception {
    URL url = new URL(PROFILE_API_URL + uuid);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }
    reader.close();

    String response = builder.toString();
    if (response == null || response.isEmpty()) {
      throw new Exception("Response from server is empty.");
    }

    JsonObject json = new JsonParser().parse(response).getAsJsonObject();
    if (json.has("properties")) {
      for (int i = 0; i < json.getAsJsonArray("properties").size(); i++) {
        JsonObject property = json.getAsJsonArray("properties").get(i).getAsJsonObject();
        if ("textures".equals(property.get("name").getAsString())) {
          return property.get("value").getAsString();
        }
      }
    }

    throw new Exception("Cannot find 'textures' value.");
  }
}
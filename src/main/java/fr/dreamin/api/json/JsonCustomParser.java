package fr.dreamin.api.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

public class JsonCustomParser {

  public static JsonElement parse(@NotNull String json) {
    return JsonParser.parseString(json);
  }

  public static JsonElement parse(@NotNull Reader reader) {
    return JsonParser.parseReader(reader);
  }
  public static JsonElement parse(@NotNull JsonReader reader) {
    return JsonParser.parseReader(reader);
  }

  public static JsonElement parse(@NotNull JsonPrimitive jsonPrimitive) {
    return JsonParser.parseString(jsonPrimitive.getAsString());
  }

  public static JsonElement parse(@NotNull JsonElement jsonElement) {
    if (jsonElement.isJsonPrimitive()) return JsonParser.parseString(jsonElement.getAsJsonPrimitive().getAsString());
    return JsonParser.parseString(jsonElement.getAsString());
  }

}

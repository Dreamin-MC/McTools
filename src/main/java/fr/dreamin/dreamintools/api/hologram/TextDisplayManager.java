package fr.dreamin.dreamintools.api.hologram;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.mysql.fetcher.displayFetcher.DisplayFetcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

public class TextDisplayManager {

  private TextDisplay textDisplay;

  public TextDisplayManager(Location location, boolean save) {
    textDisplay = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
    if (save) DisplayFetcher.addDisplay(McTools.getGson().toJson(textDisplay), location);
  }

  public TextDisplayManager(Location location, String text, boolean save) {
    textDisplay = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
    textDisplay.setText(text);
    if (save) DisplayFetcher.addDisplay(McTools.getGson().toJson(textDisplay), location);
  }

  public TextDisplay getTextDisplay() {
    return textDisplay;
  }

}

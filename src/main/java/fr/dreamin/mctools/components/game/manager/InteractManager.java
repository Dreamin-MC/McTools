package fr.dreamin.mctools.components.game.manager;

import com.ticxo.modelengine.api.model.ActiveModel;
import fr.dreamin.mctools.api.modelEngine.Interact;
import fr.dreamin.mctools.database.fetcher.interactFetcher.InteractFetcher;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class InteractManager {
  private static List<Interact> interacts = new ArrayList<>();

  public static List<Interact> getInteracts() {
    return interacts;
  }

  public static void newInteract(String modelName, Location location, Boolean clickable, Boolean save) {
    interacts.add(new Interact(modelName, location, clickable));
    if (save) InteractFetcher.addInteract(modelName, location, clickable);
  }

  public static void removeInteract(Interact door) {
    interacts.remove(door);
  }

  public static Interact getInteract(ActiveModel activeModel) {
    for (Interact interact : interacts) {
      if (interact.getActiveModel().equals(activeModel)) {
        return interact;
      }
    }
    return null;
  }

}

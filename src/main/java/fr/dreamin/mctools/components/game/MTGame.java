package fr.dreamin.mctools.components.game;

import fr.dreamin.mctools.components.game.manager.BuildManager;
import fr.dreamin.mctools.components.listeners.ListenerManager;
import fr.dreamin.mctools.database.fetcher.buildCategoryFetcher.BuildCategoryFetcher;
import fr.dreamin.mctools.database.fetcher.buildTagFetcher.BuildTagFetcher;
import org.bukkit.plugin.java.JavaPlugin;

public class MTGame {

  public MTGame(JavaPlugin plugin) {

    BuildManager.getTagCategorys().addAll(BuildCategoryFetcher.getAllCategory());
    BuildManager.getTags().addAll(BuildTagFetcher.getAllTag());

    new ListenerManager(plugin);
  }



}

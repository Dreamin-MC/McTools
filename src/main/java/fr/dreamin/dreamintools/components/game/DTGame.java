package fr.dreamin.dreamintools.components.game;

import fr.dreamin.dreamintools.components.game.manager.BuildManager;
import fr.dreamin.dreamintools.components.listeners.ListenerManager;
import fr.dreamin.dreamintools.mysql.fetcher.buildCategoryFetcher.BuildCategoryFetcher;
import fr.dreamin.dreamintools.mysql.fetcher.buildTagFetcher.BuildTagFetcher;
import org.bukkit.plugin.java.JavaPlugin;

public class DTGame {

  public DTGame(JavaPlugin plugin) {

    BuildManager.getTagCategorys().addAll(BuildCategoryFetcher.getAllCategory());
    BuildManager.getTags().addAll(BuildTagFetcher.getAllTag());

    new ListenerManager(plugin);
  }



}

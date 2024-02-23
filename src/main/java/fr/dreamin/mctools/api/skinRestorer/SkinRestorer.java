package fr.dreamin.mctools.api.skinRestorer;

import fr.dreamin.mctools.McTools;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.exception.DataRequestException;
import net.skinsrestorer.api.exception.MineSkinException;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SkinRestorer {

  private static SkinsRestorer skinsRestorerAPI = SkinsRestorerProvider.get();

  public static void setSkin(Player player, String skin, boolean async) {

    if (async) {
      Bukkit.getScheduler().runTaskAsynchronously(McTools.getInstance(), () -> {

        try {
          SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();

          Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin);

          if (result.isEmpty()) {return;}

          PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

          // Associate the skin with the player
          playerStorage.setSkinIdOfPlayer(player.getUniqueId(), result.get().getIdentifier());

          // Instantly apply skin to the player without requiring the player to rejoin
          skinsRestorerAPI.getSkinApplier(Player.class).applySkin(player);
        } catch (DataRequestException | MineSkinException e) {
          e.printStackTrace();
        }

      });
    }
    else {
      try {
        SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();

        Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin);

        if (result.isEmpty()) {return;}

        PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

        // Associate the skin with the player
        playerStorage.setSkinIdOfPlayer(player.getUniqueId(), result.get().getIdentifier());

        // Instantly apply skin to the player without requiring the player to rejoin
        skinsRestorerAPI.getSkinApplier(Player.class).applySkin(player);
      } catch (DataRequestException | MineSkinException e) {
        e.printStackTrace();
      }
    }


  }
}
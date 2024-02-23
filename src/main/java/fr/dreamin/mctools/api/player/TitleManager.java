package fr.dreamin.mctools.api.player;

import org.bukkit.entity.Player;

import java.util.List;

public class TitleManager {

  public static void sendTitleToAll(List<Player> playerList, String title, String sub) {
    for (Player player: playerList) {
      player.sendTitle(title, sub);
    };
  }

  public static void sendTitleToAll(List<Player> playerList, String title) {
    for (Player player: playerList) {
      player.sendTitle(title, "");
    };
  }

}

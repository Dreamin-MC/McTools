package fr.dreamin.api.title;

import org.bukkit.entity.Player;

import java.util.List;

public class TitleManager {

  public static void sendTitleToAll(List<Player> playerList, String title, String sub) {
    playerList.forEach(player -> player.sendTitle(title, sub));
  }

  public static void sendTitleToAll(List<Player> playerList, String title) {
    playerList.forEach(player -> player.sendTitle(title, ""));
  }

}

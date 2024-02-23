package fr.dreamin.mctools.api.player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageManager {

  public static void sendMessageToAll(List<Player> playerList, String msg) {
    for (Player player: playerList) {
      player.sendMessage(msg);
    }
  }

  public static void sendMessageToAll(List<Player> playerList, List<String> msg) {
    for (Player player: playerList) {
      for (String line : msg)
        player.sendMessage(line);
    }
  }

  public static void sendActionBarToAll(List<Player> playerList, String msg) {
    for (Player player: playerList) {
      sendActionBarMessage(player, msg);
    }
  }

  public static void sendActionBarMessage(Player player, String message) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }

}

package fr.dreamin.api.msg;

import fr.dreamin.api.colors.CustomChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Utility class for sending various types of messages to players.
 */
public class MessageUtils {

  /**
   * Sends a single message to all players in the provided list.
   *
   * @param playerList The list of players to send the message to.
   * @param msg The message to send.
   */
  public static void sendMessageToAll(final List<Player> playerList, final String msg) {
    playerList.forEach(player -> player.sendMessage(msg));
  }

  /**
   * Sends multiple lines of messages to all players in the provided list.
   *
   * @param playerList The list of players to send the messages to.
   * @param msg The list of messages to send.
   */
  public static void sendMessageToAll(final List<Player> playerList, final List<String> msg) {
    playerList.forEach(player -> msg.forEach(player::sendMessage));
  }

  /**
   * Sends an action bar message to all players in the provided list.
   *
   * @param playerList The list of players to send the action bar message to.
   * @param msg The message to send as an action bar.
   */
  public static void sendActionBarToAll(final List<Player> playerList, final String msg) {
    playerList.forEach(player -> sendActionBarMessage(player, msg));
  }

  /**
   * Sends an action bar message to a specific player.
   *
   * @param player The player to send the action bar message to.
   * @param message The message to send as an action bar.
   */
  public static void sendActionBarMessage(final Player player, final String message) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }

  /**
   * Sends an error message with a prefix to a specific player.
   *
   * @param player The player to send the error message to.
   * @param prefix The prefix for the error message.
   * @param msg The error message to send.
   */
  public static void sendError(final Player player, final String prefix, final String msg) {
    String errorMessage = prefix +
      CustomChatColor.RED.getColorWithText("Error") +
      CustomChatColor.GRAY.getColorWithText(" : ") +
      CustomChatColor.WHITE.getColorWithText(msg);
    player.sendMessage(errorMessage);
  }

  /**
   * Sends an error message with a prefix to all players in the provided list.
   *
   * @param players The list of players to send the error message to.
   * @param prefix The prefix for the error message.
   * @param msg The error message to send.
   */
  public static void sendError(final List<Player> players, final String prefix, final String msg) {
    players.forEach(player -> sendError(player, prefix, msg));
  }
}
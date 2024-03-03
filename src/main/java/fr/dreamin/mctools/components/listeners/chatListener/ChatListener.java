package fr.dreamin.mctools.components.listeners.chatListener;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

  @EventHandler
  public void onChat(AsyncPlayerChatEvent event) {
    String message = event.getMessage();

    if (message.startsWith(McTools.getCodex().getStaffChatPrefix())) {

      if (McTools.getCodex().isStaffMode() && McTools.getCodex().isStaffChatMode()) {
        if (event.getPlayer().hasPermission(PlayerPerm.STAFF.getPerm())) {
          event.setCancelled(true);
          String staffMessage = message.substring(McTools.getCodex().getStaffChatPrefix().length()).trim();
          McTools.getService(PlayersService.class).getMtPlayers().forEach(mtplayer -> {if (mtplayer.getPlayer().hasPermission(PlayerPerm.STAFF.getPerm())) {mtplayer.getPlayer().sendMessage(McTools.getCodex().getStaffBroadcastPrefix() + event.getPlayer().getName() + " " + staffMessage);}});
        }
      }
    }
  }
}

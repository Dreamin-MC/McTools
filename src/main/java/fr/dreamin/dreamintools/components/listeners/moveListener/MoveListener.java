package fr.dreamin.dreamintools.components.listeners.moveListener;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    if (McTools.getService(PlayersService.class).contains(player)) {
      DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

      if (!dtPlayer.isCanMove()) {
        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
          Location loc = event.getFrom();
          event.getPlayer().teleport(loc.setDirection(event.getTo().getDirection()));
        }
      }
    }
  }
}
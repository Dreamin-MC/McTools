package fr.dreamin.mctools.components.listeners.moveListener;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.paper.services.players.PlayersService;
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
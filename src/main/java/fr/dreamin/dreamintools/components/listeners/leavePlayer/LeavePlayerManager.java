package fr.dreamin.dreamintools.components.listeners.leavePlayer;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.entity.Player;

public class LeavePlayerManager {

  public static void removePlayer(Player player) {

    PlayersService playersService = McTools.getService(PlayersService.class);

    DTPlayer dtPlayer = playersService.getPlayer(player);

    if (dtPlayer != null)
      playersService.removeDTPlayer(dtPlayer);
  }

}

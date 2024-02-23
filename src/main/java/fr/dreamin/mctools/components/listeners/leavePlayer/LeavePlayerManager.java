package fr.dreamin.mctools.components.listeners.leavePlayer;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.paper.services.players.PlayersService;
import org.bukkit.entity.Player;

public class LeavePlayerManager {

  public static void removePlayer(Player player) {

    PlayersService playersService = McTools.getService(PlayersService.class);

    DTPlayer dtPlayer = playersService.getPlayer(player);

    if (dtPlayer != null)
      playersService.removeDTPlayer(dtPlayer);
  }

}

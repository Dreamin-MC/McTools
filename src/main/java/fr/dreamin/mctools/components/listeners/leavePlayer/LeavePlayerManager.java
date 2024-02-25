package fr.dreamin.mctools.components.listeners.leavePlayer;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.entity.Player;

public class LeavePlayerManager {

  public static void removePlayer(Player player) {

    PlayersService playersService = McTools.getService(PlayersService.class);

    MTPlayer MTPlayer = playersService.getPlayer(player);

    if (MTPlayer != null)
      playersService.removeDTPlayer(MTPlayer);
  }

}

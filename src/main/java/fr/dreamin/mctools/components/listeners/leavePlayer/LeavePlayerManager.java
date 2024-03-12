package fr.dreamin.mctools.components.listeners.leavePlayer;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.listener.leave.OnMtPlayerLeave;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.database.fetcher.UserFetcher.UserFetcher;
import org.bukkit.entity.Player;

public class LeavePlayerManager {

  public static void removePlayer(Player player) {

    PlayersService playersService = McTools.getService(PlayersService.class);

    MTPlayer mtPlayer = playersService.getPlayer(player);

    mtPlayer.getPlayerTickManager().stopBukkitTask();

    if (mtPlayer != null) {
      if (McTools.getCodex().isRemovePlayer()) playersService.removeDTPlayer(mtPlayer);
      UserFetcher.updatePlayer(mtPlayer);
      OnMtPlayerLeave.callEvent(mtPlayer);
    }

  }

}

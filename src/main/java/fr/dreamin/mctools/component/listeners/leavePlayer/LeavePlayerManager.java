package fr.dreamin.mctools.component.listeners.leavePlayer;

import fr.dreamin.mctools.McTools;
import fr.dreamin.api.listener.leave.OnMtPlayerLeave;
import fr.dreamin.mctools.component.player.MTPlayer;
import fr.dreamin.api.service.manager.players.PlayersService;
import org.bukkit.entity.Player;

public class LeavePlayerManager {

  public static void removePlayer(Player player) {
    PlayersService playersService = McTools.getService(PlayersService.class);

    MTPlayer mtPlayer = playersService.getPlayer(player);

    if (mtPlayer != null) {
      mtPlayer.getPlayerTickManager().stopBukkitTask();

      playersService.removeMTPlayer(mtPlayer);

      OnMtPlayerLeave.callEvent(mtPlayer);
    }

  }

}

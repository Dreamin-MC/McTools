package fr.dreamin.mctools.components.players.manager;

import com.rexcantor64.triton.api.players.LanguagePlayer;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.log.Logging;
import org.bukkit.entity.Player;

public class TritonPlayerManager {


  private LanguagePlayer lp;

  public TritonPlayerManager(Player player) {
    if (McTools.getInstance().getTritonManager() == null) McTools.getService(Logging.class).warn("§cTriton is not enabled, some features will not be available.");
    else this.lp = McTools.getInstance().getTritonManager().getPlayerManager().get(player.getUniqueId());
  }

  public LanguagePlayer getLanguagePlayer() {
    return lp;
  }

}

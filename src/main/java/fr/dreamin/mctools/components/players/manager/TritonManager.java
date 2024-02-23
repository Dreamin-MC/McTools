package fr.dreamin.mctools.components.players.manager;

import com.rexcantor64.triton.api.players.LanguagePlayer;
import fr.dreamin.mctools.McTools;
import org.bukkit.entity.Player;

public class TritonManager {


  private LanguagePlayer lp;

  public TritonManager(Player player) {

    if (McTools.getInstance().getTritonManager() == null) {
      McTools.getLog().warn("§cTriton is not enabled, some features will not be available.");
    }
    else {
      this.lp = McTools.getInstance().getTritonManager().getPlayerManager().get(player.getUniqueId());
    }

  }

  public LanguagePlayer getLanguagePlayer() {
    return lp;
  }

}

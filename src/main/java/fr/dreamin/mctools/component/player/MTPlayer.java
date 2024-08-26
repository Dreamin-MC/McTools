package fr.dreamin.mctools.component.player;

import fr.dreamin.mctools.component.player.manager.PlayerTickManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class MTPlayer {
  @Getter @Setter private Player player;

  @Getter private String skinBase64 = null;

  @Getter private PlayerTickManager playerTickManager = new PlayerTickManager(this);

  public MTPlayer(Player player) {
    this.player = player;
  }

}

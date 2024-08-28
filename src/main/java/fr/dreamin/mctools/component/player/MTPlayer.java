package fr.dreamin.mctools.component.player;

import fr.dreamin.api.minecraft.Minecraft;
import fr.dreamin.mctools.component.player.manager.PlayerTickManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MTPlayer {
  @Getter @Setter private Player player;

  @Getter private final String skinBase64;

  @Getter private PlayerTickManager playerTickManager = new PlayerTickManager(this);

  public MTPlayer(Player player) {
    this.player = player;

    try {
      this.skinBase64 = Minecraft.getSkinBase64(player.getUniqueId().toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}

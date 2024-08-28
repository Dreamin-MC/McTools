package fr.dreamin.api.service.manager.players;

import fr.dreamin.api.service.Service;
import fr.dreamin.mctools.component.player.MTPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersService extends Service {

  @Getter @Setter private Class<? extends MTPlayer> playerClass = null;

  @Getter @Setter private List<MTPlayer> mtPlayers = new ArrayList<>();

  //------ADDER------//

  public void addMTPlayer(MTPlayer mtPlayer) {
    if (!mtPlayers.contains(mtPlayer)) mtPlayers.add(mtPlayer);
  }

  //------REMOVER------//

  public void removeMTPlayer(MTPlayer mtPlayer) {
    if (mtPlayers.contains(mtPlayer)) mtPlayers.remove(mtPlayer);
  }

  //------METHODS------//

  public MTPlayer getPlayer(Player player) {
    for (MTPlayer dTPlayer : getMtPlayers()) {
      if (dTPlayer.getPlayer().equals(player)) return dTPlayer;
    }
    return null;
  }

  public MTPlayer getPlayerByName(String name) {
    for (MTPlayer dTPlayer : getMtPlayers()) {
      if (dTPlayer.getPlayer().getName().equals(name)) return dTPlayer;
    }
    return null;
  }

  public boolean contains(Player player) {
    for (MTPlayer MTPlayer : getMtPlayers()) {
      if (MTPlayer.getPlayer().equals(player)) return true;
    }
    return false;
  }

  public boolean containsByName(String name) {
    for (MTPlayer MTPlayer : getMtPlayers()) {
      if (MTPlayer.getPlayer().getName().equals(name)) return true;
    }
    return false;
  }

}

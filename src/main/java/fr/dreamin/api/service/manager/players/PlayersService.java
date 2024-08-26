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

  @Getter @Setter private List<MTPlayer> MtPlayers = new ArrayList<>(), spectators = new ArrayList<>();

  //------ADDER------//

  public void addDTPlayer(MTPlayer dTPlayer) {
    if (!MtPlayers.contains(dTPlayer)) MtPlayers.add(dTPlayer);
  }

  public void addSpectators(Player player) {
    MTPlayer dTPlayer = getPlayer(player);

    if (!spectators.contains(dTPlayer)) spectators.add(dTPlayer);
  }

  //------REMOVER------//

  public void removeDTPlayer(MTPlayer dTPlayer) {
    if (MtPlayers.contains(dTPlayer)) MtPlayers.remove(dTPlayer);
  }

  public void removeSpectators(Player player) {
    MTPlayer dTPlayer = getPlayer(player);
    spectators.remove(dTPlayer);
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

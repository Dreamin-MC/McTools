package fr.dreamin.mctools.api.service.manager.players;

import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.Service;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersService extends Service {

  @Getter @Setter
  private Class<? extends MTPlayer> playerClass = null;

  @Getter @Setter
  private List<MTPlayer> DTPlayers = new ArrayList<>(), spectators = new ArrayList<>();

  //------ADDER------//

  public void addDTPlayer(MTPlayer dTPlayer) {
    if (!DTPlayers.contains(dTPlayer)) DTPlayers.add(dTPlayer);
  }

  public void addSpectators(Player player) {
    MTPlayer dTPlayer = getPlayer(player);

    if (!spectators.contains(dTPlayer)) spectators.add(dTPlayer);
  }

  //------REMOVER------//

  public void removeDTPlayer(MTPlayer dTPlayer) {
    if (DTPlayers.contains(dTPlayer)) DTPlayers.remove(dTPlayer);
  }

  public void removeSpectators(Player player) {
    MTPlayer dTPlayer = getPlayer(player);
    spectators.remove(dTPlayer);
  }

  //------METHODS------//

  public MTPlayer getPlayer(Player player) {
    for (MTPlayer dTPlayer : getDTPlayers()) {
      if (dTPlayer.getPlayer().equals(player)) return dTPlayer;
    }
    return null;
  }

  public boolean contains(Player player) {
    for (MTPlayer MTPlayer : getDTPlayers()) {
      if (MTPlayer.getPlayer().equals(player)) return true;
    }
    return false;
  }

  public boolean ifPossibleToStart() {
    boolean result = false;

    for (MTPlayer MTPlayer : getDTPlayers()) {
      if (MTPlayer.getVoiceManager().getClient().isConnected()) result = true;
      else result = false;
    }
    return result;
  }

  public List<MTPlayer> playersNotConnected() {
    List<MTPlayer> MTPlayers = new ArrayList<>();
    for (MTPlayer MTPlayer : getDTPlayers()) {
      if (!MTPlayer.getVoiceManager().getClient().isConnected()) MTPlayers.add(MTPlayer);
    }
    return MTPlayers;
  }
}

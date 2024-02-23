package fr.dreamin.mctools.paper.services.players;

import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.generic.service.Service;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersService extends Service {

  private List<DTPlayer> dTPlayers = new ArrayList<>(), spectators = new ArrayList<>();

  public List<DTPlayer> getDTPlayers() {
    return dTPlayers;
  }

  public void addDTPlayer(DTPlayer dTPlayer) {
    if (!dTPlayers.contains(dTPlayer))
      dTPlayers.add(dTPlayer);
  }

  public void removeDTPlayer(DTPlayer dTPlayer) {
    if (dTPlayers.contains(dTPlayer))
      dTPlayers.remove(dTPlayer);
  }

  public DTPlayer getPlayer(Player player) {

    for (DTPlayer dTPlayer : getDTPlayers()) {
      if (dTPlayer.getPlayer().equals(player))
        return dTPlayer;
    }
    return null;
  }

  public boolean contains(Player player) {
    for (DTPlayer dtPlayer : getDTPlayers()) {
      if (dtPlayer.getPlayer().equals(player))
        return true;
    }
    return false;
  }

  public List<DTPlayer> getSpectators() {
    return spectators;
  }

  public void addSpectators(Player player) {
    DTPlayer dTPlayer = getPlayer(player);

    if (!spectators.contains(dTPlayer))
      spectators.add(dTPlayer);
  }

  public void removeSpectators(Player player) {
    DTPlayer dTPlayer = getPlayer(player);
    spectators.remove(dTPlayer);
  }

  public boolean ifPossibleToStart() {

    boolean result = false;

    for (DTPlayer dtPlayer : getDTPlayers()) {
      if (dtPlayer.getVoiceManager().getClient().isConnected()) result = true;
      else result = false;
    }
    return result;
  }

  public List<DTPlayer> playersNotConnected() {
    List<DTPlayer> dtPlayers = new ArrayList<>();
    for (DTPlayer dtPlayer : getDTPlayers()) {
      if (!dtPlayer.getVoiceManager().getClient().isConnected()) dtPlayers.add(dtPlayer);
    }
    return dtPlayers;
  }


}

package fr.dreamin.mctools.components.listeners.joinPlayer;

import fr.dreamin.mctools.api.listener.join.OnMtPlayerJoin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinPlayerListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    JoinPlayerManager.addPlayer(event.getPlayer());
  }

}

package fr.dreamin.mctools.components.listeners.leavePlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeavePlayerListener implements Listener {

  @EventHandler
  public void onJoin(PlayerQuitEvent event) {
    LeavePlayerManager.removePlayer(event.getPlayer());
  }



}

package fr.dreamin.mctools.component.listeners.leavePlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeavePlayerListener implements Listener {

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    LeavePlayerManager.removePlayer(event.getPlayer());
  }



}

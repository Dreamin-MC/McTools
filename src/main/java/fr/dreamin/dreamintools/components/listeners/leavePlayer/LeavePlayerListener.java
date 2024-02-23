package fr.dreamin.dreamintools.components.listeners.leavePlayer;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeavePlayerListener implements Listener {

  @EventHandler
  public void onJoin(PlayerQuitEvent event) {
    LeavePlayerManager.removePlayer(event.getPlayer());
  }



}

package fr.dreamin.mctools.api.listener.leave;

import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OnMtPlayerLeave extends Event {

  private static final HandlerList HANDLERS = new HandlerList();

  private MTPlayer mtPlayer;

  public OnMtPlayerLeave(MTPlayer mtPlayer) {
    this.mtPlayer = mtPlayer;
  }

  public MTPlayer getMTPlayer() {
    return this.mtPlayer;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
  
  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

  public static void callEvent(MTPlayer mtPlayer) {
    Bukkit.getPluginManager().callEvent(new OnMtPlayerLeave(mtPlayer));
  }
}
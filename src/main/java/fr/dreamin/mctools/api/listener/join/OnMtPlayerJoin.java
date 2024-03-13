package fr.dreamin.mctools.api.listener.join;

import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OnMtPlayerJoin extends Event {
  private static final HandlerList HANDLERS = new HandlerList();

  private MTPlayer mtPlayer;

  public OnMtPlayerJoin(MTPlayer mtPlayer) {
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
    Bukkit.getPluginManager().callEvent(new OnMtPlayerJoin(mtPlayer));
  }
}

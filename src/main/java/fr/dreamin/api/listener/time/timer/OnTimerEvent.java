package fr.dreamin.api.listener.time.timer;

import fr.dreamin.api.listener.time.TimeEventAction;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OnTimerEvent extends Event {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter private Object key;
  @Getter private TimeEventAction action;

  public OnTimerEvent(Object key, TimeEventAction action) {
    this.key = key;
    this.action = action;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
  public static void callEvent(Object key, TimeEventAction action) {
    Bukkit.getPluginManager().callEvent(new OnTimerEvent(key, action));
  }
}

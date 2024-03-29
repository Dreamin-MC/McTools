package fr.dreamin.mctools.api.listener.time.cooldown;

import fr.dreamin.mctools.api.listener.time.TimeEventAction;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OnCooldownEvent extends Event {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter private Object key;
  @Getter private int cooldownTick;
  @Getter private TimeEventAction action;

  public OnCooldownEvent(Object key, int cooldownTick, TimeEventAction action) {
    this.key = key;
    this.cooldownTick = cooldownTick;
    this.action = action;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
  public static void callEvent(Object key, int cooldownTick, TimeEventAction action) {
    Bukkit.getPluginManager().callEvent(new OnCooldownEvent(key, cooldownTick, action));
  }
}

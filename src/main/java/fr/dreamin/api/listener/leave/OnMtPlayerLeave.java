package fr.dreamin.api.listener.leave;

import fr.dreamin.mctools.component.player.MTPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OnMtPlayerLeave extends Event {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter private MTPlayer mtPlayer;
  @Getter private Player player;

  public OnMtPlayerLeave(MTPlayer mtPlayer) {
    this.mtPlayer = mtPlayer;
    this.player = mtPlayer.getPlayer();
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

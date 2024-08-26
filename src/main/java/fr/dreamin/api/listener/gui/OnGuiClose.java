package fr.dreamin.api.listener.gui;

import fr.dreamin.api.gui.GuiBuilder;
import fr.dreamin.mctools.component.player.MTPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class OnGuiClose extends Event {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter private MTPlayer mtPlayer;
  @Getter private GuiBuilder guiBuilder;
  @Getter private Inventory inventory;

  public OnGuiClose(MTPlayer mtPlayer, GuiBuilder guiBuilder, Inventory inventory) {
    this.mtPlayer = mtPlayer;
    this.guiBuilder = guiBuilder;
    this.inventory = inventory;
  }


  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

  public static void callEvent(MTPlayer mtPlayer, GuiBuilder guiBuilder, Inventory inventory) {
    Bukkit.getPluginManager().callEvent(new OnGuiClose(mtPlayer, guiBuilder, inventory));
  }

}

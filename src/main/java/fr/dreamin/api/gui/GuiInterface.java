package fr.dreamin.api.gui;

import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public interface GuiInterface {

  String name();
  Gui gui(Player player);
  default void open(Player player) {
    Window window = Window.single()
      .setViewer(player)
      .setGui(gui(player))
      .setTitle(name())
      .build();
    window.open();
  }
}

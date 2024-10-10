package fr.dreamin.api.gui;

import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public interface SplitGuiInterface {

  String name();
  Gui guiUpper(Player player);

  default Gui guiLower(Player player) {
    return Gui.normal()
      .setStructure(
        ". . . . . . . . . ",
        ". . . . . . . . . ",
        ". . . . . . . . . ",
        ". . . . . . . . . "
      )
      .build();
  };

  default void open(Player player) {
    Window.split()
      .setViewer(player)
      .setUpperGui(guiUpper(player))
      .setLowerGui(guiLower(player))
      .setTitle(name())
      .open(player);
  }

}

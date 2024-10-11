package fr.dreamin.api.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

@Getter @Setter
public abstract class SplitGuiInterface implements GuiInterface {
  private Window window;

  @Override
  public void open(Player player) {
    this.window = Window.split()
      .setViewer(player)
      .setUpperGui(guiUpper(player))
      .setLowerGui(this.guiLower(player))
      .setCloseable(closable(player))
      .setTitle(name(player))
      .build();
    this.window.open();
  }
}
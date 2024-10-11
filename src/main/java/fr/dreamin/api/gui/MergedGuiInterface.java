package fr.dreamin.api.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

@Getter @Setter
public abstract class MergedGuiInterface implements GuiInterface {

  private Window window;

  @Override
  public void open(Player player) {
    this.window = Window.merged()
      .setViewer(player)
      .setGui(guiUpper(player))
      .setTitle(name(player))
      .setCloseable(closable(player))
      .build();
    this.window.open();
  }
}
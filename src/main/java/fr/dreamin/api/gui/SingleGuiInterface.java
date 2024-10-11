package fr.dreamin.api.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.window.Window;

@Getter @Setter
public abstract class SingleGuiInterface implements GuiInterface {

  private Window window;

  @Override
  public void open(Player player) {
    this.window = Window.single()
      .setViewer(player)
      .setGui(guiUpper(player))
      .setTitle(name(player))
      .setCloseable(closable(player))
      .build();
    this.window.open();
  }
}
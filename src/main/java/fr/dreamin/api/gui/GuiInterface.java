package fr.dreamin.api.gui;

import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public interface GuiInterface {

  String name(Player player); // Retourne le nom/titre de la GUI
  default boolean closable(Player player) {
    return true;
  };
  Gui guiUpper(Player player); // Retourne l'instance de GUI pour le joueur
  default Gui guiLower(Player player) {
    return Gui.normal()
      .setStructure(
        ". . . . . . . . . ",
        ". . . . . . . . . ",
        ". . . . . . . . . ",
        ". . . . . . . . . "
      )
      .build();
  }// Retourne

  // Ouvre la GUI pour le joueur, méthode par défaut pour les GUIs en mode "single"
  default void open(Player player) {
    Window.single()
      .setViewer(player)
      .setGui(guiUpper(player))
      .setTitle(name(player))
      .setCloseable(closable(player))
      .open(player);
  }
}
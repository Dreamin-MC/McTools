package fr.dreamin.mctools.components.commands;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.ListMapGui;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandMT implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(McTools.getCodex().getPrefix() + McTools.getCodex().getErrorConsole());
      return true;
    }

    Player player = (Player) commandSender;

    MTPlayer dTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (dTPlayer.hasPermAdmin() || dTPlayer.hasPermDev()) {
      switch (strings[0]) {
        case "reload":
          McTools.getCodex().loadConf();
          break;
        case "editmode":
          if (!McTools.getCodex().isEditMode()) {
            player.sendMessage(McTools.getCodex().getBroadcastprefix() + "§cLe mode édition n'est pas activé.");
            return false;
          }
          if (dTPlayer.isEditMode()) {
            dTPlayer.setEditMode(false);
            player.sendMessage(McTools.getCodex().getBroadcastprefix() + "§aVous avez désactivé le mode édition.");
          }
          else {
            dTPlayer.setEditMode(true);
            player.sendMessage(McTools.getCodex().getBroadcastprefix() + "§aVous avez activé le mode édition.");

          }
          break;
        case "listmap":
          McTools.getService(GuiManager.class).open(player, ListMapGui.class);
          break;
        default:
          player.sendMessage("Merci de préciser une sous-commande.");
          return false;
      }
    }
    else {
      player.sendMessage(McTools.getCodex().getErrorCommand());
      return false;
    }


    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

    Player player = (Player) commandSender;

    MTPlayer dTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    List<String> listArgs = new ArrayList<>();

    if (dTPlayer.hasPermAdmin() || dTPlayer.hasPermDev()) {
      if (strings.length == 1) {
        listArgs.add("reload");
        listArgs.add("listmap");
        if (McTools.getCodex().isEditMode()) listArgs.add("editmode");

        return listArgs;
      }
    }

    return listArgs;
  }
}

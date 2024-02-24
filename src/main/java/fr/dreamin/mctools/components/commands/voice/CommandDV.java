package fr.dreamin.mctools.components.commands.voice;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.voice.ListPlayerGui;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.players.DTPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandDV implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(McTools.getCodex().getPrefix() + McTools.getCodex().getErrorConsole());
      return true;
    }

    Player player = (Player) sender;
    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (player.hasPermission(McTools.getCodex().getPermHote())) {

      switch (args[0]) {
        case "listplayers":
          if (dtPlayer.hasPermHot()) McTools.getService(GuiManager.class).open(player, ListPlayerGui.class);
          break;
      }

    }
    else {
      player.sendMessage(McTools.getCodex().getErrorCommand());
      return false;
    }
    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    Player player = (Player) sender;

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (args.length == 1)
      if (dtPlayer.hasPermHot()) return Arrays.asList("listplayers");

    return new ArrayList<>();
  }
}

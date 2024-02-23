package fr.dreamin.dreamintools.components.commands.hote;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.gui.defaultGui.voice.ListPlayerGui;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
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

public class CommandDVH implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(McTools.getCodex().getPrefix() + McTools.getCodex().getErrorConsole());
      return true;
    }

    Player player = (Player) sender;
    DTPlayer vPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (player.hasPermission(McTools.getCodex().getPermHote())) {

      switch (args[0]) {
        case "listplayers":
          McTools.getInstance().getGuiManager().open(player, ListPlayerGui.class);
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

      if (args.length == 1)
        return Arrays.asList("listplayers");
      else
        return new ArrayList<>(); // null = all player names
    }


}

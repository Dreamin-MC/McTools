package fr.dreamin.mctools.components.commands.staff;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
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

public class CommandStaff implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    PlayersService playersService = McTools.getService(PlayersService.class);

    if (mtPlayer == null) {
      player.sendMessage(LangMsg.ERROR_OCCURRED.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    if (player.hasPermission(PlayerPerm.STAFF.getPerm())) {

      switch (args[0]) {
        case "freeze":
          if (McTools.getCodex().isStaffFreeze()) {
            String playerFreeze = args[1];

            String sentenceFreeze = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

            if (sentenceFreeze == null) {

              //TODO LangMsg à faire
              player.sendMessage(LangMsg.ERROR_OCCURRED.getMsg(McTools.getCodex().getDefaultLang(), ""));
              return true;
            }

            if (playersService.containsByName(playerFreeze)) {
              MTPlayer mtPlayer1 = playersService.getPlayerByName(playerFreeze);

              if (!mtPlayer.equals(mtPlayer1)) mtPlayer.getStaffPlayerManager().freezePlayer(mtPlayer1, sentenceFreeze);
              else player.sendMessage("Vous ne pouvez pas vous freeze vous même");
            }
          }
          break;

        case "unfreeze":
          if (McTools.getCodex().isStaffFreeze()) {
            String playerUnFreeze = args[1];
            if (playersService.containsByName(playerUnFreeze)) {
              MTPlayer mtPlayer1 = playersService.getPlayerByName(playerUnFreeze);
              mtPlayer.getStaffPlayerManager().unFreezePlayer(mtPlayer1);
            }
          }
          break;
      }

    }

    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

    Player player = (Player) sender;

    if (player.hasPermission(PlayerPerm.STAFF.getPerm())) {

      switch (args.length) {
        case 1:
          List<String> list = new ArrayList<>();

          if (McTools.getCodex().isStaffFreeze()) {
            list.add("freeze");
            list.add("unfreeze");
          }

          return list;
        case 2:
          switch (args[0]) {
            case "freeze": return McTools.getService(PlayersService.class).getPlayersNameNotFreeze();
            case "unfreeze": return McTools.getService(PlayersService.class).getPlayersNameFreeze();
          }
          break;
      }
    }
    else return Arrays.asList("Error");
    return  new ArrayList<>();
  }
}

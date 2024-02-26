package fr.dreamin.mctools.components.commands;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.ListMapGui;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
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
import java.util.Arrays;
import java.util.List;

public class CommandMT implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(McTools.getCodex().getBroadcastprefix() + McTools.getCodex().getErrorConsole());
      return true;
    }

    Player player = (Player) commandSender;

    MTPlayer dTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (player.hasPermission(PlayerPerm.BUILD.getPerm())) {

      if (args.length < 1) {
        player.sendMessage(McTools.getCodex().getBroadcastprefix() + "Merci de préciser une sous-commande.");
        return false;
      }

      switch (args[0]) {
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
        case "set":
          switch (args[1]) {
            case "pack":
              McTools.getCodex().setPack(!McTools.getCodex().isPack());
              player.sendMessage(McTools.getCodex().getBroadcastprefix() + CustomChatColor.WHITE.getColorWithText("Vous avez ") + (McTools.getCodex().isPack()? CustomChatColor.GREEN.getColorWithText("activé") : CustomChatColor.RED.getColorWithText("désactivé")) + CustomChatColor.WHITE.getColorWithText(" le pack."));
              break;
            case "lang":
              Lang lang = Lang.getLangByName(args[2]);

              if (lang != null) {
                dTPlayer.setLang(lang);
                player.sendMessage(LangMsg.UPDATE_LANG.getMsg(lang, lang.getName()));
                return true;
              }
              else {
                player.sendMessage(McTools.getCodex().getBroadcastprefix() + "Merci de préciser une langue valide.");
                return false;
              }

            default:
              player.sendMessage(McTools.getCodex().getBroadcastprefix() + "Merci de préciser une sous-commande valide.");
              return false;
          }
          break;
        case "test":
          player.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(dTPlayer.getLang()));
          player.sendMessage(LangMsg.ERROR_PERM.getMsg(dTPlayer.getLang()));
//          player.sendMessage(LangMsg.getConfig().getString("error.console.fr_FR"));
          break;
        default:
          player.sendMessage(McTools.getCodex().getBroadcastprefix() + "Merci de préciser une sous-commande valide.");
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
  public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

    Player player = (Player) commandSender;

    MTPlayer dTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    List<String> listArgs = new ArrayList<>();

    switch (args.length) {
      case 1:
        if (PlayerPerm.hasPermMin(dTPlayer.getPerm(), PlayerPerm.HOTE)) listArgs = Arrays.asList("reload", "listmap", "set");
        if (McTools.getCodex().isEditMode()) listArgs.add("editmode");

        return listArgs;
      case 2:
        switch (args[0]) {
          case "set": listArgs = Arrays.asList("pack", "lang");
          return listArgs;
        }
      case 3:
        switch (args[1]) {
          case "lang": listArgs = new ArrayList<>(Lang.getLangs());
          return listArgs;
        }
    }
    return listArgs;
  }
}

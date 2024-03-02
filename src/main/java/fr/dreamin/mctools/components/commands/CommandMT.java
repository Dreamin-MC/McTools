package fr.dreamin.mctools.components.commands;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.ListMapGui;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Bukkit;
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
      commandSender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    Player player = (Player) commandSender;

    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (player.hasPermission(PlayerPerm.BUILD.getPerm())) {

      if (args.length < 1) {
        mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
        return false;
      }

      switch (args[0]) {
        case "reload":
          McTools.getCodex().loadConf();
          break;
        case "editmode":
          if (!McTools.getCodex().isEditMode()) {
            mtPlayer.sendMsg(LangMsg.ERROR_EDITMODE_NOTENABLED, "");
            return false;
          }
          mtPlayer.setEditMode(!mtPlayer.isEditMode());
          break;
        case "listmap":
          McTools.getService(GuiManager.class).open(player, ListMapGui.class);
          break;
        case "set":
          switch (args[1]) {
            case "pack":
              McTools.getCodex().setPack(!McTools.getCodex().isPack());
              mtPlayer.sendMsg(LangMsg.CMD_MT_SETPACK, (McTools.getCodex().isPack() ? LangMsg.GENERAL_ENABLED : LangMsg.GENERAL_DISABLED));
              break;
            case "lang":
              Lang lang = Lang.getLangByDisplayName(args[2]);

              if (lang != null) {
                mtPlayer.setLang(lang);
                mtPlayer.sendMsg(LangMsg.PLAYER_UPDATE_LANG, lang.getDisplayName());
                return true;
              }
              else {
                mtPlayer.sendMsg(LangMsg.CMD_MT_ERROR_VALIDLANG, "");
                return false;
              }

            default:
              mtPlayer.sendMsg(LangMsg.ERROR_VALIDPUTVALUE, "");
              return false;
          }
          break;
        default:
          mtPlayer.sendMsg(LangMsg.ERROR_VALIDPUTVALUE, "");
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

          case "lang":
            List<String> finalListArgs = listArgs;
            McTools.getCodex().getLangs().forEach(lang -> finalListArgs.add(lang.getDisplayName()));
          return finalListArgs;
        }
    }
    return listArgs;
  }
}

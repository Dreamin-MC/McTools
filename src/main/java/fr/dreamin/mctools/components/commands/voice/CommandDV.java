package fr.dreamin.mctools.components.commands.voice;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.gui.defaultGui.voice.VoiceListPlayerGui;
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

public class CommandDV implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (player.hasPermission(PlayerPerm.HOTE.getPerm())) {

      switch (args[0]) {
        case "listplayers":
          if (player.hasPermission(PlayerPerm.HOTE.getPerm())) McTools.getService(GuiManager.class).open(player, VoiceListPlayerGui.class);
          break;
      }

    }
    else {
      mtPlayer.sendMsg(LangMsg.ERROR_OCCURRED, "");
      return false;
    }
    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    Player player = (Player) sender;

    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (args.length == 1)
      if (player.hasPermission(PlayerPerm.HOTE.getPerm())) return Arrays.asList("listplayers");

    return new ArrayList<>();
  }
}

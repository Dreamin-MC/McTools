package fr.dreamin.mctools.components.commands.voice;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandShowVoice implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    Player player = (Player) commandSender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (strings.length > 0) {

      if (strings.length == 2) {
        try {
          double y = Double.parseDouble(strings[1]);
          mtPlayer.getVoiceConfManager().setY(y);
          mtPlayer.getVoiceConfManager().setVoice(true);
        } catch (NumberFormatException e) {
          mtPlayer.sendMsg(LangMsg.ERROR_VALIDNUMBER, "");
          return true;
        }
      }

      try {
        int radius = Integer.parseInt(strings[0]);
        mtPlayer.getVoiceConfManager().setRadius(radius);
        mtPlayer.getVoiceConfManager().setVoice(true);
      } catch (NumberFormatException e) {
        mtPlayer.sendMsg(LangMsg.ERROR_VALIDNUMBER, "");
        return true;
      }
    } else mtPlayer.getVoiceConfManager().setVoice(false);

    return true;
  }
}

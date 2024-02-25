package fr.dreamin.mctools.components.commands.build.voice;

import fr.dreamin.mctools.McTools;
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
      commandSender.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
      return true;
    }

    Player player = (Player) commandSender;
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (strings.length > 0) {

      if (strings.length == 2) {
        try {
          double y = Double.parseDouble(strings[1]);
          MTPlayer.getVoiceConfManager().setY(y);
          MTPlayer.getVoiceConfManager().setVoice(true);
        } catch (NumberFormatException e) {
          player.sendMessage("§cErreur : §7Veuillez entrer un nombre valide.");
          return true;
        }
      }

      try {
        int radius = Integer.parseInt(strings[0]);
        MTPlayer.getVoiceConfManager().setRadius(radius);
        MTPlayer.getVoiceConfManager().setVoice(true);
      } catch (NumberFormatException e) {
        player.sendMessage("§cErreur : §7Veuillez entrer un nombre valide.");
        return true;
      }
    } else MTPlayer.getVoiceConfManager().setVoice(false);

    return true;
  }
}

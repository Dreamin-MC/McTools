package fr.dreamin.mctools.components.commands.build.models;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandCoal implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(Lang.en_US, ""));
      return true;
    }

    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (args.length == 0) {
      mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
      return true;
    }
    else if (args.length == 1) {
      try {
        int id = Integer.parseInt(args[0]);

        ItemBuilder itemBuilder = new ItemBuilder(Material.COAL).setCustomMeta(id);

        player.getInventory().addItem(itemBuilder.toItemStack());

      } catch (NumberFormatException e) {
        mtPlayer.sendMsg(LangMsg.ERROR_VALIDNUMBER, "");
        return true;
      }
    }
    else if (args.length == 2){

      try {
        int id1 = Integer.parseInt(args[0]);
        int id2 = Integer.parseInt(args[1]);

        if (id2 < id1) {
          mtPlayer.sendMsg(LangMsg.ERROR_RANGEID, "");
          return true;
        }

        for (int id = id1; id <= id2; id++) {

          ItemBuilder itemBuilder = new ItemBuilder(Material.COAL).setCustomMeta(id);
          player.getInventory().addItem(itemBuilder.toItemStack());
        }

      } catch (NumberFormatException e) {
        mtPlayer.sendMsg(LangMsg.ERROR_VALIDNUMBER, "");
        return true;
      }
    }



    return false;
  }
}

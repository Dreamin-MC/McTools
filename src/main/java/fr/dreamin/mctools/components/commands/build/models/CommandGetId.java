package fr.dreamin.mctools.components.commands.build.models;

import fr.dreamin.mctools.McTools;
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

public class CommandGetId implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(Lang.en_US, ""));
      return true;
    }

    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (!mtPlayer.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
      if (mtPlayer.getPlayer().getItemInHand().getItemMeta().hasCustomModelData()) player.sendMessage("§aId : §7" + mtPlayer.getPlayer().getItemInHand().getItemMeta().getCustomModelData());
      else mtPlayer.sendMsg(LangMsg.ERROR_ITEMID, "");
    }
    else mtPlayer.sendMsg(LangMsg.ERROR_ITEMHAND, "");

    return false;
  }
}

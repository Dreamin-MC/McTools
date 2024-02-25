package fr.dreamin.mctools.components.commands.build.models;

import fr.dreamin.mctools.McTools;
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
      sender.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
      return true;
    }

    Player player = (Player) sender;
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (!MTPlayer.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
      if (MTPlayer.getPlayer().getItemInHand().getItemMeta().hasCustomModelData()) player.sendMessage("§aId : §7" + MTPlayer.getPlayer().getItemInHand().getItemMeta().getCustomModelData());
      else player.sendMessage("§cErreur : §7Cet item n'a pas d'id.");
    }
    else player.sendMessage("§cErreur : §7Veuillez tenir un item en main.");

    return false;
  }
}

package fr.dreamin.mctools.components.commands.build.models;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.items.ItemBuilder;
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
      sender.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
      return true;
    }

    Player player = (Player) sender;
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (args.length == 0) {
      player.sendMessage("§cErreur : §7Veuillez entrer un nombre valide.");
      return true;
    }
    else if (args.length == 1) {
      try {
        int id = Integer.parseInt(args[0]);

        ItemBuilder itemBuilder = new ItemBuilder(Material.COAL).setCustomMeta(id);

        player.getInventory().addItem(itemBuilder.toItemStack());

      } catch (NumberFormatException e) {
        player.sendMessage("§cErreur : §7Veuillez entrer un nombre valide.");
        return true;
      }
    }
    else if (args.length == 2){

      try {
        int id1 = Integer.parseInt(args[0]);
        int id2 = Integer.parseInt(args[1]);

        if (id2 < id1) {
          player.sendMessage("§cErreur : §7Veuillez entrer une tranche d'id normal.");
          return true;
        }

        for (int id = id1; id <= id2; id++) {

          ItemBuilder itemBuilder = new ItemBuilder(Material.COAL).setCustomMeta(id);
          player.getInventory().addItem(itemBuilder.toItemStack());
        }

      } catch (NumberFormatException e) {
        player.sendMessage("§cErreur : §7Veuillez entrer un nombre valide.");
        return true;
      }
    }



    return false;
  }
}

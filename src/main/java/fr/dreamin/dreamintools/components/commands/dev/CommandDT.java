package fr.dreamin.dreamintools.components.commands.dev;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.advancements.AdvancementMessage;
import fr.dreamin.dreamintools.api.gui.defaultGui.ListMapGui;
import fr.dreamin.dreamintools.api.hologram.ItemDisplayManager;
import fr.dreamin.dreamintools.api.items.ItemBuilder;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandDT implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(McTools.getCodex().getPrefix() + McTools.getCodex().getErrorConsole());
      return true;
    }

    Player player = (Player) commandSender;

    DTPlayer dTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (dTPlayer.hasPermAdmin() || dTPlayer.hasPermDev()) {
      switch (strings[0]) {
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
        case "test":

          AdvancementMessage advancementMessage = new AdvancementMessage("test", "七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七七\uE262", "minecraft:stone", "minecraft:stone", McTools.getInstance());

          advancementMessage.showTo(player);

          break;
        case "test1":

          ItemStack stack = new ItemBuilder(Material.COAL).setCustomMeta(Integer.valueOf(strings[1])).toItemStack();

          new ItemDisplayManager(player.getLocation(), stack, false);
          break;

        case "listmap":
          McTools.getInstance().getGuiManager().open(player, ListMapGui.class);
          break;
        case "convert":

          Inventory inv = Bukkit.createInventory(player, InventoryType.ANVIL, "Test");

          inv.setItem(0, new ItemBuilder(Material.COAL).toItemStack());

          player.openInventory(inv);

          break;
        default:
          player.sendMessage("Merci de préciser une sous-commande.");
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
  public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

    Player player = (Player) commandSender;

    DTPlayer dTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    List<String> listArgs = new ArrayList<>();

    if (dTPlayer.hasPermAdmin() || dTPlayer.hasPermDev()) {
      if (strings.length == 1) {
        listArgs.add("reload");
        listArgs.add("test");
        listArgs.add("test2");
        listArgs.add("test3");
        listArgs.add("listmap");
        if (McTools.getCodex().isEditMode())
          listArgs.add("editmode");

        return listArgs;
      }
    }

    return listArgs;
  }
}

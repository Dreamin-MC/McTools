package fr.dreamin.mctools.components.commands.modelEngine;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.modelEngine.Interact;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.components.game.manager.InteractManager;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.database.fetcher.interactFetcher.InteractFetcher;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandInteract implements CommandExecutor, TabCompleter {

  private static final String PREFIX = McTools.getCodex().getBroadcastprefix();

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }


    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (mtPlayer == null) {
      player.sendMessage(LangMsg.ERROR_OCCURRED.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    if (args.length == 0) {
      mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
      return false;
    }

    if (player.hasPermission(PlayerPerm.MODO.getPerm())) {

      if (!McTools.getCodex().isInteractMode()) {
        player.sendMessage("Cette fonctionnalité n'est pas activé");
        return false;
      }

      switch (args[0]) {
        case "add":
          addInteract(args, player);
          break;
        case "reload":
          reloadInteracts();
          break;
        case "dispawnall":
          despawnAllInteracts();
          break;
        case "spawnall":
          spawnAllInteracts();
          break;
        default:
          mtPlayer.sendMsg(LangMsg.ERROR_VALIDPUTVALUE, "");
          break;
      }
    }

    return true;
  }

  private void addInteract(String[] args, Player player) {
    InteractManager.newInteract(args[1], player.getLocation(), true, true);
  }

  private void reloadInteracts() {
    for (World world : Bukkit.getWorlds()) {
      InteractManager.getInteracts().forEach(Interact::remove);
      InteractFetcher.getAllInteractByWorld(world);
    }
  }

  private void despawnAllInteracts() {
    InteractManager.getInteracts().forEach(Interact::remove);
  }

  private void spawnAllInteracts() {
    for (World world : Bukkit.getWorlds()) {
      InteractFetcher.getAllInteractByWorld(world);
    }
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      return null;
    }

    Player player = (Player) sender;
    MTPlayer dTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    List<String> listArgs = new ArrayList<>();

    if (PlayerPerm.hasPermMin(dTPlayer.getPerm(), PlayerPerm.BUILD)) {
      if (args.length == 1) {
        listArgs.add("add");
        listArgs.add("reload");
        listArgs.add("dispawnall");
        listArgs.add("spawnall");
      }
    }

    return listArgs;
  }
}
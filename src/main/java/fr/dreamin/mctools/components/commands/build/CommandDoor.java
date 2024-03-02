package fr.dreamin.mctools.components.commands.build;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.door.Door;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.components.game.manager.DoorManager;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.database.fetcher.doorFetcher.DoorFetcher;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

public class CommandDoor implements CommandExecutor, TabCompleter {

  private static final String PREFIX = McTools.getCodex().getBroadcastprefix();

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (args.length == 0) {
      mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
      return false;
    }

    switch (args[0]) {
      case "add":
        addDoor(args, player);
        break;
      case "reload":
        reloadDoors();
        break;
      case "dispawnall":
        despawnAllDoors();
        break;
      case "spawnall":
        spawnAllDoors();
        break;
      case "lockall":
        lockAllDoors();
        break;
      case "unlockall":
        unlockAllDoors();
        break;
      default:
        mtPlayer.sendMsg(LangMsg.ERROR_VALIDPUTVALUE, "");
        break;
    }

    return true;
  }

  private void addDoor(String[] args, Player player) {
    //TODO voir comment faire ça
//        DoorManager.newDoor(args[1], player.getLocation(), true, true);
  }

  private void reloadDoors() {
    for (World world : Bukkit.getWorlds()) {
      DoorManager.getDoors().stream()
        .filter(door -> door.getCuboide() != null)
        .forEach(door -> door.getCuboide().replaceType(Material.BARRIER, Material.AIR));
      DoorFetcher.getAllDoorByWorld(world);
    }
  }

  private void despawnAllDoors() {
    DoorManager.getDoors().stream()
      .filter(door -> door.getCuboide() != null)
      .forEach(door -> door.getCuboide().replaceType(Material.BARRIER, Material.AIR));
    DoorManager.getDoors().forEach(Door::remove);
  }

  private void spawnAllDoors() {
    for (World world : Bukkit.getWorlds()) {
      DoorFetcher.getAllDoorByWorld(world);
    }
  }

  private void lockAllDoors() {
    DoorManager.getDoors().stream()
      .filter(Door::isCanByLocked)
      .forEach(door -> door.setLocked(true));
  }

  private void unlockAllDoors() {
    DoorManager.getDoors().stream()
      .filter(Door::isCanByLocked)
      .forEach(door -> door.setLocked(false));
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
        listArgs.add("lockall");
        listArgs.add("unlockall");
      }
    }

    return listArgs;
  }
}
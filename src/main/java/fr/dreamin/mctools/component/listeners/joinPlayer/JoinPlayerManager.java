package fr.dreamin.mctools.component.listeners.joinPlayer;

import fr.dreamin.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.McTools;
import fr.dreamin.api.listener.join.OnMtPlayerJoin;
import fr.dreamin.mctools.component.player.MTPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JoinPlayerManager {

  public static void addPlayer(Player player) {
    MTPlayer mtPlayer = null;

    if (McTools.getService(PlayersService.class).getPlayerClass() != null) {
      Class<? extends MTPlayer> playerClass = McTools.getService(PlayersService.class).getPlayerClass();

      try {
        Constructor<? extends MTPlayer> constructor = playerClass.getDeclaredConstructor(Player.class);
        mtPlayer = constructor.newInstance(player);

      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        player.kick();
        e.printStackTrace(); // Gérer les erreurs de création d'instance
      }
    }
    else {
      mtPlayer = new MTPlayer(player);
    }

    if (McTools.getCodex().isResourcePack() && McTools.getCodex().getResourcePackUrl() != null) player.setResourcePack(McTools.getCodex().getResourcePackUrl());

    McTools.getService(PlayersService.class).addDTPlayer(mtPlayer);

    OnMtPlayerJoin.callEvent(mtPlayer);
  }
}

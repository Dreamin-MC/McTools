package fr.dreamin.mctools.components.listeners.joinPlayer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.database.fetcher.UserFetcher.UserFetcher;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

public class JoinPlayerManager {

  public static void addPlayer(Player player) {

    MTPlayer mtPlayer = null;

    if (McTools.getService(PlayersService.class).getPlayerClass() != null) {
      Class<? extends MTPlayer> playerClass = McTools.getService(PlayersService.class).getPlayerClass();

      try {
        Constructor<? extends MTPlayer> constructor = playerClass.getDeclaredConstructor(Player.class);
        mtPlayer = constructor.newInstance(player);

      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        e.printStackTrace(); // Gérer les erreurs de création d'instance
      }
    } else {
      mtPlayer = new MTPlayer(player);
    }

    if (McTools.getCodex().isDefaultItems()) {

      player.getInventory().clear();

      //Reset ces valeurs de joueur
      player.setInvulnerable(false);
      player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.10);
      player.getActivePotionEffects().clear();
      player.setGameMode(GameMode.ADVENTURE);
      player.setLevel(0);
      player.setMaxHealth(20);
      player.setHealth(20);
      player.setFoodLevel(20);
      player.getInventory().clear();
      player.setAllowFlight(false);
      player.setFlying(false);

      player.getInventory().setItem(0, new ItemBuilder(ItemsPreset.itemDash.getItem()).setName(CustomChatColor.YELLOW.getColorWithText("[lang]default.item.dash[/lang]")).toItemStack());
      player.getInventory().setItem(4, new ItemBuilder(ItemsPreset.itemConfig.getItem()).setName(CustomChatColor.YELLOW.getColorWithText("[lang]default.item.config[/lang]")).toItemStack());
      player.getInventory().setItem(8, new ItemBuilder(mtPlayer.getItemStats()).setName(CustomChatColor.YELLOW.getColorWithText("[lang]default.item.stats[/lang]")).toItemStack());
    }

    player.setGravity(true);

    if (McTools.getCodex().isResourcePack() && McTools.getCodex().getResourcePackUrl() != null) player.setResourcePack(McTools.getCodex().getResourcePackUrl());

    McTools.getService(PlayersService.class).addDTPlayer(mtPlayer);
    UserFetcher.getIfInsert(mtPlayer);
  }
}

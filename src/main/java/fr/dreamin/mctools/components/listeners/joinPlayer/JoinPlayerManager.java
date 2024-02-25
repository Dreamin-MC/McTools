package fr.dreamin.mctools.components.listeners.joinPlayer;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JoinPlayerManager {

  private static int i = 0;

  public static void addPlayer(Player player) {

    MTPlayer mTPlayer = null;

    if (McTools.getService(PlayersService.class).getPlayerClass() != null) {
      Class<? extends MTPlayer> playerClass = McTools.getService(PlayersService.class).getPlayerClass();

      try {
        Constructor<? extends MTPlayer> constructor = playerClass.getDeclaredConstructor(Player.class);
        mTPlayer = constructor.newInstance(player);

      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        e.printStackTrace(); // Gérer les erreurs de création d'instance
      }
    }
    else {
      mTPlayer = new MTPlayer(player);
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
      player.getInventory().setItem(8, new ItemBuilder(mTPlayer.getItemStats()).setName(CustomChatColor.YELLOW.getColorWithText("[lang]default.item.stats[/lang]")).toItemStack());
    }

    McTools.getService(PlayersService.class).addDTPlayer(mTPlayer);

  }

}

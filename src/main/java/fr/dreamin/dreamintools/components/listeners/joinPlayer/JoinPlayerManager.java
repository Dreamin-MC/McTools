package fr.dreamin.dreamintools.components.listeners.joinPlayer;

import fr.dreamin.dreamintools.McTools;
import fr.dreamin.dreamintools.api.colors.CustomChatColor;
import fr.dreamin.dreamintools.api.items.ItemBuilder;
import fr.dreamin.dreamintools.api.packUtils.ItemsPreset;
import fr.dreamin.dreamintools.components.players.DTPlayer;
import fr.dreamin.dreamintools.paper.services.players.PlayersService;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class JoinPlayerManager {

  private static int i = 0;

  public static void addPlayer(Player player) {

    DTPlayer dtPlayer = new DTPlayer(player);

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
      player.getInventory().setItem(8, new ItemBuilder(dtPlayer.getItemStats()).setName(CustomChatColor.YELLOW.getColorWithText("[lang]default.item.stats[/lang]")).toItemStack());
    }

    McTools.getService(PlayersService.class).addDTPlayer(dtPlayer);

  }

}

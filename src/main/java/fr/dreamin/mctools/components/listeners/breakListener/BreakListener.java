package fr.dreamin.mctools.components.listeners.breakListener;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BreakListener implements Listener {

  private final Map<UUID, Player> lastDamager = new HashMap<>();

  @EventHandler
  public void onArmorStandDamage(EntityDamageByEntityEvent event) {

    if (McTools.getCodex().isBuildMode()) {
      if (event.getEntity() instanceof ArmorStand && event.getDamager() instanceof Player) {
        ArmorStand armorStand = (ArmorStand) event.getEntity();
        Player player = (Player) event.getDamager();

        // Enregistre le joueur comme dernier attaquant de cet armor stand
        lastDamager.put(armorStand.getUniqueId(), player);
      }
    }

  }

  @EventHandler
  public void onArmorStandDeath(EntityDeathEvent event) {

    if (McTools.getCodex().isBuildMode()) {
      if (event.getEntity() instanceof ArmorStand) {
        ArmorStand armorStand = (ArmorStand) event.getEntity();
        UUID armorStandId = armorStand.getUniqueId();

        // Vérifie si cet armor stand a été attaqué récemment par un joueur
        if (lastDamager.containsKey(armorStandId)) {
          Player player = lastDamager.get(armorStandId);

          for (MTPlayer bPlayer : McTools.getService(PlayersService.class).getDTPlayers()) {

            for (ArmorStand armorStand1 : bPlayer.getArmorStandManager().getArmorStandSelected()) {
              if (armorStand1.getUniqueId().equals(armorStandId)) {
                bPlayer.getArmorStandManager().getArmorStandSelected().remove(armorStand1);
                break;
              }
            }

          }

          // Nettoie la référence
          lastDamager.remove(armorStandId);
        }
      }
    }


  }

}
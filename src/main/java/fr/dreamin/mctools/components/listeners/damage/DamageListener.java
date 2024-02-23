package fr.dreamin.mctools.components.listeners.damage;

import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {

    // Vérifier si l'entité est un Shulker
    if (event.getEntity() instanceof Shulker) {
      // Vérifier si le type de dommage est la suffocation
      if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
        // Annuler l'événement de dommage pour empêcher la suffocation
        event.setCancelled(true);
      }
    }
  }

}

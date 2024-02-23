package fr.dreamin.mctools.api.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityListener implements Listener {

  private final Entity entity;
  private final List<EntityRunnable> runnableList = new ArrayList<>();

  public EntityListener(Entity entity) {
    this.entity = entity;
  }

  public void add(EntityRunnable runnable) {
    runnableList.add(runnable);
  }

  public void remove(EntityRunnable runnable) {
    runnableList.remove(runnable);
  }

  @EventHandler
  public void onInteract(PlayerInteractAtEntityEvent event) {
    if (event.getRightClicked().equals(entity)) {
      event.setCancelled(true);
      runnableList.forEach(runnable -> runnable.run(event, entity));
    }
  }

  public interface EntityRunnable {
    public void run(PlayerInteractAtEntityEvent event, Entity entity);
  }

}

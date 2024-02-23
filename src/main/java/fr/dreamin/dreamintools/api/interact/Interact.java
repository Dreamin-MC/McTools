package fr.dreamin.dreamintools.api.interact;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.events.BaseEntityInteractEvent;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import fr.dreamin.dreamintools.McTools;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Interact {
  private ArmorStand armorStand;
  private ModeledEntity modeledEntity;
  private ActiveModel activeModel;
  private InteractListener listener;


  public Interact(String modelName, Location location, Boolean clickable) {
    armorStand = Objects.requireNonNull(location.getWorld()).spawn(location, ArmorStand.class);
    armorStand.setBasePlate(false);
    armorStand.setVisible(false);
    armorStand.setGravity(false);
    armorStand.setSmall(true);
    armorStand.setInvulnerable(true);

    this.modeledEntity = ModelEngineAPI.createModeledEntity(armorStand);
    // Create a new ActiveModel using the ID of a model
    // Will throw an error if the model does not exist
    this.activeModel = ModelEngineAPI.createActiveModel(modelName);
    // Add the model to the entity
    this.modeledEntity.addModel(this.activeModel, true);

    armorStand.getPersistentDataContainer().set(new NamespacedKey(McTools.getInstance(), "door"), PersistentDataType.STRING, "door");
    armorStand.getPersistentDataContainer().set(new NamespacedKey(McTools.getInstance(), "modelName"), PersistentDataType.STRING, modelName);

    if (clickable) {
      listener = new Interact.InteractListener(this);
      McTools.getInstance().getServer().getPluginManager().registerEvents(listener, McTools.getInstance());

      addClickEvent((event, clickedItemStand) -> {
        setInteract();
      });
    }

  }

  public void setInteract() {
    AnimationHandler animOpen = getActiveModel().getAnimationHandler();

    animOpen.playAnimation("anim_interact", 0, 0, 1, true);
  }


  public ModeledEntity getModeledEntity() {
    return this.modeledEntity;
  }

  public ActiveModel getActiveModel() {
    return this.activeModel;
  }

  public void remove() {
    this.armorStand.remove();
  }

  private class InteractListener implements Listener {
    private final Interact doorManager;
    private final List<Interact.InteractRunnable> runnableList = new ArrayList<>();

    public InteractListener(Interact doorManager) {
      this.doorManager = doorManager;
    }

    public void add(Interact.InteractRunnable runnable) {
      runnableList.add(runnable);
    }

    public void remove(Interact.InteractRunnable runnable) {
      runnableList.remove(runnable);
    }

    @EventHandler
    public void onInteract(BaseEntityInteractEvent event) {

      if (event.getAction().equals(BaseEntityInteractEvent.Action.INTERACT_ON) && event.getModel().equals(doorManager.getActiveModel())) {

        if (!McTools.getInstance().getCooldownManager().isCooldown(doorManager.getModeledEntity().toString())) {
          runnableList.forEach(runnable -> runnable.run(event, doorManager));
          McTools.getInstance().getCooldownManager().setCooldown(doorManager.getModeledEntity().toString(), 10, (key) -> {});
        }
      }

    }
  }

  public void addClickEvent(Interact.InteractRunnable runnable) {
    listener.add(runnable);
  }

  public void removeClickEvent(Interact.InteractRunnable runnable) {
    listener.remove(runnable);
  }

  public interface InteractRunnable {
    void run(BaseEntityInteractEvent event, Interact clickedInteractManager);
  }

}
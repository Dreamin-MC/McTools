package fr.dreamin.mctools.api.door;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.events.BaseEntityInteractEvent;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.cuboide.Cuboide;
import fr.dreamin.mctools.api.time.CooldownManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Door {

  private int id;
  private Cuboide cuboide;
  private ArmorStand armorStand;
  private ModeledEntity modeledEntity;
  private ActiveModel activeModel;
  private boolean isOpen = false;
  private boolean isLocked = false;
  private boolean canByBlocked = false;
  private boolean canByLocked = false;
  private DoorListener listener;

  public Door(int id, String modelName, Location location, Cuboide cuboide, Boolean locked, Boolean blockable,  Boolean clickable) {
    armorStand = Objects.requireNonNull(location.getWorld()).spawn(location, ArmorStand.class);
    armorStand.setBasePlate(false);
    armorStand.setVisible(false);
    armorStand.setGravity(false);
    armorStand.setSmall(true);
    armorStand.setInvulnerable(true);

    this.cuboide = cuboide;
    this.canByBlocked = blockable;
    this.canByLocked = locked;

    if (this.canByBlocked && this.cuboide != null) cuboide.replaceType(Material.AIR, Material.BARRIER);

    this.modeledEntity = ModelEngineAPI.createModeledEntity(armorStand);
    // Create a new ActiveModel using the ID of a model
    // Will throw an error if the model does not exist
    this.activeModel = ModelEngineAPI.createActiveModel(modelName);
    // Add the model to the entity
    this.modeledEntity.addModel(this.activeModel, true);

    armorStand.getPersistentDataContainer().set(new NamespacedKey(McTools.getInstance(), "door"), PersistentDataType.STRING, "door");
    armorStand.getPersistentDataContainer().set(new NamespacedKey(McTools.getInstance(), "modelName"), PersistentDataType.STRING, modelName);

    if (clickable) {
      listener = new DoorListener(this);
      McTools.getInstance().getServer().getPluginManager().registerEvents(listener, McTools.getInstance());

      addClickEvent((event, clickedItemStand) -> {setOpen(!isOpen());});
    }

  }

  public void open() {
    AnimationHandler animOpen = getActiveModel().getAnimationHandler();

    animOpen.stopAnimation("anim_close");
    animOpen.playAnimation("anim_open", 0, 0, 1, true);

    if (canByBlocked && cuboide != null) cuboide.replaceType(Material.BARRIER, Material.AIR);

  }

  public void close() {
    AnimationHandler animOpen = getActiveModel().getAnimationHandler();

    animOpen.stopAnimation("anim_open");
    animOpen.playAnimation("anim_close", 0, 0, 1, true);

    if (canByBlocked && cuboide != null) cuboide.replaceType(Material.AIR, Material.BARRIER);

  }

  public void setOpen(boolean open) {

    this.isOpen = open;

    if (open) {
      if (canByLocked && !isLocked) open();
      else if (canByLocked && isLocked) this.isOpen = false;
      else open();
    }
    else close();
  }

  public boolean isOpen() {
    return this.isOpen;
  }

  public ModeledEntity getModeledEntity() {
    return this.modeledEntity;
  }

  public ActiveModel getActiveModel() {
    return this.activeModel;
  }

  public void setLocked(boolean locked) {
    this.isLocked = locked;

    if (locked) close();
    else open();

  }

  public boolean isLocked() {
    return this.isLocked;
  }

  public boolean isCanByLocked() {
    return this.canByLocked;
  }

  public void remove() {
    this.armorStand.remove();
  }

  public Cuboide getCuboide() {
    return this.cuboide;
  }

  private class DoorListener implements Listener {
    private final Door doorManager;
    private final List<DoorRunnable> runnableList = new ArrayList<>();

    public DoorListener(Door doorManager) {
      this.doorManager = doorManager;
    }

    public void add(DoorRunnable runnable) {
      runnableList.add(runnable);
    }

    public void remove(DoorRunnable runnable) {
      runnableList.remove(runnable);
    }

    @EventHandler
    public void onInteract(BaseEntityInteractEvent event) {

      if (event.getAction().equals(BaseEntityInteractEvent.Action.INTERACT_ON) && event.getModel().equals(doorManager.getActiveModel())) {

        if (!McTools.getService(CooldownManager.class).isCooldown(doorManager.getModeledEntity().toString())) {
          runnableList.forEach(runnable -> runnable.run(event, doorManager));

          McTools.getService(CooldownManager.class).setCooldown(doorManager.getModeledEntity().toString(), 10, (key) -> {});
        }
      }

    }
  }

  public void addClickEvent(DoorRunnable runnable) {
    listener.add(runnable);
  }

  public void removeClickEvent(DoorRunnable runnable) {
    listener.remove(runnable);
  }

  public interface DoorRunnable {
    void run(BaseEntityInteractEvent event, Door clickedDoorManager);
  }

}
